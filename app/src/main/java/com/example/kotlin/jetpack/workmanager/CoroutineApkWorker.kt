package com.example.kotlin.jetpack.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

/**
 * 协程 worker
 *
 * 请注意，CoroutineWorker.doWork() 是一个“挂起”函数。
 * 不同于 Worker，此代码不会在 Configuration 中指定的 Executor 上运行，而是默认为 Dispatchers.Default。
 * 您可以提供自己的 CoroutineContext 来自定义这个行为。
 *
 */
class CoroutineApkWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {


    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO){
            //doWork
        }
        Result.success()
    }
}