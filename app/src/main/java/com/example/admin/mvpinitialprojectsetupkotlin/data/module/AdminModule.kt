package com.example.admin.mvpinitialprojectsetupkotlin.data.module

import com.example.admin.mvpinitialprojectsetupkotlin.data.model.SampleModel
import io.realm.annotations.RealmModule


@RealmModule(classes = [SampleModel::class])
class AdminModule {

}
