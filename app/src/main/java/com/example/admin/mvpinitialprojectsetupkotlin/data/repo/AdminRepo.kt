package com.example.admin.mvpinitialprojectsetupkotlin.data.repo

import com.example.admin.mvpinitialprojectsetupkotlin.app.SampleApi
import com.example.admin.mvpinitialprojectsetupkotlin.base.ResponseHandler
import com.example.admin.mvpinitialprojectsetupkotlin.data.mapper.SampleMapper
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.SampleModel
import com.example.admin.mvpinitialprojectsetupkotlin.data.response.DataItem
import com.example.admin.mvpinitialprojectsetupkotlin.data.response.SampleResponseParser
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminRepo(sampleAPi: SampleApi, realmConfiguration: RealmConfiguration?) {


    private var realmConfiguration: RealmConfiguration
    private var sampleAPi: SampleApi

    init {
        this.realmConfiguration = realmConfiguration!!
        this.sampleAPi = sampleAPi!!
    }

    fun getApiList(handler: ResponseHandler<Any>) {
        val call = sampleAPi.getSampleListApi()
        call.enqueue(object : Callback<SampleResponseParser> {
            override fun onResponse(call: Call<SampleResponseParser>?, response: Response<SampleResponseParser>?) {
                if (response!!.isSuccessful)
                    saveResponseToDb(response.body()!!.data, handler)
                else handler.onFailure("Failure")
            }

            override fun onFailure(call: Call<SampleResponseParser>?, t: Throwable?) {
                handler.onFailure(t!!.message as String)
            }


        })
    }

    private fun saveResponseToDb(list: List<DataItem>?, handler: ResponseHandler<Any>) {
        val realm = Realm.getInstance(realmConfiguration)
        try {
            realm.beginTransaction()
            realm.where(SampleModel::class.java).findAll().deleteAllFromRealm()
            realm.commitTransaction()

            list!!.forEach {
                var model = SampleMapper.getModelFromPOJO(it)
                realm.beginTransaction()
                realm.insertOrUpdate(model)
                realm.commitTransaction()
            }

        } finally {
            realm.close()
            getListFromDb(handler)
        }
    }

    private fun getListFromDb(handler: ResponseHandler<Any>) {
        val realm = Realm.getInstance(realmConfiguration)
        var response = SampleResponseParser()
        try {
            realm.beginTransaction()
            response.data = SampleMapper.convertModelToPOJO(realm.where(SampleModel::class.java).findAll())
            realm.commitTransaction()
        } finally {
            realm.close()
            handler.getDataFromDB(response)
        }

    }


}