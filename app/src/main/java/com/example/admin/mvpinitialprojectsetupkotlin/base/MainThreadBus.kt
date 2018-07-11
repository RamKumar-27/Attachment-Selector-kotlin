package com.example.admin.mvpinitialprojectsetupkotlin.base

import android.os.Handler
import android.os.Looper

import com.squareup.otto.Bus


class MainThreadBus : Bus() {
    private val mainThreadHandler: Handler

    init {
        mainThreadHandler = Handler(Looper.getMainLooper())
    }

    override fun post(event: Any) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mainThreadHandler.post { this@MainThreadBus.post(event) }
        } else {
            super.post(event)
        }
    }
}
