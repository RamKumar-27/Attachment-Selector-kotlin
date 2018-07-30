package com.example.admin.mvpinitialprojectsetupkotlin.app

import android.app.Application
import com.example.admin.mvpinitialprojectsetupkotlin.base.MainThreadBus

class AppController : Application() {

    companion object {
        var appController: AppController? = null

        fun getInstanse(): AppController? {
            return appController
        }
    }
    private var bus: MainThreadBus? = null


    override fun onCreate() {
        super.onCreate()
        appController = this
        bus = MainThreadBus()
    }

    fun getBus(): MainThreadBus {
        return bus!!
    }


}