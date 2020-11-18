package com.example.myapplication.ui

import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseActivity
import com.example.myapplication.R
import com.example.myapplication.entity.Image
import com.example.myapplication.view.CardItemTouchHelperCallback
import com.example.myapplication.view.CardLayoutManager
import com.example.myapplication.view.OnSwipeListener
import io.reactivex.Observable
import io.reactivex.Observer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity() : BaseActivity() {

    override val model by viewModels<MainPresenter>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        model.loadList("汉堡包", true)
    }

    private val adapter = MainAdapter()

    override fun initView() {
        recycleView.adapter = adapter
        recycleView.itemAnimator = DefaultItemAnimator()
        val cardCallback = CardItemTouchHelperCallback(adapter, adapter.list)
        cardCallback.setOnSwipedListener(listener)
        val touchHelper = ItemTouchHelper(cardCallback)
        val cardLayoutManager = CardLayoutManager(recycleView, touchHelper)
        recycleView.layoutManager = cardLayoutManager
        touchHelper.attachToRecyclerView(recycleView)

        model.list.observe(this) {
            adapter.addData(it.list, it.currPage == 0)
        }
        etInput.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { model.search(it.toString()) }
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }

    private val listener = object : OnSwipeListener<Image> {
        override fun onSwiping(
            viewHolder: RecyclerView.ViewHolder?,
            ratio: Float,
            direction: Int
        ) {
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, t: Image, direction: Int) {
        }

        override fun onSwipedClear() {
            model.addList()
        }

    }
}