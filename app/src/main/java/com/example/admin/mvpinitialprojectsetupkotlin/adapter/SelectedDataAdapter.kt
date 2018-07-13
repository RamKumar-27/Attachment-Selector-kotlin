package com.example.admin.mvpinitialprojectsetupkotlin.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.app.AppConstants
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.SelectedItemModel
import java.util.*

class SelectedDataAdapter(val context: Context) : RecyclerView.Adapter<SelectedDataAdapter.FolderDetailViewHolder>() {

    private var audioList: ArrayList<SelectedItemModel>?
    private val inflater: LayoutInflater

    init {
        audioList = ArrayList()
        inflater = LayoutInflater.from(context)
    }

    fun setImageList(itemList: List<SelectedItemModel>) {
        if (audioList == null) return

        audioList!!.clear()
        audioList!!.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderDetailViewHolder {

        val view = inflater.inflate(R.layout.item_selected, parent, false)
        return FolderDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderDetailViewHolder, position: Int) {
        val item = audioList!![position]
        holder.setDataToView(item)
    }

    override fun getItemCount(): Int {
        return audioList!!.size
    }

    inner class FolderDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.iv_audio_ext)
        lateinit var tvExtention: ImageView
        @BindView(R.id.tv_audio_title)
        lateinit var tvTitle: TextView
        @BindView(R.id.tv_size)
        lateinit var tvAudioSize: TextView
        @BindView(R.id.img_clear)
        lateinit var imgClear: ImageView


        init {
            ButterKnife.bind(this, itemView)
        }


        fun setDataToView(item: SelectedItemModel) {
            tvTitle.text = item.file!!.name
            tvAudioSize.text = item.size
            if (item.type == AppConstants.TYPE_IMAMGE || item.type == AppConstants.TYPE_VIDEO)
                Glide.with(context)
                        .load(item.file!!.absolutePath)
                        .apply(RequestOptions().centerCrop())
                        .into(tvExtention)
            else if (item.type == AppConstants.TYPE_AUDIO)
                tvExtention.setImageResource(R.drawable.ic_audio_file)
            else if (item.type == AppConstants.TYPE_PDF)
                tvExtention.setImageResource(R.drawable.ic_pdf)
            else if (item.type == AppConstants.TYPE_DOC || item.type == AppConstants.TYPE_DOC)
                tvExtention.setImageResource(R.drawable.ic_doc)

        }


    }
}
