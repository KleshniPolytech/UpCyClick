package com.example.upcyclick.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.upcyclick.R

import com.example.upcyclick.AppSingleton
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizFragment : Fragment() {
    private lateinit var easyButton: RelativeLayout
    private lateinit var mediumButton: RelativeLayout
    private lateinit var hardButton: RelativeLayout


    private lateinit var appInstance: AppSingleton

    private lateinit var coroutine: Job

    private lateinit var coinCount: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

        init(view)
        initListeners(view)

        coroutine = lifecycleScope.launch {
            updateCoinCount()
        }

        return view
    }

    fun test() ="test"

    private fun init(v: View) {
        easyButton = v.findViewById(R.id.easyQuizButton)
        mediumButton = v.findViewById(R.id.mediumQuizButton)
        hardButton = v.findViewById(R.id.hardQuizButton)


        appInstance = AppSingleton.getInstance(this.requireContext())

        coinCount = v.findViewById(R.id.quizCoinCount)
    }

    private fun initListeners(v: View) {
        easyButton.setOnClickListener {
            appInstance.currentQuizDifficulty = 1
            v.findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizGameFragment())
        }
        mediumButton.setOnClickListener {
            appInstance.currentQuizDifficulty = 2
            v.findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizGameFragment())
        }
        hardButton.setOnClickListener {
            appInstance.currentQuizDifficulty = 3
            v.findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizGameFragment())
        }
    }

    private suspend fun updateCoinCount() {
        lifecycleScope.launch {
            while (true) {
                coinCount.text = appInstance.count.toString()
                delay(1000)
            }
        }
    }

    override fun onStop() {
        coroutine.cancel()
        super.onStop()
    }
}