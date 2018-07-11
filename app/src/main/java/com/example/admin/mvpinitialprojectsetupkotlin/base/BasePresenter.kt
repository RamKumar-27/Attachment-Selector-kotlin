package com.example.admin.mvpinitialprojectsetupkotlin.base

interface BasePresenter<in V : BaseView> {
    fun setView(view: V)

    fun destroyView()

    fun destroy()
}