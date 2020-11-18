package com.example.core.recycle

import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi

/**
 * 圆角
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class RoundOutlineProvider(var radius: Float) : ViewOutlineProvider() {

    override fun getOutline(view: View?, outline: Outline?) {
        if (view == null || outline == null) return

        val rect=Rect()
        view.getGlobalVisibleRect(rect)
        val w=view.width
        val h=view.height
        val selfRect=Rect(0,0,w,h)
        outline.setRoundRect(selfRect,radius)
    }
}