package com.example.kotlin.jetpack.paging

import android.content.Context
import androidx.paging.DataSource
import androidx.room.*
import com.example.kotlin.module.Article


@Database(entities = [Article::class], version = 1)
abstract class ArticleDB : RoomDatabase() {
    companion object {
        fun getDB(context: Context): ArticleDB {
            return Room.databaseBuilder(
                context.applicationContext,
                ArticleDB::class.java,
                "Article"
            )
                .build()
        }
    }

    abstract fun getArticleDao(): ArticleDao
}


@Dao
interface ArticleDao {

    @Query("SELECT * from `Article`")
    fun loadAll(): DataSource.Factory<Int, Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(article: List<Article>)

    @Query("DELETE  from `Article`")
    fun clear()
}