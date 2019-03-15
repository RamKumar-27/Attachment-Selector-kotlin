package ram.attachmentSelector.ui.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_videos.*
import ram.attachmentSelector.R
import ram.attachmentSelector.adapter.ImagesListAdapter
import ram.attachmentSelector.app.AttachmentApplication
import ram.attachmentSelector.base.BaseFragment
import ram.attachmentSelector.base.MainThreadBus
import ram.attachmentSelector.data.model.ImageDataModel
import ram.attachmentSelector.utils.VideoHelper

class VideosFragment : BaseFragment(), ImagesListAdapter.onFolderImageClickedListner {


    private var recAdapter: ImagesListAdapter? = null
    private var bus: MainThreadBus? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bus = AttachmentApplication.getInstanse()!!.getBus()
        bus!!.register(this)
        rec_view_video.layoutManager = GridLayoutManager(activity, 3)
        recAdapter = ImagesListAdapter(activity!!, true, this)
        rec_view_video.adapter = recAdapter
        recAdapter!!.setImageList(VideoHelper.gettAllVideos(activity!!))

        if (recAdapter!!.itemCount == 0) {
            tv_no_videos.visibility = View.VISIBLE
            rec_view_video.visibility = View.GONE
        } else {
            rec_view_video.visibility = View.VISIBLE
            tv_no_videos.visibility = View.GONE
        }
    }

    override fun onFolderImageClicked(imageModel: ImageDataModel) {
        bus!!.post(imageModel)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        bus!!.unregister(this)
    }

}