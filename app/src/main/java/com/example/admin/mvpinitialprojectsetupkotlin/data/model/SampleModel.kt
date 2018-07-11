package com.example.admin.mvpinitialprojectsetupkotlin.data.model

import io.realm.RealmObject

open class SampleModel : RealmObject() {
    var lastName: String? = ""
    var id: Int = 0
    var avatar: String? = ""
    var firstName: String? = ""
}