package ram.attachmentSelector.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_images.view.*
import ram.attachmentSelector.R
import ram.attachmentSelector.data.model.HeaderItemModel
import ram.attachmentSelector.data.model.ImageDataModel
import java.util.*

class ImageSectionGridAdapter(private val context: Context, private val listner: onGridImageClickedListner) :
        SectionedRecyclerViewAdapter<ImageSectionGridAdapter.HeaderViewHolder,
                ImageSectionGridAdapter.ChildViewHolder, ImageSectionGridAdapter.FotterViewHolder>() {


    private var map: Map<String, List<ImageDataModel>>? = null
    private var headerList: List<HeaderItemModel>? = null
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
        map = HashMap()
        headerList = ArrayList()

    }

    interface onGridImageClickedListner {
        fun onGridImageClicked(imageModel: ImageDataModel)
        fun onGridSectionClicked(imageModel: List<ImageDataModel>)
    }

    fun setImageDatas(map: Map<String, List<ImageDataModel>>?, headerlist: List<HeaderItemModel>?) {
        if (map == null || headerlist == null)
            return
        this.map = map
        this.headerList = headerlist
        notifyDataSetChanged()
    }

    override fun getSectionCount(): Int {
        return headerList!!.size
    }

    override fun getItemCountForSection(section: Int): Int {
        return map!![headerList!![section].dateString]!!.size
    }

    override fun hasFooterInSection(section: Int): Boolean {
        return false
    }

    override fun onCreateSectionHeaderViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(inflater.inflate(R.layout.item_header, parent, false))
    }

    override fun onCreateSectionFooterViewHolder(parent: ViewGroup, viewType: Int): FotterViewHolder? {
        return null
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        return ChildViewHolder(inflater.inflate(R.layout.item_images, parent, false))
    }

    override fun onBindSectionHeaderViewHolder(holder: HeaderViewHolder, section: Int) {
        holder.setHeaderDatas(headerList!![section])
        holder.tvDateHeader.setOnClickListener(View.OnClickListener {
            holder.startCheckAnimation(section)
//            secti(section)

        })

    }

    override fun onBindSectionFooterViewHolder(holder: FotterViewHolder, section: Int) {

    }

    override fun onBindItemViewHolder(holder: ChildViewHolder, section: Int, position: Int) {
        val item = map!![headerList!![section].dateString]!!.get(position)
        holder.setChildDatas(item)
        holder.parentLayout.setOnClickListener(View.OnClickListener {
            holder.startCheckAnimation(section, position)
        })
    }


    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var tvDateHeader = itemView.findViewById<TextView>(R.id.tv_date_header)
        private var animationView = itemView.findViewById<LottieAnimationView>(R.id.lottie_animation)
        private var animator: ValueAnimator? = null


        init {
            animator = ValueAnimator.ofFloat(0f, 1f)
            animator!!.addUpdateListener { valueAnimator ->
                animationView.setProgress(valueAnimator.animatedValue as Float)
            }
        }

        fun setHeaderDatas(headerItemModel: HeaderItemModel) {
            tvDateHeader.text = headerItemModel.dateString

        }

        fun startCheckAnimation(section: Int) {


            val selectedItem = headerList!!.get(section)

            if (animationView.progress == 0f) {
                animator!!.start();
                selectedItem.isSelected = true
            } else {
                animationView.progress = 0f;
                selectedItem.isSelected = false

            }
            map!!.get(headerList!!.get(section).dateString)!!.forEach {
                it.selected = selectedItem.isSelected
            }

//            if (listner != null)
//                listner.onGridImageClicked(selectedItem)

        }

    }

    inner class FotterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var ivImages = itemView.iv_images
        private var tvGradient = itemView.tv_gradient
        private var tvFolderName = itemView.tv_folder_name
        private var animationView = itemView.lottie_animation
        var parentLayout = itemView.parent_layout
        private var animator: ValueAnimator? = null


        init {
            animator = ValueAnimator.ofFloat(0f, 1f)
            animator!!.addUpdateListener { valueAnimator ->
                animationView.setProgress(valueAnimator.animatedValue as Float)
            }

        }


        fun startCheckAnimation(section: Int, child: Int) {


            val selectedItem = map!!.get(headerList!!.get(section).dateString)!!.get(child)

            if (animationView.progress == 0f) {
                animator!!.start();
                selectedItem.selected = true
            } else {
                animationView.progress = 0f;
                selectedItem.selected = false

            }
            if (listner != null)
                listner.onGridImageClicked(selectedItem)

        }


        fun setChildDatas(item: ImageDataModel) {

            Glide.with(context)
                    .load(item.imagePath)
                    .apply(RequestOptions().centerCrop())
                    .into(ivImages)
            tvGradient.visibility = View.GONE
            tvFolderName.visibility = View.GONE
            if (item.selected == true)
                animator!!.start();
            else animationView.progress = 0f;

        }
    }
}
