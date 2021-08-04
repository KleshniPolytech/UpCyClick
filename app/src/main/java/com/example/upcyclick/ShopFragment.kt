package com.example.upcyclick

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.google.android.material.tabs.TabLayout

import com.example.upcyclick.pager.SampleFragmentPagerAdapter

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.upcyclick.pager.PageFragment
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


class ShopFragment : Fragment() {

    var tvCoins: TextView? = null

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        // Получаем ViewPager и устанавливаем в него адаптер
        val viewPager: ViewPager2 = view.findViewById(R.id.viewpager)
        viewPager.adapter = SampleFragmentPagerAdapter(this@ShopFragment)

        // Передаём ViewPager в TabLayout
        val tabLayout: TabLayout = view.findViewById(R.id.sliding_tabs)

        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy{
            tab, position ->
                when(position){
                    0 -> {
                        val view2 = inflater.inflate(R.layout.tab_item, container, false)
                        val tv1 = view2.findViewById<TextView>(R.id.tab_1)
                        tv1.setText("Upgrades")
                        tv1.setTextColor(R.color.primaryColor)
                        tab.setCustomView(tv1)
                    }
                    1 -> {
                        val view2 = inflater.inflate(R.layout.tab_item, container, false)
                        val tv1 = view2.findViewById<TextView>(R.id.tab_1)
                        tv1.setText("Scrolls")
                        tv1.setTextColor(R.color.black)
                        tv1.setTextSize(27f)
                        tab.setCustomView(tv1)
                    }
                }
        }).attach()

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //val position = tab.position
                val textView: TextView = tab.customView!!.findViewById(R.id.tab_1)
                textView.setTextColor(R.color.primaryColor)
                textView.setTextSize(37f)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val textView: TextView = tab?.customView!!.findViewById(R.id.tab_1)
                textView.setTextColor(R.color.black)
                textView.setTextSize(27f)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val textView: TextView = tab?.customView!!.findViewById(R.id.tab_1)
                textView.setTextColor(R.color.main_green)
            }
        })

        tvCoins = view.findViewById<TextView>(R.id.coins)

        println(AppSingleton.getInstance(this.requireContext()).count)
        tvCoins?.text = AppSingleton.getInstance(this.requireContext()).count.toString() + " "

        return view
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        onSave()
    }

    private fun onSave() {
        val pref = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref?.edit()


        editor?.putInt("Count",  AppSingleton.getInstance(this.requireContext()).count)


        editor?.apply()
    }

}
