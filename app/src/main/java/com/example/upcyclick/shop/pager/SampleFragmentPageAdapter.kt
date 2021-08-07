package com.example.upcyclick.shop.pager

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.upcyclick.shop.ShopFragment

class SampleFragmentPagerAdapter(fa: ShopFragment) : FragmentStateAdapter(fa) {
    val PAGE_COUNT = 2

    override fun getItemCount(): Int {
        return PAGE_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return PageFragment.newInstance(position + 1)
    }

}
