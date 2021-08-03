package com.example.upcyclick.pager

import android.annotation.SuppressLint
import android.widget.TextView


import android.os.Bundle
import android.transition.Transition

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.fragment.app.Fragment
import com.example.upcyclick.R
import com.example.upcyclick.ShopFragment
import com.example.upcyclick.YourManager


class PageFragment : Fragment() {
    val commonPrice: Int = 100
    val rarePrice: Int = 1000
    val legendaryPrice: Int = 10000

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


        if (mPage == 1){

        }
        else{
            val qwe = view.findViewById<MotionLayout>(R.id.constraintLayout)

            val commonScroll = view.findViewById<ConstraintLayout>(R.id.lay_1)
            commonScroll.setOnClickListener{
                if (YourManager.getInstance(this.requireContext()).count >= commonPrice){
                    YourManager.getInstance(this.requireContext()).count -= commonPrice
                    tvCoins2?.text = YourManager.getInstance(this.requireContext()).count.toString() + " "
                    qwe.setTransition(R.id.tr1)
                    qwe.transitionToEnd()
                    //добавление данных о покупке рецепта в бд
                    //открытие PDF с рецептом
                }
            }
            val rareScroll = view.findViewById<ConstraintLayout>(R.id.lay_2)
            rareScroll.setOnClickListener{
                if (YourManager.getInstance(this.requireContext()).count >= rarePrice){
                    YourManager.getInstance(this.requireContext()).count -= rarePrice
                    tvCoins2?.text = YourManager.getInstance(this.requireContext()).count.toString() + " "
                    qwe.setTransition(R.id.tr2)
                    qwe.transitionToEnd()
                    //добавление данных о покупке рецепта в бд
                    //открытие PDF с рецептом
                }
            }
            val legendaryScroll = view.findViewById<ConstraintLayout>(R.id.lay_3)
            legendaryScroll.setOnClickListener{
                if (YourManager.getInstance(this.requireContext()).count >= legendaryPrice){
                    YourManager.getInstance(this.requireContext()).count -= legendaryPrice
                    tvCoins2?.text = YourManager.getInstance(this.requireContext()).count.toString() + " "
                    qwe.setTransition(R.id.tr3)
                    qwe.transitionToEnd()
                    //добавление данных о покупке рецепта в бд
                    //открытие PDF с рецептом
                }
            }

        }

        return view
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