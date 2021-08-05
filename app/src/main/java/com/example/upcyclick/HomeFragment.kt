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
import com.example.upcyclick.database.entity.Scroll
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {


    private lateinit var upButton: Button
    private lateinit var toQuizButton: Button
    private lateinit var commonCountButton: Button
    private lateinit var rareCountButton: Button
    private lateinit var legCountButton: Button
    private lateinit var coinCountTextView: TextView

    var commonBoughtScrollList: MutableList<Scroll>? = null
    var rareBoughtScrollList: MutableList<Scroll>? = null
    var legBoughtScrollList: MutableList<Scroll>? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        upButton = view.findViewById(R.id.up_button)
        coinCountTextView = view.findViewById(R.id.coin_count)
        toQuizButton = view.findViewById(R.id.to_shop_button)
        commonCountButton = view.findViewById(R.id.common_count)
        rareCountButton = view.findViewById(R.id.rare_count)
        legCountButton = view.findViewById(R.id.legendary_count)


        coinCountTextView.text = AppSingleton.getInstance(this.requireContext()).count.toString()

        commonBoughtScrollList = AppSingleton.getInstance(requireContext()).getBoughtScrollList(1)
        Log.d("AAA1",  commonBoughtScrollList?.size.toString())
        rareBoughtScrollList = AppSingleton.getInstance(requireContext()).getBoughtScrollList(2)
        Log.d("AAA2",  rareBoughtScrollList?.size.toString())
        legBoughtScrollList = AppSingleton.getInstance(requireContext()).getBoughtScrollList(3)
        Log.d("AAA3",  legBoughtScrollList?.size.toString())



        commonCountButton.text = commonBoughtScrollList?.size.toString()




        upButton.setOnClickListener {
            (AppSingleton.getInstance(this.requireContext()).count) += AppSingleton.getInstance(requireContext()).upgradeCount
            //Log.d("LIST" , AppSingleton.getInstance(requireContext()).upgradeCount.toString())
            coinCountTextView.text = AppSingleton.getInstance(this.requireContext()).count.toString()
        }

        toQuizButton.setOnClickListener {
            view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment())
        }

//        lifecycleScope.launch{
//            updateCounter()
//        }



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






//    private fun onSave() {
//        val pref = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)
//        val editor = pref?.edit()
//
//        editor?.putInt("Count",  YourManager.getInstance(this.requireContext()).count)
//
//        editor?.apply()
//    }


//    private suspend fun updateCounter(){
//
//        lifecycleScope.launchWhenCreated {
//            while (true) {
//                coinCounter++
//                coinCountTextView.text = coinCounter.toString()
//                delay(1000)
//            }
//
//        }
//    }
}