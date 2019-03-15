package ram.attachmentSelector.ui.Home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.activity_home.*
import ram.attachmentSelector.R
import ram.attachmentSelector.adapter.HomePagerAdapter
import ram.attachmentSelector.adapter.SelectedDataAdapter
import ram.attachmentSelector.app.AppConstants
import ram.attachmentSelector.app.AttachmentApplication
import ram.attachmentSelector.base.AttachmentSelectedListener
import ram.attachmentSelector.base.BaseActivity
import ram.attachmentSelector.base.MainThreadBus
import ram.attachmentSelector.data.eventBus.*
import ram.attachmentSelector.data.model.FragmentItemModel
import ram.attachmentSelector.data.model.ImageDataModel
import ram.attachmentSelector.data.model.SelectedItemModel
import ram.attachmentSelector.ui.fragments.*
import ram.attachmentSelector.utils.FileUtils
import ram.attachmentSelector.utils.GalleryHelper


class HomeActivity : BaseActivity() {

    private val homePagerAdapter = HomePagerAdapter(supportFragmentManager)
    private var fragmentList = ArrayList<FragmentItemModel>()
    private var bus: MainThreadBus? = null
    private var selectedList = ArrayList<SelectedItemModel>()
    private var selectedId = ArrayList<String>()
    private var totalSize = ""


    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_home)

        tv_selected.setOnClickListener { onSelectedClick() }
        tv_done.setOnClickListener { onDoneClick() }

        bus = AttachmentApplication.getInstanse()!!.getBus()
        bus!!.register(this)

        view_pager.adapter = homePagerAdapter
        tab_layout.setupWithViewPager(view_pager)

        loadFragments()

    }

    companion object {
        private var listener: AttachmentSelectedListener? = null
        fun getCallingIntent(context: Context, isImages: Boolean, isVideo: Boolean,
                             isAudio: Boolean, isPDF: Boolean, isDoc: Boolean,
                             listener: AttachmentSelectedListener) {
            context.startActivity(Intent(context, HomeActivity::class.java)
                    .putExtra(AppConstants.IS_IMAGE_NEED, isImages)
                    .putExtra(AppConstants.IS_VIDEO_NEED, isVideo)
                    .putExtra(AppConstants.IS_AUDIO_NEED, isAudio)
                    .putExtra(AppConstants.IS_PDF_NEED, isPDF)
                    .putExtra(AppConstants.IS_DOC_NEED, isDoc))
            this.listener = listener
        }
    }

    /*
    * load the required fragment and their corresponding titles in the list
    * */
    private fun loadFragments() {
        val bundle = intent.extras
        if (bundle.getBoolean(AppConstants.IS_IMAGE_NEED))
            fragmentList.add(FragmentItemModel(ImagesFragment(), "Images"))
        if (bundle.getBoolean(AppConstants.IS_VIDEO_NEED))
            fragmentList.add(FragmentItemModel(VideosFragment(), "Videos"))
        if (bundle.getBoolean(AppConstants.IS_AUDIO_NEED))
            fragmentList.add(FragmentItemModel(AudioFragment(), "Audio"))
        if (bundle.getBoolean(AppConstants.IS_PDF_NEED))
            fragmentList.add(FragmentItemModel(PDFFragment(), "Pdf"))
        if (bundle.getBoolean(AppConstants.IS_DOC_NEED))
            fragmentList.add(FragmentItemModel(DOCFragment(), "doc/docx"))

        if (fragmentList.size == 1)
            tab_layout.visibility = View.GONE
        homePagerAdapter.setFragmentList(fragmentList)
        view_pager.offscreenPageLimit = fragmentList.size
    }

    @Subscribe
    fun reciveDataRequest(event: RequestDataEvent) {
        if (event.needData)
            postDatasToFragments()

    }

    @Subscribe
    fun reciveFolderDataRequest(event: RequestFolderDataEvent) {
        if (event.needData)
            bus!!.post(FolderDetailImagesDataEvent(GalleryHelper.getImagesFromFolderID(this,
                    event.folderId, selectedId)))
    }

    private fun postDatasToFragments() {
        bus!!.post(ImagesDataEvent(GalleryHelper.gettAllImages(this, selectedId)))
        bus!!.post(FolderDataEvent(GalleryHelper.getImageFolders(this)))
    }

    override fun onBackPressed() {
        bus!!.post(HomeBackPressEvent(true))
    }

    override fun onDestroy() {
        super.onDestroy()
        bus!!.unregister(this)
    }


    /*
    * this method can show the selected attachment list in a dialog with total selected data size
    * */
    fun onSelectedClick() {

        val dialog = Dialog(this, R.style.dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_selected_datas)
        val rec_view_selected = dialog.findViewById<RecyclerView>(R.id.rec_view_selected)
        val tvClear = dialog.findViewById<TextView>(R.id.tv_clear_all)
        val tvTotalSize = dialog.findViewById<TextView>(R.id.tv_total_size)

        tvTotalSize.text = totalSize

        val selectedAdapter = SelectedDataAdapter(this, object : SelectedDataAdapter.onSelectedDataClicked {
            override fun onDeleteImage(position: Int) {
                selectedList.removeAt(position)
                selectedId.removeAt(position)

            }

        })
        tvClear.setOnClickListener {
            selectedList.clear()
            selectedId.clear()
            homePagerAdapter.notifyDataSetChanged()
            selected_layout.visibility = View.GONE
            dialog.dismiss()
        }
        rec_view_selected.layoutManager = LinearLayoutManager(this)
        rec_view_selected.adapter = selectedAdapter
        selectedAdapter.setImageList(selectedList)

        dialog.show()
    }

    private fun onDoneClick() {
        if (listener != null) {
            listener!!.onSelectedAttachments(selectedList)
            finish()
        }
    }

    @Subscribe
    fun onSelectedEvent(event: ImageDataModel) {
        if (event.selected == true) {
            selectedList.add(SelectedItemModel(event.file, event.mimeType!!, event.imgId, event.type))
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

        var totalsize: Long = 0
        selectedList.forEach { totalsize += it.file!!.length() }
        totalSize = FileUtils.getSizeFromFile(totalsize)
        Log.e("selected count", (selectedList.size).toString())

    }


}