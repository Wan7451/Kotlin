package com.example.kotlin.jetpack.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.kotlin.module.Article
import com.example.kotlin.net.BaseData
import com.example.kotlin.net.BaseList
import com.example.kotlin.net.api
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticleDataSourceFactory : DataSource.Factory<Int, Article>() {

    val dataSource=MutableLiveData<DataSource<Int, Article>>()

    override fun create(): DataSource<Int, Article> {
        return ArticleDataSource().also { dataSource.postValue(it) }
    }
}


class ArticleDataSource : PageKeyedDataSource<Int, Article>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        //params.requestedLoadSize  //配置设置的 初始化数量
        api().articleList(0).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseData<BaseList<Article>>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseData<BaseList<Article>>) {
                    if (t.errorCode != 0) return
                    t.data?.let {
                        callback.onResult(it.datas, it.offset, it.total, null, it.curPage)
                    }
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        api().articleList(params.key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseData<BaseList<Article>>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseData<BaseList<Article>>) {
                    if (t.errorCode != 0) return
                    t.data?.let {
                        callback.onResult(
                            it.datas,
                            if (it.curPage == it.pageCount) null else it.curPage
                        )
                    }
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }
}