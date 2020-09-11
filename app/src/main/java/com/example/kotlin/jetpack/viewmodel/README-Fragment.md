
```
//使用 Activity 的 viewModelStore
inline fun <reified VM : ViewModel> Fragment.activityViewModels(
    noinline factoryProducer: (() -> Factory)? = null
) = createViewModelLazy(VM::class, { requireActivity().viewModelStore }, factoryProducer)

//使用 Fragment 的 viewModelStore
inline fun <reified VM : ViewModel> Fragment.viewModels(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> Factory)? = null
) = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProd

//与 Activity 一样
fun <VM : ViewModel> Fragment.createViewModelLazy(
    viewModelClass: KClass<VM>,
    storeProducer: () -> ViewModelStore,
    factoryProducer: (() -> Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        val application = activity?.application ?: throw IllegalStateException(
            "ViewModel can be accessed only when Fragment is attached"
        )
        AndroidViewModelFactory.getInstance(application)
    }
    return ViewModelLazy(viewModelClass, storeProducer, factoryPromise)
}
```

```
//Fragment 
public ViewModelStore getViewModelStore() {
    if (mFragmentManager == null) {
        throw new IllegalStateException("Can't access ViewModels from detached fragment");
    }
    return mFragmentManager.getViewModelStore(this);
}

//FragmentMangerImlp
ViewModelStore getViewModelStore(@NonNull Fragment f) {
    return mNonConfig.getViewModelStore(f);
}
```
### Fragment  通过 FragmentManger 缓存 viewModelStore

https://juejin.im/post/6844904079265644551

1. FragmentManagerViewModel mNonConfig;
  
 //存放 child 的 viewModel  
 private final HashMap<String, FragmentManagerViewModel> mChildNonConfigs = new HashMap<>();
 //存放 当前 的 viewModel
 private final HashMap<String, ViewModelStore> mViewModelStores = new HashMap<>();

 Key 值都为 Fragment 的唯一 UUID。该 UUID 会在 Fragment 对象创建时自动生成。也就是每个 Fragment 对应唯一 UUID。


第一步：在宿主 Activity 创建时，默认会在其 FramgentManager 中创建一个 FragmentManagerViewModel。
同时将生成的 FragmentManagerViewModel 存储在其本身的 ViewModelStore 中。同时使用自身的FragmentManager

```
//FragmentActivity 
protected void onCreate(@Nullable Bundle savedInstanceState) {
    mFragments.attachHost(null /*parent*/);
    ....
}
//FragmentManager
public void attachController(@NonNull FragmentHostCallback host,
        @NonNull FragmentContainer container, @Nullable final Fragment parent) {
    
    // Get the FragmentManagerViewModel
    if (parent != null) { //再次调用 添加到 mChildNonConfigs
        mNonConfig = parent.mFragmentManager.getChildNonConfig(parent);
    } else if (host instanceof ViewModelStoreOwner) {  //先这里
        ViewModelStore viewModelStore = ((ViewModelStoreOwner) host).getViewModelStore();
        mNonConfig = FragmentManagerViewModel.getInstance(viewModelStore); //首次创建 FragmentManagerViewModel 并将其添加到 Activity 中的 ViewModelStore 
    } else {
    mNonConfig = new FragmentManagerViewModel(false);
    }
}

注意，当 Fragment 是 子Fragment 时，parent.fragmentManager 的值为父Fragment 的 FragmentManager，否则为 Activity 中的 FragmentManager。

```


第二步：在 Fragment 创建时，从 宿主Activity 或 父Fragment 中的 FramgentManager 中获取对应的 
FragmentManagerViewModel，并使用自身的 ChildFragmentManager 中 mNonConfig 变量进行保存。

```
// FragmentManagerViewModel
FragmentManagerViewModel getChildNonConfig(@NonNull Fragment f) {
    FragmentManagerViewModel childNonConfig = mChildNonConfigs.get(f.mWho);
    if (childNonConfig == null) {
    childNonConfig = new FragmentManagerViewModel(mStateAutomaticallySaved);
    mChildNonConfigs.put(f.mWho, childNonConfig);
    }
    return childNonConfig;
}

在该方法中，会从 上层的 FragmentManagerViewModel 中的 mChildNonConfigs 中获取 Fragment 的 FragmentManagerViewModel，
如果有，直接返回。反之，存入mChildNonConfigs 中。
```

第三步：将 Fragment 中所创建的 ViewModel 与其自身的 ViewModelStore 关联 ，
并自身的 ViewModelStore 存储在 mNonConfig 所指向的 FragmentManaerViewModel 中的 mViewModelStores 中。

```
//Fragment
public ViewModelStore getViewModelStore() {
    if (mFragmentManager == null) {
        throw new IllegalStateException("Can't access ViewModels from detached fragment");
    }
    return mFragmentManager.getViewModelStore(this);
    }

//FragmentManager
ViewModelStore getViewModelStore(@NonNull Fragment f) {
    return mNonConfig.getViewModelStore(f);
}

//FragmentManagerViewModel
ViewModelStore getViewModelStore(@NonNull Fragment f) {
    ViewModelStore viewModelStore = mViewModelStores.get(f.mWho);
    if (viewModelStore == null) {
        viewModelStore = new ViewModelStore();
        //将创建好的ViewStore，放入FragmentManagerViewModel中
        mViewModelStores.put(f.mWho, viewModelStore);
    }
    return viewModelStore;
}

```

ViewModel 在 Fragment 中不会因配置改变而销毁的原因其实是因为其声明的 ViewModel 是存储在 FragmentManagerViewModel 
中的，而 FragmentManagerViewModel 是存储在宿主 Activity 中的 ViewModelStore 中，又因 Activity 中 ViewModelStore
不会因配置改变而销毁，故 Fragment 中 ViewModel 也不会因配置改变而销毁。


```

创建 FragmentManagerViewModel 对象


FragmentManagerViewModel getChildNonConfig(@NonNull Fragment f) {
    FragmentManagerViewModel childNonConfig = mChildNonConfigs.get(f.mWho);
    if (childNonConfig == null) {
        childNonConfig = new FragmentManagerViewModel(mStateAutomaticallySaved);
        mChildNonConfigs.put(f.mWho, childNonConfig);
    }
    return childNonConfig;
}
```





