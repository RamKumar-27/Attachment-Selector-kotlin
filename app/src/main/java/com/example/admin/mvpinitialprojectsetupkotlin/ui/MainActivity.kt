package com.example.admin.mvpinitialprojectsetupkotlin.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.base.BaseActivity
import com.example.admin.mvpinitialprojectsetupkotlin.data.response.DataItem
import com.example.admin.mvpinitialprojectsetupkotlin.utils.UiUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {


    var presenter: MainPresenter? = MainPresenter()
    var nameListAdapter: NameListAdapter? = null

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_main)
        rec_view.layoutManager = LinearLayoutManager(this)
        nameListAdapter = NameListAdapter(this)
        rec_view.adapter = nameListAdapter
        presenter!!.setView(this)
        presenter!!.getApiCall()

    }

    override fun responseList(list: List<DataItem>) {
        nameListAdapter!!.setNameList(list)
        UiUtils.showToast(context(), "Success")
    }

    override fun showLoading() {
        showProgress()
    }

    override fun hideLoading() {
        hideProgress()
    }

    override fun showRetry() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideRetry() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        UiUtils.showToast(context(), message)
    }

    override fun context(): Context {
        return this
    }
}
