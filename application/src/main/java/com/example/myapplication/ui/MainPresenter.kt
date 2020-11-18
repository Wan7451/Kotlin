package com.example.myapplication.ui

import android.app.Application
import android.app.DownloadManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.core.base.IBasePresenter
import com.example.core.net.MyObserver
import com.example.myapplication.api.getApi
import com.example.myapplication.entity.Image
import com.example.myapplication.entity.ImageList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainPresenter(app: Application, handle: SavedStateHandle) : IBasePresenter(app, handle) {

    private val model = MainModel()

    private var query: String = ""
    private var currPage = 0

    val list = MutableLiveData<ImageList>()

    fun loadList(query: String, showLoading: Boolean = false) {
        currPage = 0
        this.query = query
        model.load(query, currPage)
            .subscribe(object : MyObserver<ImageList>(if (showLoading) view?.get() else null) {
                override fun onNext(t: ImageList) {
                    t.currPage = currPage
                    list.value = t
                    currPage++
                }

                override fun onError(e: Throwable) {
                    view?.get()?.showError(e.message ?: "")
                }
            })
    }


    fun addList() {
        model.load(query, currPage)
            .subscribe(object : MyObserver<ImageList>() {
                override fun onNext(t: ImageList) {
                    t.currPage = currPage
                    list.value = t
                    currPage++
                }

                override fun onError(e: Throwable) {
                    view?.get()?.showError(e.message ?: "")
                }
            })
    }

    private var subscribe: Disposable? = null

    fun search(s: String) {
        if (s.isEmpty()) return
        subscribe = Observable.just(s)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                loadList(it)
            }
    }

    override fun onCleared() {
        super.onCleared()
        subscribe?.dispose()
    }

}