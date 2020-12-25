package com.example.kotlin.recycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.databinding.ActivityMyListAdapterBinding

class MyListAdapterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyListAdapterBinding
    private lateinit var adapter: MyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyListAdapterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycleView.adapter = MyListAdapter().also { adapter = it }
        binding.recycleView.layoutManager = LinearLayoutManager(this)

        val list = mutableListOf<MyListItem>()
        var index = 0L
        for (i in 0..10) {
            list.add(MyListItem(index++, "$index"))
        }
        adapter.setData(list)
        binding.test.setOnClickListener {
            val new = if (isAdd) {
                listOf(MyListItem(100, "100"))
            } else {
                list.removeAt(0)
                list
            }
            adapter.setData(new)
            isAdd = !isAdd
        }
    }

    private var isAdd = true
}