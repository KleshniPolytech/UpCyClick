package com.example.upcyclick.pager

import android.widget.TextView


import android.os.Bundle

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

import androidx.fragment.app.Fragment
import com.example.upcyclick.R
import com.example.upcyclick.AppSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class PageFragment : Fragment() {
    val commonPrice: Int = 100
    val rarePrice: Int = 1000
    val legendaryPrice: Int = 10000

    lateinit var singleton: AppSingleton

    private var mPage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            mPage = getArguments()?.getInt(ARG_PAGE) ?: 1
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                     savedInstanceState: Bundle?): View? {

        val view = if (mPage == 1) inflater.inflate(R.layout.fragment_page, container, false) else inflater.inflate(R.layout.fragment_page_2, container, false)
        val tvCoins2 = activity?.findViewById<TextView>(R.id.coins)
        singleton = AppSingleton.getInstance(this.requireContext())

        if (mPage == 1){

        }
        else{
            val qwe = view.findViewById<MotionLayout>(R.id.constraintLayout)

            val commonScroll = view.findViewById<ConstraintLayout>(R.id.lay_1)
            if (findScroll(1))
                commonScroll.setOnClickListener{
                    if (singleton.count >= commonPrice && singleton.availableCommonScrollList?.count()?:0 > 0){

                        //изменение синглетона (удаление из списка доступных и добавление в список купленных)
                        var index = Random().nextInt(singleton.availableCommonScrollList?.count()!!)
                        var newScroll = singleton.availableCommonScrollList?.removeAt(index)
                        singleton.boughtCommonScrollList?.add(newScroll!!)

                        //отладочная информация
                        println("index is " + index)
                        println("list size is " + singleton.availableCommonScrollList?.count()?:"error")

                        //изменение коинов
                        singleton.count -= commonPrice
                        tvCoins2?.text = singleton.count.toString() + " "

                        //добавление данных о покупке рецепта в бд
                        CoroutineScope(Dispatchers.IO).launch {
                            singleton.upDB?.scrollDao()?.buyScroll(newScroll!!.name)
                        }

                        //проигрывание анимаций
                        qwe.setTransition(R.id.tr1)
                        qwe.transitionToEnd()

                        if (singleton.availableCommonScrollList?.count()?:0 == 0){
                            allCommonBought(commonScroll)
                        }

                    }
                }
            else{
                allCommonBought(commonScroll)
            }
            val rareScroll = view.findViewById<ConstraintLayout>(R.id.lay_2)
            rareScroll.setOnClickListener{
                if (singleton.count >= rarePrice){
                    singleton.count -= rarePrice
                    tvCoins2?.text = singleton.count.toString() + " "
                    qwe.setTransition(R.id.tr2)
                    qwe.transitionToEnd()
                    //добавление данных о покупке рецепта в бд
                    //открытие PDF с рецептом
                }
            }
            val legendaryScroll = view.findViewById<ConstraintLayout>(R.id.lay_3)
            legendaryScroll.setOnClickListener{
                if (singleton.count >= legendaryPrice){
                    singleton.count -= legendaryPrice
                    tvCoins2?.text = singleton.count.toString() + " "
                    qwe.setTransition(R.id.tr3)
                    qwe.transitionToEnd()
                    //добавление данных о покупке рецепта в бд
                    //открытие PDF с рецептом
                }
            }

        }

        return view
    }

    private fun allCommonBought(layout: ConstraintLayout): Unit{
        var tv = layout.findViewById<TextView>(R.id.buy_icon)
        tv.text = ""
        tv.setBackgroundResource(R.drawable.ic_baseline_check_24)
        layout.findViewById<TextView>(R.id.price_count).isVisible = false
        //layout.findViewById<ImageView>(R.id.imageView3).setImageResource(R.drawable.ic_baseline_check_24)
        layout.isClickable = false
    }

    private fun findScroll(rarity: Int): Boolean{
        for (scroll in singleton.availableCommonScrollList!!){
            if (scroll.typeId == rarity) return true
        }
        return false
    }

    companion object {
        const val ARG_PAGE = "ARG_PAGE"
        fun newInstance(page: Int): PageFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = PageFragment()
            fragment.setArguments(args)
            return fragment
        }
    }
}