package com.example.upcyclick.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import com.example.upcyclick.R
import com.example.upcyclick.database.entity.Question

class QuizGameFragment : Fragment() {

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private val questionIndex = 0
    private val numQuestions = 2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_quiz_game, container, false)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        return view
    }
}