package com.example.kotlin.jetpack.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import com.example.kotlin.R

class ViewModelActivity : AppCompatActivity() {

    //private val model by viewModels<MyModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)
        //model.doSomethings()
        //Log.e(">>>>>>>>>","activity ${model.hashCode()}")
        Handler().postDelayed({
            supportFragmentManager.beginTransaction()
                .add(ModelFragment(),"test")
                .commitNowAllowingStateLoss()
        },1000)
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