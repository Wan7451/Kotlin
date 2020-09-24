package com.example.kotlin.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * 对应数据库中的一张表
 */

@Entity(tableName = "student") //默认表名与类名相同
class Student {
    @PrimaryKey(autoGenerate = true) //主键 自增长
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER) //字段名 数据库中的类型
    var id: Int = 0

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    var name: String = ""

    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.INTEGER)
    var age: Int = 0

    @Ignore
    var tmp: String = "临时字段"    //忽略临时字段

    @Ignore
    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }

    constructor(id: Int, name: String, age: Int) {
        this.id = id
        this.name = name
        this.age = age
    }


}
