package com.example.myapplication.entity

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class ImageList(
    @SerializedName("list")
    val list: List<Image> = listOf(),
    @SerializedName("total")
    val total: Int = 0,
    var currPage: Int = 0
)
