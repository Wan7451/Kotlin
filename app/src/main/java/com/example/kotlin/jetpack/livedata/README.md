## 注册
```
@MainThread
public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
    assertMainThread("observe");  //不在主线程抛异常
    //owner 销毁后跳过
    if (owner.getLifecycle().getCurrentState() == DESTROYED) {
    // ignore
    return;
    }
    //将 owner   observer 绑定
    LifecycleBoundObserver wrapper = new LifecycleBoundObserver(owner, observer);
    //用map记录 wrapper 对象，如果已存在并绑定上 抛异常
    ObserverWrapper existing = mObservers.putIfAbsent(observer, wrapper);
    if (existing != null && !existing.isAttachedTo(owner)) {
    throw new IllegalArgumentException("Cannot add the same observer"
    + " with different lifecycles");
    }
    //如果已存在 跳过
    if (existing != null) {
    return;
    }
    //添加 Observer
    //当 owner 的生命周期变化  会回调 LifecycleBoundObserver 的onStateChange方法
    owner.getLifecycle().addObserver(wrapper);
}
```

```
//将 owner   observer 绑定
class LifecycleBoundObserver extends ObserverWrapper implements LifecycleEventObserver {
    @NonNull
    final LifecycleOwner mOwner;

    LifecycleBoundObserver(@NonNull LifecycleOwner owner, Observer<? super T> observer) {
        super(observer);
        mOwner = owner;
    }
    
    //判断是否可以激活 状态是 onStart onResume
    //判断是否需要激活，当生命周期为STARTED/RESUMED状态下返回true
    @Override
    boolean shouldBeActive() {
        return mOwner.getLifecycle().getCurrentState().isAtLeast(STARTED);
    }
    //状态发生变化时调用
    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        Lifecycle.State currentState = mOwner.getLifecycle().getCurrentState();
        //如果已经消亡，移除掉Observer，然后返回
        //看吧，所以我们不需要手动移除Observer，内部帮我们实现了
        if (currentState == DESTROYED) {
            removeObserver(mObserver); //LiveData 的方法
            return;
        }
        Lifecycle.State prevState = null;
        while (prevState != currentState) {
            prevState = currentState;
            activeStateChanged(shouldBeActive());
            currentState = mOwner.getLifecycle().getCurrentState();
        }
    }
    // 是否已绑定
    @Override
    boolean isAttachedTo(LifecycleOwner owner) {
        return mOwner == owner;
    }
    //移除 Observer
    @Override
    void detachObserver() {
        mOwner.getLifecycle().removeObserver(this);
    }
}


// Observer 包装类
private abstract class ObserverWrapper {
    final Observer<? super T> mObserver;
    boolean mActive;  //是否活跃
    int mLastVersion = START_VERSION; //数据版本

    ObserverWrapper(Observer<? super T> observer) {
        mObserver = observer;
    }
    //是否要激活
    abstract boolean shouldBeActive();
    //是否绑定
    boolean isAttachedTo(LifecycleOwner owner) {
        return false;
    }

    void detachObserver() {
    }
    //更新状态 是否激活
    void activeStateChanged(boolean newActive) {
        if (newActive == mActive) {
            return;
        }
        // immediately set active state, so we'd never dispatch anything to inactive
        // owner
        mActive = newActive;  //记录新的状态
        changeActiveCounter(mActive ? 1 : -1); LiveData 方法
        if (mActive) {      //处理数据
            dispatchingValue(this);
        }
    }
}

```


