package com.example.kotlin.jetpack.viewmodel

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner

class TestView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var model: MyModel? = null

    init {
        ViewTreeLifecycleOwner.get(this)?.lifecycle?.addObserver(object :LifecycleObserver{

        })
        viewTreeObserver?.isAlive

        findViewTreeViewModelStoreOwner()?.let {
            model = ViewModelProvider(it).get(MyModel::class.java)
        }
    }
}