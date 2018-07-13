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
import com.example.admin.mvpinitialprojectsetupkotlin.R
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.HeaderItemModel
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel
import java.util.*

class AudioAdapter(val context: Context, val listner: onAudioClickedListner) : RecyclerView.Adapter<AudioAdapter.FolderDetailViewHolder>() {

    private var audioList: ArrayList<ImageDataModel>?
    private val inflater: LayoutInflater

    init {
        audioList = ArrayList()
        inflater = LayoutInflater.from(context)
    }

    interface onAudioClickedListner {
        fun onAudioClicked(imageModel: ImageDataModel)
    }

    fun setImageList(itemList: List<ImageDataModel>) {
        if (audioList == null) return

        Collections.sort(itemList, object : Comparator<ImageDataModel> {
            override fun compare(lhs: ImageDataModel?, rhs: ImageDataModel?): Int {
                return rhs!!.imageTitle!!.compareTo(lhs!!.imageTitle!!)

            }

        })

        audioList!!.clear()
        audioList!!.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderDetailViewHolder {

        val view = inflater.inflate(R.layout.item_audio, parent, false)
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

        @BindView(R.id.tv_audio_ext)
        lateinit var tvExtention: TextView
        @BindView(R.id.tv_audio_title)
        lateinit var tvTitle: TextView
        @BindView(R.id.tv_size)
        lateinit var tvAudioSize: TextView
        @BindView(R.id.tv_duration)
        lateinit var tvDuration: TextView
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
            tvTitle.text = item.imageTitle
            tvAudioSize.text = item.size
            tvDuration.text = item.duration
            var nameArr = item.imageTitle!!.split("\\.".toRegex())
            tvExtention.text = nameArr[nameArr.size - 1]
            if (item.selected == true)
                animator!!.start();
            else animationView.progress = 0f;
        }

        @OnClick(R.id.parent_layout)
        fun onItemClicked() {
            startCheckAnimation()
        }

        fun startCheckAnimation() {
            val selectedItem = audioList!!.get(adapterPosition)

            if (animationView.progress == 0f) {
                animator!!.start();
                selectedItem.selected = true
            } else {
                animationView.progress = 0f;
                selectedItem.selected = false

            }
            if (listner != null)
                listner.onAudioClicked(selectedItem)
        }


    }
}
