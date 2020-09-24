package com.example.kotlin.jetpack.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Data Access Object  数据访问对象
 *
 *
 * 查询类型	   协程	        RxJava	                                    Guava	                    生命周期
 * 可观察读取	   Flow<T>	    Flowable<T>、Publisher<T>、Observable<T>	    无	                        LiveData<T>
 * 单次读取	   suspend fun	Single<T>、Maybe<T>	                        ListenableFuture<T>	        无
 * 单次写入	   suspend fun	Single<T>、Maybe<T>、Completable<T>	        ListenableFuture<T>	        无
 *
 */
@Dao
interface StudentDao {
    @Insert
    fun insert(s: Student)

    /**
     * 主键为条件
     */
    @Update
    fun update(s: Student)

    /**
     * 主键为条件
     */
    @Delete
    fun delete(s: Student)

    @Query("SELECT * FROM student")
    fun loadAll(): List<Student>

    @Query("SELECT * FROM student WHERE id = :id")
    fun getStudent(id: Int): Student


    @Insert
    fun insert4Rx(s: Student): Completable

    @Query("SELECT * FROM student")
    fun loadAll4Rx(): Flowable<List<Student>>


    @Query("SELECT * FROM student")
    suspend fun loadAll4Scope(): List<Student>


    @Query("SELECT * FROM student")
    fun loadAll4LiveData(): LiveData<List<Student>>
}