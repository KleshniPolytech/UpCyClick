package com.example.upcyclick.shop.pager

import android.widget.TextView


import android.os.Bundle

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upcyclick.R
import com.example.upcyclick.AppSingleton
import com.example.upcyclick.shop.ShopFragment
import com.example.upcyclick.shop.ShopFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class PageFragment : Fragment() {
    val commonPrice: Int = 2500
    val rarePrice: Int = 10000
    val legendaryPrice: Int = 50000

    lateinit var singleton: AppSingleton

    private lateinit var toQuizButton: Button
    private lateinit var toMainButton: Button

    private var mPage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        singleton = AppSingleton.getInstance(this.requireContext())

        if (getArguments() != null) {
            mPage = getArguments()?.getInt(ARG_PAGE) ?: 1
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                     savedInstanceState: Bundle?): View? {

        val view = if (mPage == 1) inflater.inflate(R.layout.fragment_page, container, false) else inflater.inflate(R.layout.fragment_page_2, container, false)
        val tvCoins2 = activity?.findViewById<TextView>(R.id.coinCount)

        if (mPage == 1){
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_upgrades)
            recyclerView.layoutManager = LinearLayoutManager(this.context)
            recyclerView.itemAnimator = null
            val adapter = UpgradeAdapter(singleton.allUpgradeList!!, tvCoins2)
            recyclerView.adapter = adapter
        }
        else{
            val commonScroll = view.findViewById<ConstraintLayout>(R.id.lay_1)
            val rareScroll = view.findViewById<ConstraintLayout>(R.id.lay_2)
            val legendaryScroll = view.findViewById<ConstraintLayout>(R.id.lay_3)

            toQuizButton = view.findViewById(R.id.button_quiz)
            toQuizButton.setOnClickListener {
                view.findNavController()
                    .navigate(ShopFragmentDirections.actionShopFragmentToQuizFragment())
            }
            toMainButton = view.findViewById(R.id.button_get_coins)
            toMainButton.setOnClickListener {
                view.findNavController()
                    .navigate(ShopFragmentDirections.actionShopFragmentToHomeFragment())
            }

            val qwe = view.findViewById<MotionLayout>(R.id.constraintLayout)
            qwe.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                    commonScroll.isClickable = false
                    rareScroll.isClickable = false
                    legendaryScroll.isClickable = false
                }

                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                }

                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                    commonScroll.isClickable = true
                    rareScroll.isClickable = true
                    legendaryScroll.isClickable = true

                    if (singleton.availableCommonScrollList?.count()?:0 == 0){
                        allCommonBought(commonScroll)
                    }

                    if (singleton.availableRareScrollList?.count()?:0 == 0){
                        allRareBought(rareScroll)
                    }

                    if (singleton.availableLegendaryScrollList?.count()?:0 == 0){
                        allLegendaryBought(legendaryScroll)
                    }

                }

                override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                }

            })

            if (!singleton.availableCommonScrollList!!.isEmpty())
                commonScroll.setOnClickListener{
                    if (singleton.count >= commonPrice && singleton.availableCommonScrollList?.count()?:0 > 0){

                        //изменение синглетона (удаление из списка доступных и добавление в список купленных)
                        var index = Random().nextInt(singleton.availableCommonScrollList?.count()!!)
                        var newScroll = singleton.availableCommonScrollList?.removeAt(index)
                        newScroll?.purchased = true
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

                    }
                    else {
                        Toast.makeText(context, "You have no enough money for the purchase", Toast.LENGTH_SHORT).show()
                    }
                }
            else{
                allCommonBought(commonScroll)
            }
            if (!singleton.availableRareScrollList!!.isEmpty())
                rareScroll.setOnClickListener{
                    if (singleton.count >= rarePrice && singleton.availableRareScrollList?.count()?:0 > 0){

                        //изменение синглетона (удаление из списка доступных и добавление в список купленных)
                        var index = Random().nextInt(singleton.availableRareScrollList?.count()!!)
                        var newScroll = singleton.availableRareScrollList?.removeAt(index)
                        newScroll?.purchased = true
                        singleton.boughtRareScrollList?.add(newScroll!!)

                        //отладочная информация
                        println("index is " + index)
                        println("list size is " + singleton.availableRareScrollList?.count()?:"error")

                        //изменение коинов
                        singleton.count -= rarePrice
                        tvCoins2?.text = singleton.count.toString() + " "

                        //добавление данных о покупке рецепта в бд
                        CoroutineScope(Dispatchers.IO).launch {
                            singleton.upDB?.scrollDao()?.buyScroll(newScroll!!.name)
                        }

                        //проигрывание анимаций
                        qwe.setTransition(R.id.tr2)
                        qwe.transitionToEnd()
                        commonScroll.isClickable = false
                        legendaryScroll.isClickable = false
                    }
                    else {
                        Toast.makeText(context, "You have no enough money for the purchase", Toast.LENGTH_SHORT).show()
                    }
                }
            else{
                allRareBought(rareScroll)
            }
            if (!singleton.availableLegendaryScrollList!!.isEmpty())
                legendaryScroll.setOnClickListener{
                    if (singleton.count >= legendaryPrice && singleton.availableLegendaryScrollList?.count()?:0 > 0){

                        //изменение синглетона (удаление из списка доступных и добавление в список купленных)
                        var index = Random().nextInt(singleton.availableLegendaryScrollList?.count()!!)
                        var newScroll = singleton.availableLegendaryScrollList?.removeAt(index)
                        newScroll?.purchased = true
                        singleton.boughtLegendaryScrollList?.add(newScroll!!)

                        //отладочная информация
                        println("index is " + index)
                        println("list size is " + singleton.availableLegendaryScrollList?.count()?:"error")

                        //изменение коинов
                        singleton.count -= legendaryPrice
                        tvCoins2?.text = singleton.count.toString() + " "

                        //добавление данных о покупке рецепта в бд
                        CoroutineScope(Dispatchers.IO).launch {
                            singleton.upDB?.scrollDao()?.buyScroll(newScroll!!.name)
                        }

                        //проигрывание анимаций
                        qwe.setTransition(R.id.tr3)
                        qwe.transitionToEnd()
                        rareScroll.isClickable = false
                        commonScroll.isClickable = false
                    }
                    else {
                        Toast.makeText(context, "You have no enough money for the purchase", Toast.LENGTH_SHORT).show()
                    }
                }
            else{
                allLegendaryBought(legendaryScroll)
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

    private fun allRareBought(layout: ConstraintLayout): Unit{
        var tv = layout.findViewById<TextView>(R.id.buy_icon_2)
        tv.text = ""
        tv.setBackgroundResource(R.drawable.ic_baseline_check_24)
        layout.findViewById<TextView>(R.id.price_count_2).isVisible = false
        //layout.findViewById<ImageView>(R.id.imageView3).setImageResource(R.drawable.ic_baseline_check_24)
        layout.isClickable = false
    }

    private fun allLegendaryBought(layout: ConstraintLayout): Unit{
        var tv = layout.findViewById<TextView>(R.id.buy_icon_3)
        tv.text = ""
        tv.setBackgroundResource(R.drawable.ic_baseline_check_24)
        layout.findViewById<TextView>(R.id.price_count_3).isVisible = false
        //layout.findViewById<ImageView>(R.id.imageView3).setImageResource(R.drawable.ic_baseline_check_24)
        layout.isClickable = false
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

