package com.example.kotlin.jetpack.room

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.kotlin.R
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataBaseActivity : AppCompatActivity() {

    private val model by viewModels<DataBaseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_base)
        model.load4ViewModel()?.observe(this, {
            model.showToast("${it.size}")
        })
    }

    fun add(v: View) {
        model.add4Rx()
    }

    //主键为条件
    fun delete(v: View) {
        model.delete()
    }

    fun update(v: View) {
        model.update()
    }

    fun query(v: View) {
        model.load4Scope()
        //model.load4Rx()
    }

}