package com.example.admin.mvpinitialprojectsetupkotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.HeaderItemModel
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel
import com.example.admin.mvpinitialprojectsetupkotlin.utils.SectionedRecyclerViewAdapter

import java.util.ArrayList

class RecyclerViewSectionAdapter(private val context: Context) : SectionedRecyclerViewAdapter<RecyclerView.ViewHolder>() {


    private var headerList = ArrayList<HeaderItemModel>()
    private var childMap: Map<String, List<ImageDataModel>>? = null

    override val sectionCount: Int
        get() = headerList.size


    public fun setDataList(headerList: ArrayList<HeaderItemModel>?, childMap: Map<String, List<ImageDataModel>>?) {
        if (childMap == null || headerList == null)
            return

        this.headerList = headerList
        this.childMap = childMap
    }

    override fun getItemCount(section: Int): Int {
        return childMap!![headerList[section].dateString]!!.size

    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, section: Int) {

        val sectionName = headerList[section].dateString
        val sectionViewHolder = holder as SectionViewHolder
        sectionViewHolder.tvHeaderDate.text = sectionName

        //        sectionViewHolder.sectionTitle.setText(sectionName);
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, section: Int, relativePosition: Int, absolutePosition: Int) {

        val itemsInSection = childMap!![headerList[section].dateString]


        val itemViewHolder = holder as ItemViewHolder

        Glide.with(context)
                .load(childMap!![headerList[section].dateString]!!.get(relativePosition).imagePath)
                .apply(RequestOptions().centerCrop())
                .into(itemViewHolder.imageView)

        itemViewHolder.tvGradient.visibility = View.GONE
        itemViewHolder.tvSize.visibility = View.GONE


        //        String itemName = itemsInSection.get(relativePosition);
        //        itemViewHolder.itemTitle.setText(itemName);

        // Try to put a image . for sample i set background color in xml layout file
        // itemViewHolder.itemImage.setBackgroundColor(Color.parseColor("#01579b"));
    }

    override fun onCreateViewHolder(parent: ViewGroup, header: Boolean): RecyclerView.ViewHolder {
        var v: View? = null
        if (header) {
            v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_header, parent, false)
            return SectionViewHolder(v)
        } else {
            v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_images, parent, false)
            return ItemViewHolder(v)
        }

    }


    // SectionViewHolder Class for Sections
    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.tv_date_header)
        lateinit var tvHeaderDate: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.iv_images)
        lateinit var imageView: ImageView
        @BindView(R.id.tv_gradient)
        lateinit var tvGradient: TextView
        @BindView(R.id.tv_folder_name)
        lateinit var tvSize: TextView

        init {
            ButterKnife.bind(this, itemView)
        }


    }
}