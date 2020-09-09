package com.example.kotlin.jetpack.lifecycle

import androidx.lifecycle.LifecycleService

class LifeCycleServicee : LifecycleService() {
    init {
        lifecycle.addObserver(MyComponent())
    }
}