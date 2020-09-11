package com.example.kotlin.recycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import kotlinx.android.synthetic.main.activity_layout_manager.*

class LayoutManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_manager)
        recycleView.adapter = MyAdapter()
        recycleView.layoutManager= MyLayoutManager()
    }
}

class MyAdapter : RecyclerView.Adapter<MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val textView = TextView(parent.context)
        val lp = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 200);
        textView.layoutParams = lp
        return MyHolder(textView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.fillTxt("position: $position")
    }

    override fun getItemCount(): Int = 100

}

class MyHolder(var v: TextView) : RecyclerView.ViewHolder(v) {
    fun fillTxt(txt: String) {
        v.text = txt
    }
}