package com.example.core.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    constructor(parent: ViewGroup, @LayoutRes layoutId: Int) :
            this(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    abstract fun fillData(data: T)

}
