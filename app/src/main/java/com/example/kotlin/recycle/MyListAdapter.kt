package com.example.kotlin.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.jetpack.hilt.toast

class MyListAdapter() : ListAdapter<MyListItem, MyListHolder>(Diff()) {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListHolder {
        val inflater = inflater ?: LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return MyListHolder(view)
    }

    override fun onBindViewHolder(holder: MyListHolder, position: Int) {
        holder.fillData(getItem(position))
    }

    fun setData(list:List<MyListItem>){
        val data = mutableListOf<MyListItem>()
        data.addAll(currentList)
        data.addAll(list)
        submitList(data)
    }

}

class MyListHolder(var view: View) : RecyclerView.ViewHolder(view) {
    fun fillData(item: MyListItem?) {
        data=item
        (view as? TextView)?.text = item?.name ?: ""
    }

    private var data:MyListItem?=null

    init {
        view.setOnClickListener { v->
            data?.let {
                toast(v.context,it.name)
            }
        }
    }
}

class Diff : DiffUtil.ItemCallback<MyListItem>() {
    override fun areItemsTheSame(oldItem: MyListItem, newItem: MyListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MyListItem, newItem: MyListItem): Boolean {
        return oldItem == newItem
    }
}

data class MyListItem(var id: Long = 0, var name: String = "")