package ram.attachmentSelector.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

class ViewerPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private var fragmentList = ArrayList<Fragment>()

    fun setFragmentList(fragmentList: ArrayList<Fragment>) {
        this.fragmentList = fragmentList
        notifyDataSetChanged()

    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}