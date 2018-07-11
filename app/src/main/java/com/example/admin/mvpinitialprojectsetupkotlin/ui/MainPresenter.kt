package com.example.admin.mvpinitialprojectsetupkotlin.ui

import com.example.admin.mvpinitialprojectsetupkotlin.app.AppController
import com.example.admin.mvpinitialprojectsetupkotlin.base.AbstractBasePresenter
import com.example.admin.mvpinitialprojectsetupkotlin.data.repo.AdminRepo
import com.example.admin.mvpinitialprojectsetupkotlin.data.response.SampleResponseParser

class MainPresenter : AbstractBasePresenter<MainView>() {

    private var mainView: MainView? = null

    override fun setView(view: MainView) {
        mainView = view
        adminRepo = AppController.getInstanse()!!.getAdminRepo()
    }

    override fun onResponse(responseParser: Any) {
        mainView!!.showLoading()

    }

    override fun onFailure(message: String) {
        mainView!!.showLoading()
    }

    fun getApiCall() {
        mainView!!.showLoading()
        adminRepo!!.getApiList(this)
    }

    override fun getDataFromDB(dbData: Any) {
        mainView!!.hideLoading()
        if (dbData is SampleResponseParser)
            mainView!!.responseList(dbData.data!!)
    }
}