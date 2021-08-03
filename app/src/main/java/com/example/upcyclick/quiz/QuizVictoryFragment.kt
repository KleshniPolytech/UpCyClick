package com.example.upcyclick.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.upcyclick.R
import com.example.upcyclick.YourManager

class QuizVictoryFragment : Fragment() {
    private lateinit var wonCoins: TextView
    private lateinit var retryButton: Button
    private lateinit var appInstance: YourManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_quiz_victory, container, false)

        init(view)

        initListeners(view)

        return view
    }

    private fun init(v: View) {
        wonCoins = v.findViewById(R.id.wonCoins)
        retryButton = v.findViewById(R.id.playAgainButton)
        appInstance = YourManager.getInstance(this.requireContext())

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
}