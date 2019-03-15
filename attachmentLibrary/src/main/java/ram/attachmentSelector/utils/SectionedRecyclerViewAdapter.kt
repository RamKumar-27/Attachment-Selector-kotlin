package ram.attachmentSelector.utils

import android.support.v7.widget.RecyclerView
import android.support.v4.util.ArrayMap
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ViewGroup

abstract class SectionedRecyclerViewAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private val mHeaderLocationMap: ArrayMap<Int, Int>
    private var mLayoutManager: GridLayoutManager? = null

    abstract val sectionCount: Int

    init {
        mHeaderLocationMap = ArrayMap()
    }

    abstract fun getItemCount(section: Int): Int

    abstract fun onCreateViewHolder(parent: ViewGroup, header: Boolean): VH

    abstract fun onBindHeaderViewHolder(holder: VH, section: Int)

    abstract fun onBindViewHolder(holder: VH, section: Int, relativePosition: Int, absolutePosition: Int)

    fun isHeader(position: Int): Boolean {
        return mHeaderLocationMap[position] != null
    }

    fun setLayoutManager(lm: GridLayoutManager?) {
        mLayoutManager = lm
        if (lm == null) return
        lm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (isHeader(position)) mLayoutManager!!.spanCount else 1
            }
        }
    }

    // returns section along with offsetted position
    private fun getSectionIndexAndRelativePosition(itemPosition: Int): IntArray {
        synchronized(mHeaderLocationMap) {
            var lastSectionIndex: Int? = -1
            for (sectionIndex in mHeaderLocationMap.keys) {
                if (itemPosition > sectionIndex) {
                    lastSectionIndex = sectionIndex
                } else {
                    break
                }
                lastSectionIndex = sectionIndex
            }
            return intArrayOf(mHeaderLocationMap.get(lastSectionIndex)!!, itemPosition - lastSectionIndex!! - 1)
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        mHeaderLocationMap.clear()
        for (s in 0 until sectionCount) {
            mHeaderLocationMap[count] = s
            count += getItemCount(s) + 1
        }
        return count
    }

    /**
     * @hide
     */
    @Deprecated("")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateViewHolder(parent, viewType == VIEW_TYPE_HEADER)
    }

    /**
     * @hide
     */
    @Deprecated("")
    override fun getItemViewType(position: Int): Int {
        return if (isHeader(position)) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }

    /**
     * @hide
     */
    @Deprecated("")
    override fun onBindViewHolder(holder: VH, position: Int) {
        var layoutParams: StaggeredGridLayoutManager.LayoutParams? = null
        if (holder.itemView.layoutParams is GridLayoutManager.LayoutParams)
            layoutParams = StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        else if (holder.itemView.layoutParams is StaggeredGridLayoutManager.LayoutParams)
            layoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        if (isHeader(position)) {
            if (layoutParams != null) layoutParams.isFullSpan = true
            onBindHeaderViewHolder(holder, mHeaderLocationMap.get(position)!!)
        } else {
            if (layoutParams != null) layoutParams.isFullSpan = false
            val sectionAndPos = getSectionIndexAndRelativePosition(position)
            onBindViewHolder(holder, sectionAndPos[0],
                    // offset section view positions
                    sectionAndPos[1],
                    position - (sectionAndPos[0] + 1))
        }
        if (layoutParams != null)
            holder.itemView.layoutParams = layoutParams
    }

    /**
     * @hide
     */
    @Deprecated("")
    override fun onBindViewHolder(holder: VH, position: Int, payloads: List<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    companion object {

        private val VIEW_TYPE_HEADER = 0
        private val VIEW_TYPE_ITEM = 1
    }
}