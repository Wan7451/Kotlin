package com.example.kotlin.jetpack.paging

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.Config.MAX_SIZE_UNBOUNDED
import androidx.paging.toLiveData

class ArticleModel(private var app: Application, private var handle: SavedStateHandle) :
    AndroidViewModel(app) {

    private val factory = ArticleDataSourceFactory()

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)        //设置控件占位
        .setPageSize(20)                    //分页大小
        .setPrefetchDistance(3)             //距离底部多少条数据时开始加载下一页
        .setInitialLoadSizeHint(80)         //首次加载数据的数量，为 pageSize 的整数，默认3倍 没啥用
        .setMaxSize(MAX_SIZE_UNBOUNDED)                  //展示的最大数量
        .build()
//    val page = LivePagedListBuilder(factory, config).build()
    val page = factory.toLiveData(config)



    fun invalidate() {
        factory.dataSource.value?.invalidate()
    }
}