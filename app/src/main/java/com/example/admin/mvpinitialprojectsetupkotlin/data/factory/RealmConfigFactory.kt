package com.example.admin.mvpinitialprojectsetupkotlin.data.factory

import io.realm.RealmConfiguration

class RealmConfigFactory {

    companion object {
        fun createAdminRealmRealmConfiguration(): RealmConfiguration? {
            return RealmConfiguration.Builder().name("SampleModule").build()
        }
    }
}