```
LibeData 
//更改活跃回调状态
@MainThread
void changeActiveCounter(int change) {  激活 +1  取消 -1
    int previousActiveCount = mActiveCount;
    mActiveCount += change;  //更新点数  活跃的Observer?
    if (mChangingActiveState) {
        return;
    }
    mChangingActiveState = true;
    try {
        while (previousActiveCount != mActiveCount) {
            boolean needToCallActive = previousActiveCount == 0 && mActiveCount > 0;
            boolean needToCallInactive = previousActiveCount > 0 && mActiveCount == 0;
            previousActiveCount = mActiveCount;
            if (needToCallActive) {
                onActive();  //当前Observer活跃 之前为0 现在 >0 调用 onActive()方法 Called when the number of active observers change from 0 to 1.
            } else if (needToCallInactive) {
                onInactive(); //当前Observer不活跃  Called when the number of active observers change from 1 to 0.
            }
        }
    } finally {
        mChangingActiveState = false;
    }
}

//分发数据
void dispatchingValue(@Nullable ObserverWrapper initiator) {
    //防止重复分发相同的数据
    if (mDispatchingValue) {
        mDispatchInvalidated = true;
        return;
    }
    mDispatchingValue = true;
    do {
        mDispatchInvalidated = false;
        //当initiator != null，只处理当前Observer
        if (initiator != null) {
            considerNotify(initiator);
            initiator = null;
        } else {
            //否则遍历所有的Observer，进行分发
            for (Iterator<Map.Entry<Observer<? super T>, ObserverWrapper>> iterator =
                mObservers.iteratorWithAdditions(); iterator.hasNext(); ) {
            considerNotify(iterator.next().getValue());
            if (mDispatchInvalidated) {
                break;
            }
            }
        }
    } while (mDispatchInvalidated);
    mDispatchingValue = false;
}



private void considerNotify(ObserverWrapper observer) {
    if (!observer.mActive) { //如果不处于活跃状态，就返回
        return;
    }
    // Check latest state b4 dispatch. Maybe it changed state but we didn't get the event yet.
    //
    // we still first check observer.active to keep it as the entrance for events. So even if
    // the observer moved to an active state, if we've not received that event, we better not
    // notify for a more predictable notification order.
    if (!observer.shouldBeActive()) { //检测是否激活
        observer.activeStateChanged(false);
        return;
    }
    //版本号终于派上用场了，如果数据已经是最新，就不更新，直接返回
    if (observer.mLastVersion >= mVersion) {
        return;
    }
    observer.mLastVersion = mVersion;
    observer.mObserver.onChanged((T) mData);
}



@MainThread
protected void setValue(T value) {
    assertMainThread("setValue");
    mVersion++;
    mData = value;
    dispatchingValue(null);  //分发给所有的Observer
}


protected void postValue(T value) {
    boolean postTask;
    //进程锁住，因此liveData是线程安全的
    synchronized (mDataLock) {
        //判断是否没有在处理数据
        postTask = mPendingData == NOT_SET;
        mPendingData = value;
    }
    if (!postTask) {
        return;
    }
    //到主线程去处理
    ArchTaskExecutor.getInstance().postToMainThread(mPostValueRunnable);
}


private final Runnable mPostValueRunnable = new Runnable() {
    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        Object newValue;
        synchronized (mDataLock) {
            newValue = mPendingData;
            mPendingData = NOT_SET;
        }
        //调用的还是setValue方法
        setValue((T) newValue);
    }
};

```



```
//不跟生命周期走的回调
@MainThread
public void observeForever(@NonNull Observer<? super T> observer) {
    assertMainThread("observeForever");
    AlwaysActiveObserver wrapper = new AlwaysActiveObserver(observer);
    ObserverWrapper existing = mObservers.putIfAbsent(observer, wrapper);
    if (existing instanceof LiveData.LifecycleBoundObserver) {
        throw new IllegalArgumentException("Cannot add the same observer"
            + " with different lifecycles");
    }
    if (existing != null) {
        return;
    }
    wrapper.activeStateChanged(true);
}


//状态一直为激活状态    需要手动取消注册
private class AlwaysActiveObserver extends ObserverWrapper {
    AlwaysActiveObserver(Observer<? super T> observer) {
        super(observer);
    }

    @Override
    boolean shouldBeActive() {
        return true;
    }
}


* liveData只在activity（lifecycleowner）处于活跃状态下更新数据的原因
* setValue和postValue更新数据时，activity处于非活跃状态，activity重回前台后继续更新数据的原因
* liveData防止内存泄漏的原因以及自动移除Observer的原因


https://www.jianshu.com/p/943770f22470
```