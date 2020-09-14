package com.example.kotlin.jetpack.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.kotlin.R

class LiveDataActivity : AppCompatActivity() {

    private val model by viewModels<LiveDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)
        //不需要手动取消 注册
        model.testData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        model.test()

        model.testData.observeForever(observer)
    }
    private val observer = Observer<String> {
        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.testData.removeObserver(observer)
        //model.testData.removeObservers(this)
    }
}