package com.example.core.net

import com.example.core.base.IBaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

open class MyObserver<T>(var v: IBaseView? = null) : Observer<T> {
    override fun onSubscribe(d: Disposable) {
        v?.showLoading()
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
        v?.hintLoading()
    }

    override fun onComplete() {
        v?.hintLoading()
    }
}