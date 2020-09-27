package com.example.kotlin.net

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BaseData<T>(
    @SerializedName("data")
    val `data`: T? = null,
    @SerializedName("errorCode")
    val errorCode: Int = -1,
    @SerializedName("errorMsg")
    val errorMsg: String = ""
)


