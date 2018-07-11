package com.example.admin.mvpinitialprojectsetupkotlin.ui.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.adapter.ImagesListAdapter
import com.example.admin.mvpinitialprojectsetupkotlin.base.BaseFragment
import com.example.admin.mvpinitialprojectsetupkotlin.utils.VideoHelper
import kotlinx.android.synthetic.main.fragment_videos.*

class VideosFragment : BaseFragment() {


    private var recAdapter: ImagesListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rec_view_video.layoutManager = GridLayoutManager(activity, 3)
        recAdapter = ImagesListAdapter(activity!!,true)
        rec_view_video.adapter = recAdapter
        recAdapter!!.setImageList(VideoHelper.gettAllVideos(activity!!))
    }
}