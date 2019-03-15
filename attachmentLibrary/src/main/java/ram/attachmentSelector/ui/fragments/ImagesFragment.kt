package ram.attachmentSelector.ui.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.squareup.otto.Subscribe
import com.truizlop.sectionedrecyclerview.SectionedSpanSizeLookup
import io.reactivex.Flowable
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.fragment_images.*
import ram.attachmentSelector.R
import ram.attachmentSelector.adapter.FolderAdapter
import ram.attachmentSelector.adapter.ImageSectionGridAdapter
import ram.attachmentSelector.adapter.ImagesListAdapter
import ram.attachmentSelector.app.AttachmentApplication
import ram.attachmentSelector.base.BaseFragment
import ram.attachmentSelector.base.MainThreadBus
import ram.attachmentSelector.data.eventBus.*
import ram.attachmentSelector.data.model.FolderItem
import ram.attachmentSelector.data.model.HeaderItemModel
import ram.attachmentSelector.data.model.ImageDataModel
import ram.attachmentSelector.utils.DateTimeUtils
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ImagesFragment : BaseFragment(), FolderAdapter.ClickManager, RadioGroup.OnCheckedChangeListener, ImageSectionGridAdapter.onGridImageClickedListner, ImagesListAdapter.onFolderImageClickedListner {

    override fun onGridSectionClicked(imageModel: List<ImageDataModel>) {

    }


    private var recAdapter: ImagesListAdapter? = null
    private var folderAdapter: FolderAdapter? = null
    private var allImageList: List<ImageDataModel>? = null
    private var allIFolderItem: List<FolderItem>? = null
    private var isFolderDetailEnable: Boolean? = false
    private var bus: MainThreadBus? = null
    private var sectionAdapter: ImageSectionGridAdapter? = null
    private var headerList = ArrayList<HeaderItemModel>()
    private var stringListMap = HashMap<String, List<ImageDataModel>>()
    private var layoutManager: GridLayoutManager? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_images, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bus = AttachmentApplication.getInstanse()!!.getBus()
        bus!!.register(this)

        folderAdapter = FolderAdapter(activity!!, this)
        sectionAdapter = ImageSectionGridAdapter(activity!!, this)

        rg_image_type.setOnCheckedChangeListener(this)
        rg_image_type.check(R.id.rb_all_image)
        bus!!.post(RequestDataEvent(true))

    }

    @Subscribe
    fun reciveAllImages(event: ImagesDataEvent) {
        allImageList = event.imgList
        convertToMap()
    }

    @Subscribe
    fun reciveAllFolders(event: FolderDataEvent) {
        allIFolderItem = event.folderList
    }

    /*
    * this method can convert list of data to Hashmap grouped by date
    * */
    private fun convertToMap() {
        Flowable.just<List<ImageDataModel>>(allImageList).flatMapIterable { list -> list }
                .groupBy { imageDataModel -> imageDataModel.getDate() }
                .flatMapSingle { dateImageDataModelGroupedFlowable ->
                    dateImageDataModelGroupedFlowable.toList()
                }.toMap(Function<List<ImageDataModel>, String> { list ->
                    val modelItem = list[0]
                    modelItem.dateCreated!!
                }).subscribe { stringListMap, throwable ->
                    setToAdapter(stringListMap);
                    Log.e("ddd", stringListMap.size.toString() + "")
                }
    }

    private fun setToAdapter(stringListMap: Map<String, List<ImageDataModel>>) {

        this.stringListMap.clear()
        headerList.clear()
        stringListMap.keys.forEach { headerList.add(HeaderItemModel(it, DateTimeUtils.convertToDate(it)!!, false)) }

        Collections.sort(headerList, object : Comparator<HeaderItemModel> {
            override fun compare(lhs: HeaderItemModel?, rhs: HeaderItemModel?): Int {
                return rhs!!.date!!.compareTo(lhs!!.date)

            }

        })
        this.stringListMap.putAll(stringListMap)
        setSectionRecDatas()

    }

    private fun setSectionRecDatas() {
        layoutManager = GridLayoutManager(activity, 3)
        val lookup = SectionedSpanSizeLookup(sectionAdapter, layoutManager)
        layoutManager!!.spanSizeLookup = lookup
        images_rec_view.adapter = sectionAdapter
        images_rec_view.layoutManager = layoutManager

        sectionAdapter!!.setImageDatas(stringListMap, headerList)
    }


    override fun onCheckedChanged(group: RadioGroup?, p1: Int) {
        when (group!!.checkedRadioButtonId) {
            R.id.rb_all_image -> {
                setSectionRecDatas()
                isFolderDetailEnable = false
            }
            R.id.rb_by_folder -> setFolderImage()
        }
    }

    override fun onGridImageClicked(imageModel: ImageDataModel) {
        bus!!.post(imageModel)
    }

    override fun onFolderImageClicked(imageModel: ImageDataModel) {
        bus!!.post(imageModel)
        allImageList!!.forEach {
            if (it.imgId.equals(imageModel.imgId))
                it.selected = imageModel.selected
        }
    }


    private fun setFolderImage() {
        layoutManager = GridLayoutManager(activity, 3)
        images_rec_view.layoutManager = layoutManager

        isFolderDetailEnable = false
        images_rec_view.adapter = folderAdapter
        folderAdapter!!.setFolderItemList(allIFolderItem!!)
    }


    private fun setAllImages(itemList: List<ImageDataModel>) {
        recAdapter = ImagesListAdapter(activity!!, false, this)
        images_rec_view.adapter = recAdapter
        recAdapter!!.setImageList(itemList)
    }


    override fun onItemClick(folderItem: FolderItem) {
        isFolderDetailEnable = true
        bus!!.post(RequestFolderDataEvent(true, folderItem.folderId!!))
    }

    @Subscribe
    fun reciveFolderDetailImages(event: FolderDetailImagesDataEvent) {
        setAllImages(event.imgList)
    }

    @Subscribe
    public fun onHomeBackPressed(event: HomeBackPressEvent) {
        if (isFolderDetailEnable!!)
            setFolderImage()
        else activity!!.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bus!!.unregister(this)
    }
}