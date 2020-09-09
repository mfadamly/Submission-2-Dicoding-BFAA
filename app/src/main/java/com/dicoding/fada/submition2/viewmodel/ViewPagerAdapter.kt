package com.dicoding.fada.submition2.viewmodel

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.fada.submition2.R
import com.dicoding.fada.submition2.view.FollowerFragment
import com.dicoding.fada.submition2.view.FollowingFragment

class ViewPagerAdapter(private val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val pages = listOf(
        FollowerFragment(),
        FollowingFragment()
    )

    private val tabTitles = intArrayOf(
        R.string.followers,
        R.string.followings
    )

    override fun getCount(): Int = pages.size

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(tabTitles[position])
    }

}