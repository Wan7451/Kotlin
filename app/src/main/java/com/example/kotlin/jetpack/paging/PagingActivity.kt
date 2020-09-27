package com.example.kotlin.jetpack.paging

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.R
import kotlinx.android.synthetic.main.activity_paging.*

class PagingActivity : AppCompatActivity() {

    private val adapter = ArticleAdapter()
    private val model by viewModels<ArticleRoomModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = adapter

        model.page.observe(this, {
            adapter.submitList(it)
            swipeRefresh.isRefreshing = false
        })
        swipeRefresh.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                model.invalidate()
            }, 500)
        }
    }
}