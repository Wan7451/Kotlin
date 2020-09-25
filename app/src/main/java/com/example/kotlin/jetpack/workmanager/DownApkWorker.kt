package com.example.kotlin.jetpack.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class DownApkWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        if(isStopped){
            //被停止   Worker 停止后，从 Worker.doWork() 返回什么已不重要；Result 将被忽略。
            return Result.success()
        }

        //工作线程执行
        Log.e(">>>>>>", "thread - ${Thread.currentThread().name}")

        val url = inputData.getString("URL") //获取传参
        Log.e(">>>>>>", "params - $url")
        Thread.sleep(3000)

        setProgressAsync(Data.Builder().putInt("P",1).build())//执行进度
        Thread.sleep(3000)
        val out = Data.Builder().putString("OUT", "out").build() //返回结果
        return Result.success(out)
//        return Result.failure()
//        return Result.retry() //需要重新执行
    }
}