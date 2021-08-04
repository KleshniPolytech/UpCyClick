package com.example.upcyclick.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.upcyclick.R
import com.example.upcyclick.AppSingleton
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizLossFragment : Fragment() {
    private lateinit var retryButton: Button

    private lateinit var coinCount: TextView
    private lateinit var appInstance: AppSingleton
    private lateinit var coroutine: Job


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_quiz_loss, container, false)

        init(view)
        initListeners(view)

        coroutine = lifecycleScope.launch {
            updateCoinCount()
        }

        return view
    }

    private fun init(v: View) {
        retryButton = v.findViewById(R.id.playAgainButton)
        coinCount = v.findViewById(R.id.lossCoinCount)
        appInstance = AppSingleton.getInstance(this.requireContext())
    }

    private fun initListeners(v: View) {
        retryButton.setOnClickListener {
            v.findNavController().navigate(QuizLossFragmentDirections.actionQuizLossFragmentToQuizFragment())
        }
    }

    private suspend fun updateCoinCount() {
        lifecycleScope.launch {
            coinCount.text = appInstance.count.toString()
            delay(1000)
        }
    }

    override fun onStop() {
        coroutine.cancel()
        super.onStop()
    }
}