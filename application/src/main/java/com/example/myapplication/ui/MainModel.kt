package com.example.myapplication.ui

import com.example.core.base.IBaseModel
import com.example.myapplication.api.getApi
import com.example.myapplication.entity.ImageList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainModel : IBaseModel {

    fun load(query: String, page: Int): Observable<ImageList> {
        return getApi().loadImage(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}