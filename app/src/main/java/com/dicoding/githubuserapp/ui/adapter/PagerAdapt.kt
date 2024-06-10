@file:Suppress("DEPRECATION")

package com.dicoding.githubuserapp.ui.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.ui.fragment.FollowersFragment
import com.dicoding.githubuserapp.ui.fragment.FollowingFragment

class PagerAdapt(private val mCtx: Context, fm: FragmentManager, data: Bundle) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragBundle: Bundle

    init {
        fragBundle = data
    }

    @StringRes
    private val layoutTab = intArrayOf(R.string.tabLayout1, R.string.tabLayout2)

    override fun getCount(): Int = 2

    override fun getItem(loc: Int): Fragment {
        var frag: Fragment? = null
        when (loc) {
            0 -> frag = FollowersFragment()
            1 -> frag = FollowingFragment()
        }
        frag?.arguments = this.fragBundle
        return frag as Fragment
    }

    override fun getPageTitle(loc: Int): CharSequence? {
        return mCtx.resources.getString(layoutTab[loc])
    }
}