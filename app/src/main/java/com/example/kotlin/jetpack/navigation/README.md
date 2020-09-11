1.  NavHostFragment
        findNavController(f:Fragment)  查找 controller   
    
NavController 中的view 都有 tag R.id.nav_controller_view_tag                
```
private static NavController getViewNavController(@NonNull View view) {
        Object tag = view.getTag(R.id.nav_controller_view_tag);
        NavController controller = null;
        if (tag instanceof WeakReference) {
            controller = ((WeakReference<NavController>) tag).get();
        } else if (tag instanceof NavController) {
            controller = (NavController) tag;
        }
        return controller;
    }
```                


```
    public static void setViewNavController(@NonNull View view,
            @Nullable NavController controller) {
        view.setTag(R.id.nav_controller_view_tag, controller);
    }
```


https://www.jianshu.com/p/6eecd7db9169   源码解析