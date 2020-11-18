package com.example.core.base

interface IBaseView {
    fun showError(msg: String)
    fun showDialog(msg: String)
    fun showLoading()
    fun hintLoading()
}