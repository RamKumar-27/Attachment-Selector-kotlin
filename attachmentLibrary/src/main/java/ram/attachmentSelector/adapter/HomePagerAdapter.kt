package ram.attachmentSelector.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import ram.attachmentSelector.data.model.FragmentItemModel

class HomePagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private var fragmentList = ArrayList<FragmentItemModel>()

    fun setFragmentList(fragmentList: ArrayList<FragmentItemModel>) {
        this.fragmentList = fragmentList
        notifyDataSetChanged()

    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position).fragment
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentList[position].title
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}