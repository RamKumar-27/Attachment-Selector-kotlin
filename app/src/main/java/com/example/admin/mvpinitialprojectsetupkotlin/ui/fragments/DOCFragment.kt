package com.example.admin.mvpinitialprojectsetupkotlin.ui.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.adapter.AudioAdapter
import com.example.admin.mvpinitialprojectsetupkotlin.adapter.DocumentAdapter
import com.example.admin.mvpinitialprojectsetupkotlin.adapter.ImagesListAdapter
import com.example.admin.mvpinitialprojectsetupkotlin.app.AppConstants
import com.example.admin.mvpinitialprojectsetupkotlin.base.BaseFragment
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel
import com.example.admin.mvpinitialprojectsetupkotlin.utils.AudioHelper
import com.example.admin.mvpinitialprojectsetupkotlin.utils.DocumentFileHelper
import kotlinx.android.synthetic.main.fragment_videos.*
import java.util.ArrayList

class DOCFragment : BaseFragment() {
    private var recAdapter: DocumentAdapter? = null
    private var allfiles: ArrayList<ImageDataModel>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rec_view_video.layoutManager = LinearLayoutManager(activity!!)
        recAdapter = DocumentAdapter(activity!!)
        rec_view_video.adapter = recAdapter
        allfiles = DocumentFileHelper.getDocumentListFromStorage(activity!!, "doc", AppConstants.TYPE_DOC)
        allfiles!!.addAll(DocumentFileHelper.getDocumentListFromStorage(activity!!, "docx", AppConstants.TYPE_DOCX))
        recAdapter!!.setImageList(allfiles!!, R.drawable.ic_doc)
    }
}