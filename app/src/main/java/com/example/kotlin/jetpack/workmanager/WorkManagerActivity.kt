package com.example.kotlin.jetpack.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.kotlin.R
import com.example.kotlin.jetpack.viewmodel.SavedStateModel
import java.util.concurrent.TimeUnit

class WorkManagerActivity : AppCompatActivity() {

    private val model  by viewModels<SavedStateModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)

        val params = Data.Builder().putString("URL", "url").build()
        //任务触发条件
        val cons = Constraints.Builder()
            //.setRequiresCharging(true)  //接通电源
            //.setRequiredNetworkType(NetworkType.CONNECTED)
            //.setRequiresBatteryNotLow(true)
            //.setRequiresDeviceIdle(true) 设备空闲
            .build()
        //workRequest
        val request = OneTimeWorkRequestBuilder<DownApkWorker>() //只执行一次
//        val request = PeriodicWorkRequestBuilder<DownApkWorker>(15, TimeUnit.MINUTES) //重复执行
            .setConstraints(cons) //触发条件
            //.setInitialDelay(10, TimeUnit.SECONDS) //延迟10s
            .setBackoffCriteria(  //退避算法
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MICROSECONDS
            )
            .setInputData(params) //传参
            .addTag("down") //tag
            .build()

        //提交
        WorkManager.getInstance(this).enqueue(request)

        //获取任务
        WorkManager.getInstance(this).getWorkInfosByTag("down") //uuid
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id).observe(this, Observer {
            Log.e(">>>>>>>", "workinfo ${it.state}")
            if (it.state == WorkInfo.State.SUCCEEDED) {
                Log.e(">>>>>>>", "result ${it.outputData.getString("OUT")}")
            }
            Log.e(">>>>>>>", "progress ${it.progress.getInt("P", 0)}")
        })

        //WorkManager.getInstance(this).beginWith(request).then(request).
        model.save()
    }

    override fun onDestroy() {
        super.onDestroy()
        //取消任务
        WorkManager.getInstance(this).cancelAllWorkByTag("down")
    }
}

/**
 * 1. 针对的是不需要及时完成的任务
 * 2. 保证任务一定会执行   数据库记录任务的信息与数据
 * 3. 兼容范围广         最低兼容API 14
 *
 *
 * API 23以上版本  JobScheduler
 * API 23以下版本  AlarmManager + BroadcastReceivers
 *
 *
 * 移除默认初始化
 *
 * <provider
 *   android:name="androidx.work.impl.WorkManagerInitializer"
 *   android:authorities="${applicationId}.workmanager-init"
 *   tools:node="remove" />
 *
 *
 * 可以定义的最短重复间隔是 15 分钟（与 JobScheduler API 相同）。
 *
 *
 * 原生系统可用
 *
 */
