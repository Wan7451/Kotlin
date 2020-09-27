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
        api().articleList(0).subscribeOn(Schedulers.io())
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

    override fun onItemAtEndLoaded(itemAtEnd: Article) {
        super.onItemAtEndLoaded(itemAtEnd)
        loadMore()
    }

    private fun loadMore() {
        api().articleList(2).subscribeOn(Schedulers.io())
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