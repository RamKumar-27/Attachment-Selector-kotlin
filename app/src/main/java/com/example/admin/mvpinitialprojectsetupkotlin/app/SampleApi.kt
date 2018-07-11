package com.example.admin.mvpinitialprojectsetupkotlin.app

import com.example.admin.mvpinitialprojectsetupkotlin.data.response.SampleResponseParser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface SampleApi {

    @GET("users?page=2")
    fun getSampleListApi(): Call<SampleResponseParser>

}