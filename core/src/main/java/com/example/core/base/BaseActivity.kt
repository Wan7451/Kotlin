package com.example.core.base

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), IBaseView {

    abstract val model: IBasePresenter?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model?.apply {
            attach(this@BaseActivity)
            lifecycle.addObserver(this)
        }
        setContentView(getLayoutId())
        initView()
        initData()
    }

    protected open fun initView() {}
    protected open fun initData() {}

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private var dialog: ProgressDialog? = null

    override fun showDialog(msg: String) {

    }

    override fun showLoading() {
        if (isFinishing) return
        if (dialog == null) {
            dialog = ProgressDialog(this)
            dialog!!.setMessage("loading")
        }
        dialog!!.show()
    }

    override fun hintLoading() {
        if (isFinishing) return
        dialog?.dismiss()
    }
}