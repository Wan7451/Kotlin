package com.example.kotlin.jetpack.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [Student::class, Up::class], version = 2)
abstract class MyDataBase : RoomDatabase() {
    abstract fun studentDao(): StudentDao


    /**
     * 数据库更新思路
     * 1. 增加新表  直接增加即可
     * 2. 更改表字段  Android不支持删除字段
     *    a. 创建符合条件的临时表
     *    b. 将数据从旧表复制到新表
     *    c. 删除旧表
     *    d. 将临时表重命名
     */
    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `Up` (`id` INTEGER NOT NULL,`content` TEXT NOT NULL,PRIMARY KEY(`id`))")
            }
        }

        fun instance(context: Context): MyDataBase {
            return Room.databaseBuilder(context.applicationContext, MyDataBase::class.java, "my.DB")
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}