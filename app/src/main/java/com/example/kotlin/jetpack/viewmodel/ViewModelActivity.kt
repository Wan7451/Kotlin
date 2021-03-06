package com.example.kotlin.jetpack.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import com.example.kotlin.R
import kotlinx.android.synthetic.main.activity_view_model.*

class ViewModelActivity : AppCompatActivity() {

    private val model by viewModels<MyModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)
        model.doSomethings()
        Log.e(">>>>>>>>>","activity ${model.hashCode()}")
        Handler(Looper.getMainLooper()).postDelayed({
            supportFragmentManager.beginTransaction()
                .add(ModelFragment(),"test")
                .commitNowAllowingStateLoss()
        },1000)

        ViewTreeLifecycleOwner.set(container,this)
        ViewTreeViewModelStoreOwner.set(container,this)
        ViewModelProvider(this).get(MyModel::class.java)
    }


    override fun onStop() {
        super.onStop()
        if(isFinishing){
            Log.e(">>>>>>>","onStop ${System.currentTimeMillis()}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(">>>>>>>","onDestroy ${System.currentTimeMillis()}")
    }
}