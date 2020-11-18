package com.example.myapplication.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Image(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("img")
    val img: String = "",
    @SerializedName("_thumb")
    val thumb: String = "",
    @SerializedName("title")
    val title: String = "",
)