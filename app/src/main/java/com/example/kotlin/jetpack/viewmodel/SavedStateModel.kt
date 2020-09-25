package com.example.kotlin.jetpack.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle

/***
 * https://juejin.im/post/6874136956347875342
 *
 * 1.  由于屏幕旋转等配置更改的原因导致 Activity 被销毁    viewModel
 * 2.  由于系统资源限制导致 Activity 被销毁              SavedStateHandle
 *     打开开发者模式中"不保留活动"的选项   触发 2
 */
class SavedStateModel(app: Application, var handle: SavedStateHandle) : AndroidViewModel(app) {


    fun save() {
        Log.e(">>>>>>", "SavedStateHandle   ${handle.get<String>("A")}")
        handle.set("A", "A")
    }
}

