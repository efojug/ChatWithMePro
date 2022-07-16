package com.efojug.chatwithmepro

import android.app.Application
import android.content.Context
import android.widget.Toast
import dove.desu.chatwithme.network.process.RegisterProcess
import kotlin.system.exitProcess

class MyApplication : Application() {
    //静态变量
    companion object {
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = baseContext
    }
}