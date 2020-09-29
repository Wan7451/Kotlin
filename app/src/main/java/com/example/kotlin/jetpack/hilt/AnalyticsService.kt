package com.example.kotlin.jetpack.hilt

import android.util.Log
import androidx.core.app.ActivityCompat
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Inject


interface AnalyticsService {
    fun analyticsMethods()
}

//class AnalyticsServiceImpl @Inject constructor():AnalyticsService{
//    override fun analyticsMethods() {
//        Log.e(">>>>>>","====analytics===")
//    }
//}
//
//@Module
//@InstallIn(ActivityCompat::class)
//abstract class AnalyticsModule{
//    @Binds
//    abstract fun bindAnalyticsService(s:AnalyticsServiceImpl):AnalyticsService
//}