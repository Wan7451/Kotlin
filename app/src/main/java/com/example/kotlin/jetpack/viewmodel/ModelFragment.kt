package com.example.kotlin.jetpack.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.kotlin.R


class ModelFragment : Fragment() {

    //private val mode1 by activityViewModels<MyModel>()
    private val mode2 by viewModels<MyModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_model, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //mode1.doSomethings()
        mode2.doSomethings()
        //Log.e(">>>>>>>", "model1 ${mode1.hashCode()}")
        Log.e(">>>>>>>", "model2 ${mode2.hashCode()}")
    }
}