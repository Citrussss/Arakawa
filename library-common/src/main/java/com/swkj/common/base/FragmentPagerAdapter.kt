package com.swkj.common.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.swkj.common.constant.Constant

/**
 * ViewPager2的适配器
 */
open class FragmentPagerAdapter(fragmentActivity: FragmentActivity, private val mFragments: MutableList<Fragment>) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        val fragment = mFragments[position]
        if (fragment.arguments == null) {
            fragment.arguments = Bundle()
        }
        fragment.arguments?.putInt(Constant.KEY_POSITION, position)
        return mFragments[position]
    }

    override fun getItemCount(): Int {
        return mFragments.size
    }

    /**
     * add new data to the end of mData
     *
     * @param newData the new data collection
     */
    fun addData(newData: Collection<Fragment>) {
        mFragments.addAll(newData)
        notifyItemRangeInserted(mFragments.size - newData.size, newData.size)
        compatibilityDataSizeChanged(newData.size)
    }

    /**
     * add one new data
     */
    fun addData(data: Fragment) {
        mFragments.add(data)
        notifyItemInserted(mFragments.size)
        compatibilityDataSizeChanged(1)
    }

    /**
     * compatible getLoadMoreViewCount and getEmptyViewCount may change
     *
     * @param size Need compatible data size
     */
    private fun compatibilityDataSizeChanged(size: Int) {
        val dataSize = mFragments.size ?: 0
        if (dataSize == size) {
            notifyDataSetChanged()
        }
    }

}