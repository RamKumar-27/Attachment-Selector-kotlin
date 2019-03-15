package ram.attachmentSelector.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_images.view.*
import ram.attachmentSelector.R
import ram.attachmentSelector.data.model.ImageDataModel
import java.util.*


class ImagesListAdapter(val context: Context, val isVideo: Boolean, val listner: onFolderImageClickedListner) :
        RecyclerView.Adapter<ImagesListAdapter.FolderDetailViewHolder>() {

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

    interface onFolderImageClickedListner {
        fun onFolderImageClicked(imageModel: ImageDataModel)
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

        private var imageView = itemView.iv_images
        private var tvGradient = itemView.tv_gradient
        private var tvSize = itemView.tv_folder_name
        private var animationView = itemView.lottie_animation
        private var animator: ValueAnimator? = null

        init {
            itemView.setOnClickListener { onImageClick() }
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


        fun onImageClick() {
            startCheckAnimation()
        }

        private fun startCheckAnimation() {
            val selectedItem = folderDetailItemList!!.get(adapterPosition)

            if (animationView.progress == 0f) {
                animator!!.start();
                selectedItem.selected = true
            } else {
                animationView.progress = 0f;
                selectedItem.selected = false

            }
            if (listner != null)
                listner.onFolderImageClicked(selectedItem)

        }

    }


}
