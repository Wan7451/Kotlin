package com.example.kotlin.jetpack.room

import androidx.room.Dao
import io.reactivex.Maybe

@Dao
interface UpDao {
    fun add(up: Up): Maybe<Int>
}