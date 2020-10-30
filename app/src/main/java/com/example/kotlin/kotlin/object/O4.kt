package com.example.kotlin.kotlin.`object`

import android.view.View
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import java.util.*

private fun testComponent() {
    val pair = 10 to "A"  //Pair
    var (age, name) = pair
    val triple = Triple(10, "A", 10f)
    val (f, s, t) = triple
}

object O5 {
    fun test() {
    }
}

private val o6 = object : ViewPager.OnPageChangeListener{
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }
}
