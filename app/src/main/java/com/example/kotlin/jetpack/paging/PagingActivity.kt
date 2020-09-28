package com.example.kotlin.jetpack.paging

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.R
import kotlinx.android.synthetic.main.activity_paging.*

/****
 *
 *
 * 每个 PagedList 实例都会从对应的 DataSource 对象加载应用数据的最新快照
 * 
 * 注意：PagedList 具有内容不可变特性。
 * 这意味着即使可以将新内容加载到 PagedList 的实例中，但加载的项目本身不会在加载后立即改变。
 * 因此，如果 PagedList 中的内容更新，则 PagedListAdapter 对象会收到包含更新后信息的全新 PagedList。
 *
 *
 */
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