package com.example.admin.mvpinitialprojectsetupkotlin.ui

import com.example.admin.mvpinitialprojectsetupkotlin.base.LoadDataView
import com.example.admin.mvpinitialprojectsetupkotlin.data.response.DataItem

interface MainView : LoadDataView {

    fun responseList(list: List<DataItem>)
}