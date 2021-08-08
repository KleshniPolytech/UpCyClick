package com.example.upcyclick.shop

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController

import com.google.android.material.tabs.TabLayout

import com.example.upcyclick.shop.pager.SampleFragmentPagerAdapter

import androidx.viewpager2.widget.ViewPager2
import com.example.upcyclick.AppSingleton
import com.example.upcyclick.R
import com.example.upcyclick.shop.pager.PageFragment.Companion.newInstance
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ShopFragment : Fragment() {

    lateinit var tvCoins: TextView

    lateinit var singleton: AppSingleton

    private lateinit var coroutine: Job

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        singleton = AppSingleton.getInstance(this.requireContext())
        //делаем много коинов, потому что мне впадлу их фармить
        //singleton.count = 1000000000

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
                        val span = SpannableString("Upgrades")
                        span.setSpan(UnderlineSpan(), 0, "Upgrades".length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        tv1.text = span
                        tv1.setTextColor(Color.parseColor("#57B920"))
                        tab.customView = tv1
                    }
                    1 -> {
                        val view2 = inflater.inflate(R.layout.tab_item, container, false)
                        val tv1 = view2.findViewById<TextView>(R.id.tab_1)
                        tv1.text = "Scrolls"
                        tv1.setTextColor(R.color.black)
                        tab.customView = tv1
                    }
                }
        }).attach()

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //val position = tab.position
                val textView: TextView = tab.customView!!.findViewById(R.id.tab_1)
                val span = SpannableString(textView.text)
                span.setSpan(UnderlineSpan(), 0, textView.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                textView.text = span
                textView.setTextColor(Color.parseColor("#57B920"))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val textView: TextView = tab?.customView!!.findViewById(R.id.tab_1)
                textView.setTextColor(R.color.black)
                var string = ""
                for (ch in textView.text) string += ch
                textView.text = string
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val textView: TextView = tab?.customView!!.findViewById(R.id.tab_1)
                textView.setTextColor(R.color.main_green)
            }
        })

        tvCoins = view.findViewById(R.id.coinCount)

        println(AppSingleton.getInstance(this.requireContext()).count)

        coroutine = lifecycleScope.launch {
            updateCoinCount()
        }

        return view
    }

    private suspend fun updateCoinCount() {
        lifecycleScope.launch {
            while (true) {
                tvCoins.text = singleton.count.toString()
                delay(1000)
            }
        }
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
