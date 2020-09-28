package com.example.kotlin.jetpack.paging

import android.content.Context
import androidx.paging.PagedList
import com.example.kotlin.module.Article
import com.example.kotlin.net.BaseData
import com.example.kotlin.net.BaseList
import com.example.kotlin.net.api
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticleBoundaryCallback(var context: Context) : PagedList.BoundaryCallback<Article>() {
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        getFirst()
    }

    private fun getFirst() {
        page=0
        api().articleList(page).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : Observer<BaseData<BaseList<Article>>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseData<BaseList<Article>>) {
                    if (t.errorCode != 0) return
                    t.data?.let {
                        ArticleDB.getDB(context).getArticleDao().insertList(it.datas)
                    }
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    /**
     * 当数据库中最后一项数据被加载时，则会调用其onItemAtEndLoaded函数
     */
    override fun onItemAtEndLoaded(itemAtEnd: Article) {
        super.onItemAtEndLoaded(itemAtEnd)
        loadMore()
    }

    private var page = 0
    private fun loadMore() {
        page++
        api().articleList(page).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : Observer<BaseData<BaseList<Article>>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseData<BaseList<Article>>) {
                    if (t.errorCode != 0) return
                    t.data?.let {
                        ArticleDB.getDB(context).getArticleDao().insertList(it.datas)
                    }
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

    }


}