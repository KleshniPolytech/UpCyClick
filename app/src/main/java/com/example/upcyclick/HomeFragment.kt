package com.example.upcyclick

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import kotlinx.coroutines.*


class HomeFragment : Fragment() {
    private lateinit var upButton: Button
    private lateinit var toQuizButton: Button
    private lateinit var coinCountTextView: TextView

    private lateinit var commonCount: TextView
    private lateinit var rareCount: TextView
    private lateinit var legendaryCount: TextView

    private lateinit var commonIncome: TextView
    private lateinit var rareIncome: TextView
    private lateinit var legendaryIncome: TextView

    lateinit var appInstance: AppSingleton

    var running = true

    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        appInstance = AppSingleton.getInstance(this.requireContext())

        upButton = view.findViewById(R.id.up_button)
        coinCountTextView = view.findViewById(R.id.coin_count)
        toQuizButton = view.findViewById(R.id.to_shop_button)

        commonCount = view.findViewById(R.id.commonCount)
        rareCount = view.findViewById(R.id.rareCount)
        legendaryCount = view.findViewById(R.id.legCount)

        commonIncome = view.findViewById(R.id.commonIncome)
        rareIncome = view.findViewById(R.id.rareIncome)
        legendaryIncome = view.findViewById(R.id.legIncome)

        coinCountTextView.text = appInstance.count.toString()


        upButton.setOnClickListener {
            (AppSingleton.getInstance(this.requireContext()).count) += appInstance.upgradeCount
            //Log.d("LIST" , AppSingleton.getInstance(requireContext()).upgradeCount.toString())
            coinCountTextView.text = appInstance.count.toString()
        }

        toQuizButton.setOnClickListener {
            view.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment())
        }

        if (isCommonExist()) {
            commonCount.text = "x${appInstance.boughtCommonScrollList!!.size}"
            commonIncome.text = "${appInstance.boughtCommonScrollList!!.size * appInstance.commonScrollIncome}/sec"
        }
        if (isRareExist()) {
            rareCount.text = "x${appInstance.boughtRareScrollList!!.size}"
            rareIncome.text = "${appInstance.boughtRareScrollList!!.size * appInstance.rareScrollIncome}/sec"
        }
        if (isLegendaryExist()) {
            legendaryCount.text = "x${appInstance.boughtLegendaryScrollList!!.size}"
            legendaryIncome.text = "${appInstance.boughtLegendaryScrollList!!.size * appInstance.legendaryScrollIncome}/sec"
        }

        if (!appInstance.coinCounterLaunched) GlobalScope.launch(Dispatchers.Main) {
            appInstance.coinCounterLaunched = true
            Log.d("testing", "coroutine launched")
            while (true) {
                var sum = 0
                if (isCommonExist()) sum += appInstance.commonScrollIncome * appInstance.boughtCommonScrollList!!.size
                if (isRareExist()) sum += appInstance.rareScrollIncome * appInstance.boughtRareScrollList!!.size
                if (isLegendaryExist()) sum += appInstance.legendaryScrollIncome * appInstance.boughtLegendaryScrollList!!.size

                appInstance.count += sum

                delay(1000)
            }
        }

        lifecycleScope.launch {
            delay(1000)
            while (true) {
                coinCountTextView.text = appInstance.count.toString()
                delay(1000)
            }
        }

        return view
    }

    fun isCommonExist() = appInstance.boughtCommonScrollList != null
    fun isRareExist() = appInstance.boughtRareScrollList != null
    fun isLegendaryExist() = appInstance.boughtLegendaryScrollList != null

    override fun onResume() {

        super.onResume()
        Log.d("LIST5", "A")
        if (AppSingleton.getInstance(requireContext()).updatesList.isNotEmpty()) {
            Log.d("LIST5", "B")
            AppSingleton.getInstance(requireContext()).updateUpgradeCount()
        }
    }

    override fun onStop() {
        running = false
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("testing", "home destroyed")
        super.onDestroy()
    }
//    private fun onSave() {
//        val pref = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)
//        val editor = pref?.edit()
//
//        editor?.putInt("Count",  YourManager.getInstance(this.requireContext()).count)
//
//        editor?.apply()
//    }
}