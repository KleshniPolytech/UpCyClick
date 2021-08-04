package com.example.upcyclick

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController


class HomeFragment : Fragment() {


    private lateinit var upButton: Button
    private lateinit var toQuizButton: Button
    private lateinit var coinCountTextView: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        upButton = view.findViewById(R.id.up_button)
        coinCountTextView = view.findViewById(R.id.coin_count)
        toQuizButton = view.findViewById(R.id.to_quiz_button)

        coinCountTextView.text = AppSingleton.getInstance(this.requireContext()).count.toString()


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