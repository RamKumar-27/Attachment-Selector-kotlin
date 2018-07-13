package com.example.admin.mvpinitialprojectsetupkotlin.ui.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.adapter.ImagesListAdapter
import com.example.admin.mvpinitialprojectsetupkotlin.app.AppController
import com.example.admin.mvpinitialprojectsetupkotlin.base.BaseFragment
import com.example.admin.mvpinitialprojectsetupkotlin.base.MainThreadBus
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel
import com.example.admin.mvpinitialprojectsetupkotlin.utils.VideoHelper
import kotlinx.android.synthetic.main.fragment_videos.*

class VideosFragment : BaseFragment(), ImagesListAdapter.onFolderImageClickedListner {


    private var recAdapter: ImagesListAdapter? = null
    private var bus: MainThreadBus? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bus = AppController.getInstanse()!!.getBus()
        bus!!.register(this)
        rec_view_video.layoutManager = GridLayoutManager(activity, 3)
        recAdapter = ImagesListAdapter(activity!!, true, this)
        rec_view_video.adapter = recAdapter
        recAdapter!!.setImageList(VideoHelper.gettAllVideos(activity!!))
    }

    override fun onFolderImageClicked(imageModel: ImageDataModel) {
        bus!!.post(imageModel)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        bus!!.unregister(this)
    }

}