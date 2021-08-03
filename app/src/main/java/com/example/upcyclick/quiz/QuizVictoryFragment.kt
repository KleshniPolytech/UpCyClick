package com.example.upcyclick.quiz

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
import androidx.navigation.fragment.findNavController
import com.example.upcyclick.R
import com.example.upcyclick.YourManager
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizVictoryFragment : Fragment() {
    private lateinit var wonCoins: TextView
    private lateinit var retryButton: Button
    private lateinit var appInstance: YourManager

    private lateinit var coinCount: TextView

    private lateinit var coroutine: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_quiz_victory, container, false)

        init(view)
        initListeners(view)

        coroutine = lifecycleScope.launch {
            updateCoinCount()
        }

        return view
    }

    private fun init(v: View) {
        wonCoins = v.findViewById(R.id.wonCoins)
        retryButton = v.findViewById(R.id.playAgainButton)
        appInstance = YourManager.getInstance(this.requireContext())
        coinCount = v.findViewById(R.id.victoryCoinCount)

        when(appInstance.currentQuizDifficulty) {
            1 -> {
                wonCoins.text = "+100"
                appInstance.count += 100
            }
            2 -> {
                wonCoins.text = "+1000"
                appInstance.count += 1000
            }
            3 -> {
                wonCoins.text = "+10000"
                appInstance.count += 10000
            }
        }
    }

    private fun initListeners(v: View) {
        retryButton.setOnClickListener {
            v.findNavController().navigate(QuizVictoryFragmentDirections.actionQuizVictoryFragmentToQuizFragment())
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