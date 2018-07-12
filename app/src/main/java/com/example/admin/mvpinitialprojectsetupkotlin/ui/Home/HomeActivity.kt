package com.example.admin.mvpinitialprojectsetupkotlin.ui.Home

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.adapter.HomePagerAdapter
import com.example.admin.mvpinitialprojectsetupkotlin.app.AppController
import com.example.admin.mvpinitialprojectsetupkotlin.base.BaseActivity
import com.example.admin.mvpinitialprojectsetupkotlin.base.MainThreadBus
import com.example.admin.mvpinitialprojectsetupkotlin.data.HomeBackPressEvent
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.HeaderItemModel
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.SelectedItemModel
import com.example.admin.mvpinitialprojectsetupkotlin.ui.fragments.*
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.activity_home.*
import android.widget.TextView
import com.example.admin.mvpinitialprojectsetupkotlin.R.style.dialog
import com.example.admin.mvpinitialprojectsetupkotlin.adapter.SelectedDataAdapter


class HomeActivity : BaseActivity() {
    private val homePagerAdapter = HomePagerAdapter(supportFragmentManager)
    private var fragmentList = ArrayList<Fragment>()
    private var bus: MainThreadBus? = null
    private var selectedList = ArrayList<SelectedItemModel>()
    private var selectedId = ArrayList<String>()


    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)
        bus = AppController.getInstanse()!!.getBus()
        bus!!.register(this)
        view_pager.adapter = homePagerAdapter
        tab_layout.setupWithViewPager(view_pager)
        fragmentList.add(ImagesFragment())
        fragmentList.add(VideosFragment())
        fragmentList.add(AudioFragment())
        fragmentList.add(PDFFragment())
        fragmentList.add(DOCFragment())
        homePagerAdapter.setFragmentList(fragmentList)
        view_pager.offscreenPageLimit = fragmentList.size

    }

    override fun onBackPressed() {
        bus!!.post(HomeBackPressEvent(true))
    }

    override fun onDestroy() {
        super.onDestroy()
        bus!!.unregister(this)
    }

    @OnClick(R.id.tv_selected)
    fun onSelectedClick() {
        val dialog = Dialog(this, R.style.dialog)
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_selected)
        dialog.setCancelable(true)

        val tvClear = dialog.findViewById(R.id.tv_clear_all) as TextView
        val tvSize = dialog.findViewById(R.id.tv_clear_all) as TextView
        val recView = dialog.findViewById(R.id.rec_view_selected) as RecyclerView
        val selectedAdapter = SelectedDataAdapter(this)
        recView.layoutManager = LinearLayoutManager(this)
        recView.adapter = selectedAdapter
        selectedAdapter.setImageList(selectedList)

        dialog.show()

    }

    @OnClick(R.id.tv_done)
    fun onDoneClick() {

    }

    @Subscribe
    fun onSelectedEvent(event: ImageDataModel) {
        if (event.selected == true) {
            selectedList.add(SelectedItemModel(event.file, event.mimeType!!, event.imgId,event.type))
            selectedId.add(event.imgId)
        } else {
            selectedId.indexOf(event.imgId)
            selectedList.removeAt(selectedId.indexOf(event.imgId))
            selectedId.remove(event.imgId)

        }
        if (selectedList.size == 0)
            selected_layout.visibility = View.GONE
        else
            selected_layout.visibility = View.VISIBLE
        tv_selected.text = selectedList.size.toString() + " Selected"

        Log.e("selected count", (selectedList.size).toString())
    }
}