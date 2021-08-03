package com.example.upcyclick.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.navigation.findNavController
import com.example.upcyclick.R
import com.example.upcyclick.YourManager

class QuizFragment : Fragment() {
    lateinit var easyButton: RelativeLayout
    lateinit var mediumButton: RelativeLayout
    lateinit var hardButton: RelativeLayout

    lateinit var appInstance: YourManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

        init(view)
        initListeners(view)

        return view
    }

    private fun init(v: View) {
        easyButton = v.findViewById(R.id.easyQuizButton)
        mediumButton = v.findViewById(R.id.mediumQuizButton)
        hardButton = v.findViewById(R.id.hardQuizButton)

        appInstance = YourManager.getInstance(this.requireContext())
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
}