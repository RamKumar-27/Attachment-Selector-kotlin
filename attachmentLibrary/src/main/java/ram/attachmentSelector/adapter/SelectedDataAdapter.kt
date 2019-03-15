package ram.attachmentSelector.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_selected.view.*
import ram.attachmentSelector.R
import ram.attachmentSelector.app.AppConstants
import ram.attachmentSelector.data.model.SelectedItemModel
import java.util.*

class SelectedDataAdapter(val context: Context, val listner: onSelectedDataClicked) : RecyclerView.Adapter<SelectedDataAdapter.FolderDetailViewHolder>() {

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

    interface onSelectedDataClicked {
        fun onDeleteImage(position: Int)
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

        private var tvExtention = itemView.iv_audio_ext
        private var tvTitle = itemView.tv_audio_title
        private var tvAudioSize = itemView.tv_size
        private var imgClear = itemView.img_clear


        init {
            imgClear.setOnClickListener { onImageDeleteClicked() }
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

        fun onImageDeleteClicked() {
            if (listner != null) {
                listner.onDeleteImage(adapterPosition)
                audioList!!.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
        }


    }
}
