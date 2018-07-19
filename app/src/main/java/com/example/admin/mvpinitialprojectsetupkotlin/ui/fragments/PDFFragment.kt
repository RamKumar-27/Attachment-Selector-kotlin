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
import com.example.admin.mvpinitialprojectsetupkotlin.app.AppController
import com.example.admin.mvpinitialprojectsetupkotlin.base.BaseFragment
import com.example.admin.mvpinitialprojectsetupkotlin.base.MainThreadBus
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel
import com.example.admin.mvpinitialprojectsetupkotlin.utils.AudioHelper
import com.example.admin.mvpinitialprojectsetupkotlin.utils.DocumentFileHelper
import kotlinx.android.synthetic.main.fragment_videos.*

class PDFFragment : BaseFragment(), DocumentAdapter.onDocumentClickedListner {
    private var bus: MainThreadBus? = null

    private var recAdapter: DocumentAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bus = AppController.getInstanse()!!.getBus()
        bus!!.register(this)
        rec_view_video.layoutManager = LinearLayoutManager(activity!!)
        recAdapter = DocumentAdapter(activity!!, this)
        rec_view_video.adapter = recAdapter
        recAdapter!!.setImageList(DocumentFileHelper.getDocumentListFromStorage(activity!!, "pdf",
                AppConstants.TYPE_PDF), R.drawable.ic_pdf)
    }
    override fun onDocumentClicked(imageModel: ImageDataModel) {
        bus!!.post(imageModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bus!!.unregister(this)
    }
}