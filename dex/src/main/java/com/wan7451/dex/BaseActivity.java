package com.wan7451.dex;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class BaseActivity extends Activity {

    //    @Override
//    public Resources getResources() {
//        Resources resource = PlugIn.getResource(this);
//        return resource == null ? super.getResources() : resource;
//    }
    private ContextThemeWrapper mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = PlugIn.getResource(this);
        mContext = new ContextThemeWrapper(getBaseContext(), 0);
        Class<?> clazz = mContext.getClass();
        try {
            Field mResourcesField = clazz.getDeclaredField("mResources");
            mResourcesField.setAccessible(true);
            mResourcesField.set(mContext,resources);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(mContext).inflate(layoutResID, null);
        super.setContentView(view);
    }
}
