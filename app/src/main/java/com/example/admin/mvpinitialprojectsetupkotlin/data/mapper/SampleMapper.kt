package com.example.admin.mvpinitialprojectsetupkotlin.data.mapper

import com.example.admin.mvpinitialprojectsetupkotlin.data.model.SampleModel
import com.example.admin.mvpinitialprojectsetupkotlin.data.response.DataItem
import io.realm.RealmResults

object SampleMapper {
    fun getModelFromPOJO(it: DataItem): SampleModel {
        val sampleModel = SampleModel()
        sampleModel.avatar = it.avatar
        sampleModel.firstName = it.firstName
        sampleModel.id = it.id
        sampleModel.lastName = it.lastName
        return sampleModel
    }

    fun convertModelToPOJO(dbList: RealmResults<SampleModel>?): List<DataItem>? {
        val list = ArrayList<DataItem>()
        dbList!!.forEach {
            val dataItem = DataItem()
            dataItem.avatar = it.avatar
            dataItem.firstName = it.firstName
            dataItem.id = it.id
            dataItem.lastName = it.lastName
            list.add(dataItem)

        }
        return list
    }

}