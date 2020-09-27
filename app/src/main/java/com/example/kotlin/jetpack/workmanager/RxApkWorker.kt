package com.example.kotlin.jetpack.workmanager

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Rx worker
 *
 *   请注意，RxWorker.createWork() 在主线程上调用，但默认情况下会在后台线程上订阅返回值。
 *   您可以替换 RxWorker.getBackgroundScheduler() 来更改订阅线程。
 *
 */
class RxApkWorker(appContext: Context, params: WorkerParameters) :
    RxWorker(appContext, params) {


    override fun createWork(): Single<Result> {

        return Observable.range(0, 100)
            .flatMap { return@flatMap Observable.just(it) }
            .toList()
            .map { Result.success() }
    }


}