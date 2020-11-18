package com.example.core.base

import android.app.Application
import androidx.lifecycle.*
import com.example.core.base.IBaseView
import java.lang.ref.WeakReference

abstract class IBasePresenter(app: Application, var handle: SavedStateHandle) :
    AndroidViewModel(app),
    LifecycleObserver {

    protected var view: WeakReference<IBaseView>? = null

    fun attach(view: IBaseView) {
        this.view = WeakReference(view)
    }

    fun getSavedStateHandle(): SavedStateHandle {
        return handle
    }

    private fun detach() {
        view?.clear()
        view = null
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestroy() {
    }

    override fun onCleared() {
        super.onCleared()
        detach()
    }

}