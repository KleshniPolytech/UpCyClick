package com.example.upcyclick

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class HomeFragment : Fragment() {


    private lateinit var upButton: Button
    private lateinit var toQuizButton: Button
    private lateinit var coinCountTextView: TextView
    private var coinCounter = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val pref = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        coinCounter = pref!!.getInt("Count", 0)

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        upButton = view.findViewById(R.id.up_button)
        coinCountTextView = view.findViewById(R.id.coin_count)
        toQuizButton = view.findViewById(R.id.to_quiz_button)


        coinCountTextView.text = coinCounter.toString()

        upButton.setOnClickListener {
            coinCounter++
            coinCountTextView.text = coinCounter.toString()
        }

        toQuizButton.setOnClickListener {

        }

//        lifecycleScope.launch{
//            updateCounter()
//        }


        return view
    }

    override fun onStop() {
        super.onStop()
        onSave()
    }


    private fun onSave() {
        val pref = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref?.edit()

        editor?.putInt("Count", coinCounter)

        editor?.apply()


    }


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