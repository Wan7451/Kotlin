package com.example.kotlin.jetpack.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import com.example.kotlin.module.Article


private val CL = object : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

class ArticleHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_article_list, parent, false)
) {
    fun bind(item: Article?) {
        item?.let {
            (itemView as? TextView)?.let { v ->
                v.text = it.title
                return
            }
        }
        (itemView as? TextView)?.let { v ->
            v.text = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            return
        }
    }
}

class ArticleAdapter : PagedListAdapter<Article, ArticleHolder>(CL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        return ArticleHolder(parent)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

