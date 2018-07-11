package com.example.admin.mvpinitialprojectsetupkotlin.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import android.widget.VideoView

import com.bumptech.glide.Glide
import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.request.RequestOptions


class ImagesListAdapter(val context: Context, val isVideo: Boolean) : RecyclerView.Adapter<ImagesListAdapter.FolderDetailViewHolder>() {

    private var folderDetailItemList: ArrayList<ImageDataModel>?
    private val inflater: LayoutInflater

    init {
        folderDetailItemList = ArrayList()
        inflater = LayoutInflater.from(context)
    }

    fun setImageList(itemList: List<ImageDataModel>) {
        if (folderDetailItemList == null) return
        folderDetailItemList!!.clear()
        folderDetailItemList!!.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderDetailViewHolder {

        val view = inflater.inflate(R.layout.item_images, parent, false)
        return FolderDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderDetailViewHolder, position: Int) {
        val item = folderDetailItemList!![position]
        holder.setDataToView(item)
    }

    override fun getItemCount(): Int {
        return folderDetailItemList!!.size
    }

    inner class FolderDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.iv_images)
        lateinit var imageView: ImageView
        @BindView(R.id.tv_gradient)
        lateinit var tvGradient: TextView
        @BindView(R.id.tv_folder_name)
        lateinit var tvSize: TextView
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


        fun setDataToView(item: ImageDataModel) {
            Glide.with(context)
                    .load(item.imagePath)
                    .apply(RequestOptions().centerCrop())
                    .into(imageView)
            if (isVideo) {
                tvGradient.visibility = View.VISIBLE
                tvSize.visibility = View.VISIBLE
                tvSize.text = item.duration + " / " + item.size
            } else {
                tvGradient.visibility = View.GONE
                tvSize.visibility = View.GONE
            }
            if (item.selected == true)
                animator!!.start();
            else animationView.progress = 0f;


        }

        @OnClick(R.id.parent_layout)
        fun onImageClick() {
            startCheckAnimation()
        }

        fun startCheckAnimation() {
            val selectedItem = folderDetailItemList!!.get(adapterPosition)

            if (animationView.progress == 0f) {
                animator!!.start();
                selectedItem.selected = true
            } else {
                animationView.progress = 0f;
                selectedItem.selected = false

            }
        }

    }


}
