package com.example.myapplication.ui

import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.dpF
import com.example.core.loadImage
import com.example.core.recycle.BaseHolder
import com.example.core.recycle.RoundOutlineProvider
import com.example.myapplication.R
import com.example.myapplication.entity.Image

class MainAdapter : RecyclerView.Adapter<MainHolder>() {
    var list = ArrayList<Image>()
    fun addData(data: List<Image>, clear: Boolean = false) {
        if (clear) {
            list.clear()
        }
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(parent)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.fillData(list[position])
    }

    override fun getItemCount(): Int = list.size
}

class MainHolder(parent: ViewGroup) : BaseHolder<Image>(parent, R.layout.item_main) {

    private val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    private val titleView = itemView.findViewById<TextView>(R.id.titleView)

    init {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val provider = RoundOutlineProvider(5.dpF)
            itemView.outlineProvider = provider
            itemView.clipToOutline = true
        }
    }

    override fun fillData(data: Image) {
        imageView.loadImage(data.img, data.thumb)
        titleView.text = data.title
    }
}

