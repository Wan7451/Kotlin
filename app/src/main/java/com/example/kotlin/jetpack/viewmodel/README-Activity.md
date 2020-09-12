
## isAssignableFrom()方法与instanceof关键字的区别总结为以下两个点：

1. isAssignableFrom()方法是从类继承的角度去判断，instanceof关键字是从实例继承的角度去判断。
2. isAssignableFrom()方法是判断是否为某个类的父类，instanceof关键字是判断是否某个类的子类。


### ViewModelStore  用于存储 viewModel对象
    * 通过hashMap方式缓存所有viewModel   HashMap<String, ViewModel> mMap = new HashMap<>();  
    * key = DEFAULT_KEY + ":" + canonicalName
    
### ViewModelProvider.Factory  用于创建 viewModel 对象
    * NewInstanceFactory  无参构造器 viewModel
    * AndroidViewModelFactory  构造参数为 Application 的 viewModel
    * KeyedFactory   用含有 key 的方式创建 viewModel     
    
    
    
### 如何创建 viewModel    
```
val provider=ViewModelProvider(store:ViewModelStore, factory:ViewModelProvider.Factory)
provider.get(viewModelClass.java)

public <T extends ViewModel> T get(@NonNull Class<T> modelClass) {
  String canonicalName = modelClass.getCanonicalName();
  if (canonicalName == null) {
      throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
  }
  return get(DEFAULT_KEY + ":" + canonicalName, modelClass);
}


public <T extends ViewModel> T get(@NonNull String key, @NonNull Class<T> modelClass) {
  ViewModel viewModel = mViewModelStore.get(key);
  //从缓存取  
  if (modelClass.isInstance(viewModel)) {
      //noinspection unchecked
      return (T) viewModel;
  } else {
      //noinspection StatementWithEmptyBody
      if (viewModel != null) {
          // TODO: log a warning.
      }
  }
  //缓存没有再去创建  
  if (mFactory instanceof KeyedFactory) {
      viewModel = ((KeyedFactory) (mFactory)).create(key, modelClass);
  } else {
      viewModel = (mFactory).create(modelClass);
  }
  //再存起来
  mViewModelStore.put(key, viewModel);
  //noinspection unchecked
  return (T) viewModel;
}
```

### activity 如何处理

1. 缓存
```
public ViewModelStore getViewModelStore() {
    if (getApplication() == null) {
        throw new IllegalStateException("Your activity is not yet attached to the "
            + "Application instance. You can't request ViewModel before onCreate call.");
    }
    if (mViewModelStore == null) {
        //从缓存中取出
        NonConfigurationInstances nc =
            (NonConfigurationInstances) getLastNonConfigurationInstance();
        if (nc != null) {
            // Restore the ViewModelStore from NonConfigurationInstances
            mViewModelStore = nc.viewModelStore;
        }
        //没有再创建
        if (mViewModelStore == null) {
            mViewModelStore = new ViewModelStore();
        }
    }
    return mViewModelStore;
}


public Object getLastNonConfigurationInstance() {
    return mLastNonConfigurationInstances != null    //再次启动时有数据会恢复
            ? mLastNonConfigurationInstances.activity : null;
}
```

2. 存
```
ComponentActivity 

public final Object onRetainNonConfigurationInstance() {
    Object custom = onRetainCustomNonConfigurationInstance();  //自定义缓存的内容

    ViewModelStore viewModelStore = mViewModelStore;
    if (viewModelStore == null) {
        // No one called getViewModelStore(), so see if there was an existing
        // ViewModelStore from our last NonConfigurationInstance
        NonConfigurationInstances nc =
            (NonConfigurationInstances) getLastNonConfigurationInstance();
        if (nc != null) {
            viewModelStore = nc.viewModelStore;
        }
    }

    if (viewModelStore == null && custom == null) {
        return null;
    }

    NonConfigurationInstances nci = new NonConfigurationInstances();
    nci.custom = custom;                  //缓存数据
    nci.viewModelStore = viewModelStore;  //viewModelStore 对象 
    return nci;
}


Activity
//因内存不足被杀之前 缓存数据
NonConfigurationInstances retainNonConfigurationInstances() {
    Object activity = onRetainNonConfigurationInstance();
    HashMap<String, Object> children = onRetainNonConfigurationChildInstances();
    FragmentManagerNonConfig fragments = mFragments.retainNestedNonConfig();

    // We're already stopped but we've been asked to retain.
    // Our fragments are taken care of but we need to mark the loaders for retention.
    // In order to do this correctly we need to restart the loaders first before
    // handing them off to the next activity.
    mFragments.doLoaderStart();
    mFragments.doLoaderStop(true);
    ArrayMap<String, LoaderManager> loaders = mFragments.retainLoaderNonConfig();

    if (activity == null && children == null && fragments == null && loaders == null
            && mVoiceInteractor == null) {
        return null;
    }

    NonConfigurationInstances nci = new NonConfigurationInstances();
    nci.activity = activity;  //viewModelStore 缓存
    nci.children = children;
    nci.fragments = fragments;
    nci.loaders = loaders;
    if (mVoiceInteractor != null) {
        mVoiceInteractor.retainInstance();
        nci.voiceInteractor = mVoiceInteractor;
    }
    return nci;
}

```



```
getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source,
                    @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    if (!isChangingConfigurations()) {
                        getViewModelStore().clear();
                    }
                }
            }
        });
```



