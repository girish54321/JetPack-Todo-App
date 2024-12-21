package com.example.resreqapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        Graph.provide(this)
        super.onCreate()
    }
}