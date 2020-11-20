package com.wan7451.dex;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import dalvik.system.DexClassLoader;

/**
 * 插件化
 */
public class PlugIn {

    public static final String TARGET_INTENT = "TARGET_INTENT";

    public static void hookAMS() throws Exception {

        //1. 获取ActivityManager的IActivityManagerSingleton对象(类型是Singleton)
        Class<?> clazz = Class.forName("android.app.ActivityManager");
        Field iActivityManagerSingletonField = clazz.getDeclaredField("IActivityManagerSingleton");
        iActivityManagerSingletonField.setAccessible(true);
        Object singleton = iActivityManagerSingletonField.get(null);
        //2. 获取Singleton的mInstance对象(其实是IActivityManager)
        Class<?> singletonClass = Class.forName("android.util.Singleton");
        Field mInstanceField = singletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object mInstance = mInstanceField.get(singleton);
        //3. 创建代理的iActivityManager对象mInstanceProxy
        Class<?> iActivityManager = Class.forName("android.app.IActivityManager");
        Object mInstanceProxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{iActivityManager}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //5. 修改Intent
                        if (method.getName().equals("startActivity")) {
                            int index = 0;
                            for (int i = 0; i < args.length; i++) {
                                if (args[i] instanceof Intent) {
                                    index = i;
                                    break;
                                }
                            }
                            //将Intent替换为代理
                            Intent intent = (Intent) args[index];
                            Intent intentProxy = new Intent();
                            intentProxy.setComponent(new ComponentName("包名", "代理Activity"));
                            intentProxy.putExtra(TARGET_INTENT, intent);
                            args[index] = intentProxy;
                        }
                        return method.invoke(mInstance, args);
                    }
                });
        //4. 将mInstance替换为mInstanceProxy
        mInstanceField.set(singleton, mInstanceProxy);
    }


    public static void hookHandler() throws Exception {
        //1. 获取ActivityThread对象
        Class<?> clazz = Class.forName("android.app.ActivityThread");
        Field sCurrentActivityThreadField = clazz.getDeclaredField("sCurrentActivityThread");
        sCurrentActivityThreadField.setAccessible(true);
        Object activityThread = sCurrentActivityThreadField.get(null);
        //2. 获取Handler对象
        Field mHField = clazz.getDeclaredField("mH");
        mHField.setAccessible(true);
        Handler mH = (Handler) mHField.get(activityThread);

        //3. 创建 Handler.Callback
        Handler.Callback callback = new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 100:
                        try {
                            //ActivityClientRecord
                            Field intentField = msg.obj.getClass().getDeclaredField("intent");
                            intentField.setAccessible(true);
                            Intent intentProxy = (Intent) intentField.get(msg.obj);
                            Intent intent = intentProxy.getParcelableExtra(TARGET_INTENT);
                            //取出原始的intent
                            if (intent != null) {
                                intentField.set(msg.obj, intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
                return false;
            }
        };
        //4. 将Callback设置到Handler
        Class<?> handlerClass = Class.forName("android.os.Handler");
        Field mCallbackField = handlerClass.getDeclaredField("mCallback");
        mCallbackField.setAccessible(true);
        mCallbackField.set(mH, callback);
    }

    public static void loadClass(Context context, String dexPath) throws Exception {
        Class<?> classLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
        Field pathListField = classLoaderClass.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
        Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        //1.宿主的dexElements
        ClassLoader pathClassLoader = context.getClassLoader();
        Object hostPathList = pathListField.get(pathClassLoader);
        Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);
        //2. 插件的dexElements
        ClassLoader pluginClassLoader = new DexClassLoader(dexPath,
                context.getCacheDir().getAbsolutePath(),
                null, pathClassLoader);
        Object pluginPathList = pathListField.get(pluginClassLoader);
        Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);
        //3.合并
        Object[] newElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(),
                hostDexElements.length + pluginDexElements.length);
        System.arraycopy(hostDexElements, 0, newElements, 0, hostDexElements.length);
        System.arraycopy(pluginDexElements, 0, newElements, hostDexElements.length, pluginDexElements.length);
        //4.替换
        dexElementsField.set(hostPathList, newElements);
    }
}
