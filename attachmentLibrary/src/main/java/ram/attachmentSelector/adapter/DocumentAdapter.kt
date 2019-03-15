package ram.attachmentSelector.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_document.view.*
import ram.attachmentSelector.R
import ram.attachmentSelector.data.model.ImageDataModel

class DocumentAdapter(val context: Context, val listner: onDocumentClickedListner) : RecyclerView.Adapter<DocumentAdapter.FolderDetailViewHolder>() {

    private var audioList: ArrayList<ImageDataModel>?
    private val inflater: LayoutInflater
    private var image: Int = 0

    init {
        audioList = ArrayList()
        inflater = LayoutInflater.from(context)
    }
    interface onDocumentClickedListner {
        fun onDocumentClicked(imageModel: ImageDataModel)
    }

    fun setImageList(itemList: ArrayList<ImageDataModel>, image: Int) {
        if (audioList == null) return

//        Collections.sort(itemList, object : Comparator<ImageDataModel> {
//            override fun compare(lhs: ImageDataModel?, rhs: ImageDataModel?): Int {
//                return rhs!!.imageTitle!!.compareTo(lhs!!.imageTitle!!)
//
//            }
//
//        })

        audioList!!.clear()
        audioList!!.addAll(itemList)
        this.image = image
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderDetailViewHolder {

        val view = inflater.inflate(R.layout.item_document, parent, false)
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

        private var tvExtention=itemView.iv_audio_ext
        private var tvTitle=itemView.tv_audio_title
        private var tvAudioSize=itemView.tv_size
        private var animationView=itemView.lottie_animation
        private var animator: ValueAnimator? = null


        init {
            itemView.setOnClickListener { onItemClicked() }
            animator = ValueAnimator.ofFloat(0f, 1f)
            animator!!.addUpdateListener { valueAnimator ->
                animationView.setProgress(valueAnimator.animatedValue as Float)
            }
        }


        fun setDataToView(item: ImageDataModel) {
            tvTitle.text = item.file!!.name
            tvAudioSize.text = item.size
//            tvDuration.text = item.file!!.name
//            var nameArr = item.imageTitle!!.split("\\.".toRegex())
            tvExtention.setImageResource(image)
            if (item.selected == true)
                animator!!.start();
            else animationView.progress = 0f;
        }

        fun onItemClicked() {
            startCheckAnimation()
        }

        private fun startCheckAnimation() {
            val selectedItem = audioList!!.get(adapterPosition)

            if (animationView.progress == 0f) {
                animator!!.start();
                selectedItem.selected = true
            } else {
                animationView.progress = 0f;
                selectedItem.selected = false

            }
            if (listner != null)
                listner.onDocumentClicked(selectedItem)

        }


    }
}
