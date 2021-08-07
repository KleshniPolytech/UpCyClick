package com.example.upcyclick

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import kotlinx.coroutines.*


class HomeFragment : Fragment() {


    private lateinit var upButton: Button
    private lateinit var toQuizButton: Button
    private lateinit var coinCountTextView: TextView

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

        coinCountTextView.text = appInstance.count.toString()


        upButton.setOnClickListener {
            (AppSingleton.getInstance(this.requireContext()).count) += appInstance.upgradeCount
            //Log.d("LIST" , AppSingleton.getInstance(requireContext()).upgradeCount.toString())
            coinCountTextView.text = appInstance.count.toString()
        }

        toQuizButton.setOnClickListener {
            view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment())
        }

        if (!appInstance.coinCounterLaunched) GlobalScope.launch(Dispatchers.Main) {
            appInstance.coinCounterLaunched = true
            Log.d("testing", "coroutine launched")
            while (true) {
                appInstance.count++
                delay(1000)
            }
        }

        lifecycleScope.launch {
            delay(1000)
            while(true) {
                coinCountTextView.text = appInstance.count.toString()
                delay(1000)
            }
        }

        return view
    }

    override fun onResume() {

        super.onResume()
        Log.d("LIST5" , "A")
        if(AppSingleton.getInstance(requireContext()).updatesList.isNotEmpty()) {
            Log.d("LIST5" , "B")
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