package com.example.advancedtask.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.advancedtask.R
import com.example.advancedtask.main.MainTab
import com.example.advancedtask.saved.SavedFragment
import com.example.advancedtask.search.SearchFragment

class MainViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private var fragments = listOf(
        MainTab(
            fragment = SearchFragment.newInstance(),
            title = R.string.search_tab
        ),
        MainTab(
            fragment = SavedFragment.newInstance(),
            title = R.string.saved_tab
        )
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position].fragment

    fun getFragment(position: Int): Fragment = fragments[position].fragment
    // TODO createFragment() 랑 정확하게 무슨 차이가 있는지 이해가 되지 않는다.

    fun getTitle(position: Int): Int = fragments[position].title
}