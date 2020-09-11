package com.example.kotlin.jetpack.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kotlin.R


class Nav2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nav2, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val age = Nav1FragmentArgs.fromBundle(it).age
            Toast.makeText(context, "age  $age", Toast.LENGTH_SHORT).show()
        }

    }

}