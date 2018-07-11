package com.example.admin.mvpinitialprojectsetupkotlin.ui.fragments

import android.content.Intent.getIntent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.adapter.ImagesListAdapter
import com.example.admin.mvpinitialprojectsetupkotlin.app.AppConstants
import com.example.admin.mvpinitialprojectsetupkotlin.base.BaseFragment
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel
import com.example.admin.mvpinitialprojectsetupkotlin.utils.GalleryHelper
import kotlinx.android.synthetic.main.fragment_folder_detail.*

class FolderDetailFragment : BaseFragment() {

    private var recAdapter: ImagesListAdapter? = null
    private var allImageList: List<ImageDataModel>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_folder_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rec_view_folder_detail.layoutManager = GridLayoutManager(activity, 3)
        allImageList = GalleryHelper.getImagesFromFolderID(activity!!,
                arguments!!.getString(AppConstants.FOLDER_ID))

        recAdapter = ImagesListAdapter(activity!!, false)
        rec_view_folder_detail.adapter = recAdapter

        recAdapter!!.setImageList(allImageList!!)

    }
}