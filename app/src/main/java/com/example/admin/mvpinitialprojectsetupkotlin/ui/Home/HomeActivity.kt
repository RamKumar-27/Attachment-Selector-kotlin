package com.example.admin.mvpinitialprojectsetupkotlin.ui.Home

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.adapter.HomePagerAdapter
import com.example.admin.mvpinitialprojectsetupkotlin.app.AppController
import com.example.admin.mvpinitialprojectsetupkotlin.base.BaseActivity
import com.example.admin.mvpinitialprojectsetupkotlin.base.MainThreadBus
import com.example.admin.mvpinitialprojectsetupkotlin.data.HomeBackPressEvent
import com.example.admin.mvpinitialprojectsetupkotlin.ui.fragments.AudioFragment
import com.example.admin.mvpinitialprojectsetupkotlin.ui.fragments.ImagesFragment
import com.example.admin.mvpinitialprojectsetupkotlin.ui.fragments.VideosFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    val homePagerAdapter = HomePagerAdapter(supportFragmentManager)
    var fragmentList = ArrayList<Fragment>()
    var bus: MainThreadBus? = null

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_home)
        bus = AppController.getInstanse()!!.getBus()
        bus!!.register(this)
        view_pager.adapter = homePagerAdapter
        tab_layout.setupWithViewPager(view_pager)
        fragmentList.add(ImagesFragment())
        fragmentList.add(VideosFragment())
        fragmentList.add(AudioFragment())
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
}