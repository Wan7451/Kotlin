

mSavedStateRegistryController   用于执行恢复数据、保存数据，是入口

```

ComponentActivity


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedStateRegistryController.performRestore(savedInstanceState);
        ReportFragment.injectIfNeededIn(this);
        if (mContentLayoutId != 0) {
            setContentView(mContentLayoutId);
        }
    }


  @CallSuper
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Lifecycle lifecycle = getLifecycle();
        if (lifecycle instanceof LifecycleRegistry) {
            ((LifecycleRegistry) lifecycle).setCurrentState(Lifecycle.State.CREATED);
        }
        super.onSaveInstanceState(outState);
        mSavedStateRegistryController.performSave(outState);
    }
```


mRegistry  是真正执行 保存 读取的操作

```
SavedStateRegistryController

    @MainThread
    public void performRestore(@Nullable Bundle savedState) {
        Lifecycle lifecycle = mOwner.getLifecycle();
        if (lifecycle.getCurrentState() != Lifecycle.State.INITIALIZED) {
            throw new IllegalStateException("Restarter must be created only during "
                    + "owner's initialization stage");
        }
        lifecycle.addObserver(new Recreator(mOwner));
        mRegistry.performRestore(lifecycle, savedState);
    }
	
    @MainThread
    public void performSave(@NonNull Bundle outBundle) {
        mRegistry.performSave(outBundle);
    }	

```


```
SavedStateRegistry
	
	//取出数据
    void performRestore(@NonNull Lifecycle lifecycle, @Nullable Bundle savedState) {
        if (mRestored) {
            throw new IllegalStateException("SavedStateRegistry was already restored.");
        }
        if (savedState != null) {  
            mRestoredState = savedState.getBundle(SAVED_COMPONENTS_KEY);
        }

        lifecycle.addObserver(new GenericLifecycleObserver() {
            @Override
            public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_START) {
                    mAllowingSavingState = true;
                } else if (event == Lifecycle.Event.ON_STOP) {
                    mAllowingSavingState = false;
                }
            }
        });

        mRestored = true;
    }
	
	
	//保存数据
    @MainThread
    void performSave(@NonNull Bundle outBundle) {
        Bundle components = new Bundle();
        if (mRestoredState != null) {
            components.putAll(mRestoredState);
        }
        for (Iterator<Map.Entry<String, SavedStateProvider>> it =
                mComponents.iteratorWithAdditions(); it.hasNext(); ) {
            Map.Entry<String, SavedStateProvider> entry1 = it.next();
            components.putBundle(entry1.getKey(), entry1.getValue().saveState());
        }
        outBundle.putBundle(SAVED_COMPONENTS_KEY, components);
    }	


```


在创建 viewModel 的时候，解析、注册数据  SavedStateHandleController 来控制

```
SavedStateViewModelFactory

        SavedStateHandleController controller = SavedStateHandleController.create(
                mSavedStateRegistry, mLifecycle, key, mDefaultArgs);
        try {
            T viewmodel;
            if (isAndroidViewModel && mApplication != null) {
                viewmodel = constructor.newInstance(mApplication, controller.getHandle());
            } else {
                viewmodel = constructor.newInstance(controller.getHandle());
            }
            viewmodel.setTagIfAbsent(TAG_SAVED_STATE_HANDLE_CONTROLLER, controller);

```

```
    static SavedStateHandleController create(SavedStateRegistry registry, Lifecycle lifecycle,
            String key, Bundle defaultArgs) {
        Bundle restoredState = registry.consumeRestoredStateForKey(key);   //取出数据
        SavedStateHandle handle = SavedStateHandle.createHandle(restoredState, defaultArgs); //真正viewModel中使用
        SavedStateHandleController controller = new SavedStateHandleController(key, handle);
        controller.attachToLifecycle(registry, lifecycle);  //设置保存数据的回调
        tryToAddRecreator(registry, lifecycle);
        return controller;
    }
	
	
	

```

```
SavedStateRegistry

	//取出数据
    public Bundle consumeRestoredStateForKey(@NonNull String key) {
        if (!mRestored) {
            throw new IllegalStateException("You can consumeRestoredStateForKey "
                    + "only after super.onCreate of corresponding component");
        }
        if (mRestoredState != null) {
            Bundle result = mRestoredState.getBundle(key);
            mRestoredState.remove(key);
            if (mRestoredState.isEmpty()) {
                mRestoredState = null;
            }
            return result;
        }
        return null;
    }

```


```
SavedStateHandle
	
	//将数据解析成viewModel 的格式，并由用户处理
    static SavedStateHandle createHandle(@Nullable Bundle restoredState,
            @Nullable Bundle defaultState) {
        if (restoredState == null && defaultState == null) {
            return new SavedStateHandle();
        }

        Map<String, Object> state = new HashMap<>();
        if (defaultState != null) {
            for (String key : defaultState.keySet()) {
                state.put(key, defaultState.get(key));
            }
        }

        if (restoredState == null) {
            return new SavedStateHandle(state);
        }

        ArrayList keys = restoredState.getParcelableArrayList(KEYS);
        ArrayList values = restoredState.getParcelableArrayList(VALUES);
        if (keys == null || values == null || keys.size() != values.size()) {
            throw new IllegalStateException("Invalid bundle passed as restored state");
        }
        for (int i = 0; i < keys.size(); i++) {
            state.put((String) keys.get(i), values.get(i));
        }
        return new SavedStateHandle(state);
    }


```


```

SavedStateHandleController
	//注册保存数据的回调
    void attachToLifecycle(SavedStateRegistry registry, Lifecycle lifecycle) {
        if (mIsAttached) {
            throw new IllegalStateException("Already attached to lifecycleOwner");
        }
        mIsAttached = true;
        lifecycle.addObserver(this);
        registry.registerSavedStateProvider(mKey, mHandle.savedStateProvider());
    }

```


```

//保存数据回调
	private final SavedStateProvider mSavedStateProvider = new SavedStateProvider() {
        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public Bundle saveState() {
            // Get the saved state from each SavedStateProvider registered with this
            // SavedStateHandle, iterating through a copy to avoid re-entrance
            Map<String, SavedStateProvider> map = new HashMap<>(mSavedStateProviders);
            for (Map.Entry<String, SavedStateProvider> entry : map.entrySet()) {
                Bundle savedState = entry.getValue().saveState();
                set(entry.getKey(), savedState);
            }
            // Convert the Map of current values into a Bundle
            Set<String> keySet = mRegular.keySet();
            ArrayList keys = new ArrayList(keySet.size());
            ArrayList value = new ArrayList(keys.size());
            for (String key : keySet) {
                keys.add(key);
                value.add(mRegular.get(key));
            }

            Bundle res = new Bundle();
            // "parcelable" arraylists - lol
            res.putParcelableArrayList("keys", keys);
            res.putParcelableArrayList("values", value);
            return res;
        }
    };

```


