package com.example.admin.sample.ui.Home

import android.app.Application
import ram.attachmentSelector.app.AttachmentApplication

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AttachmentApplication().init(this)
    }
}