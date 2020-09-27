package com.example.kotlin.jetpack.room

import android.app.Application
import android.os.AsyncTask
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Suppress("DEPRECATION")
class DataBaseModel(app: Application) : AndroidViewModel(app) {

    private var dao: StudentDao? = MyDataBase.instance(getApplication()).studentDao()

    fun load4ViewModel(): LiveData<List<Student>>? {
        return dao?.loadAll4LiveData()
    }

    /**
     * 加载数据库文件  需要先建表
     */
    fun loadAssets(){
        Room.databaseBuilder(getApplication(),MyDataBase::class.java,"name")
            .createFromAsset("") //asset
            .createFromFile(File("")) //文件
            .build()

    }

    fun load4Scope() {
        viewModelScope.launch(Dispatchers.Main) {
            val r = withContext(Dispatchers.IO) {
                dao?.loadAll4Scope()
            }
            withContext(Dispatchers.Main) {
                showToast("成功 ${r?.size}")
            }
        }
    }


    fun add4Rx() {
        dao?.let {
            it.insert4Rx(Student("aaaaa", 10))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showToast("成功")
                }
        }
    }

    fun load4Rx() {
        dao?.let {
            it.loadAll4Rx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { r ->
                    showToast("成功 ${r.size}")
                }
        }
    }

    fun add() {
        run {
            dao?.insert(Student("aaaaa", 10))
            dao?.insert(Student("bbbbb", 20))
            dao?.insert(Student("aaaaa", 30))
        }
    }

    fun load() {
        run {
            dao?.loadAll()
        }
    }


    fun delete() {
        run {
            dao?.delete(Student(1, "aaaaa", 300))
        }
    }

    fun update() {
        run {
            dao?.update(Student(1, "aaaaa", 300))
        }
    }

    private fun run(action: () -> Unit) {
        AsyncTask.execute {
            action.invoke()
        }
    }

    fun showToast(content: String) {
        Toast.makeText(getApplication(), content, Toast.LENGTH_SHORT).show()
    }
}