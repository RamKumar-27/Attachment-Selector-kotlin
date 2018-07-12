package com.example.admin.mvpinitialprojectsetupkotlin.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView

import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.HeaderItemModel
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter

import java.util.ArrayList
import java.util.HashMap

import butterknife.BindView
import butterknife.ButterKnife

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

        @BindView(R.id.tv_date_header)
        lateinit var tvDateHeader: TextView
        @BindView(R.id.lottie_animation)
        lateinit var animationView: LottieAnimationView
        private var animator: ValueAnimator? = null


        init {
            ButterKnife.bind(this, itemView)
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

        @BindView(R.id.iv_images)
        lateinit var ivImages: ImageView
        @BindView(R.id.tv_gradient)
        lateinit var tvGradient: TextView
        @BindView(R.id.tv_folder_name)
        lateinit var tvFolderName: TextView
        @BindView(R.id.lottie_animation)
        lateinit var animationView: LottieAnimationView
        @BindView(R.id.parent_layout)
        lateinit var parentLayout: ConstraintLayout
        private var animator: ValueAnimator? = null


        init {
            ButterKnife.bind(this, itemView)
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
