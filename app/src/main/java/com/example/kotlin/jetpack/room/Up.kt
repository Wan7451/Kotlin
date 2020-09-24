package com.example.kotlin.jetpack.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Up {
    @PrimaryKey
    var id: Int = 0
    var content: String = ""
}
