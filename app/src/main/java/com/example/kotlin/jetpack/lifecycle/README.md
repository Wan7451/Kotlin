1.  ProcessLifecycleOwnerInitializer  中初始化  
```
public class ProcessLifecycleOwnerInitializer extends ContentProvider {
    @Override
    public boolean onCreate() {
        LifecycleDispatcher.init(getContext());
        ProcessLifecycleOwner.init(getContext());
        return true;
    }
    ...
}
```
2. LifecycleDispatcher   
    为每个新建的Activity 添加Fragment，最终通过Fragment来感应 Activity 的生命周期
    
    
3. ProcessLifecycleOwner
     记录Activity的状态来 回调 进程的状态    
     
     api < 29  用Fragment获得生命周期回调
               activity.registerActivityLifecycleCallbacks 获得回调
     
     