package com.example.upcyclick.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.upcyclick.R
import com.example.upcyclick.YourManager

class QuizLossFragment : Fragment() {
    private lateinit var retryButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_quiz_loss, container, false)

        init(view)

        initListeners(view)

        return view
    }

    private fun init(v: View) {
        retryButton = v.findViewById(R.id.playAgainButton)
    }

    private fun initListeners(v: View) {
        retryButton.setOnClickListener {
            v.findNavController().navigate(QuizLossFragmentDirections.actionQuizLossFragmentToQuizFragment())
        }
    }
}