package com.example.admin.mvpinitialprojectsetupkotlin.base


interface ResponseHandler<in T> {

    fun onResponse(responseParser: T)

    fun onFailure(message: String)

    fun getDataFromDB(dbData: T)



}
