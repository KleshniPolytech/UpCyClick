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
    data class Q(
        val dif: Int,
        val text: String,
        val answers: List<String>)

    private val questions: MutableList<Q> = mutableListOf(
        Q(dif = 1,
            text = "2 + 2 = ?",
            answers = listOf("1", "2", "3", "4")),
        Q(dif = 1,
            text = "2 + 5 = ?",
            answers = listOf("4", "5", "6", "7")),
        Q(dif = 2,
            text = "5 * 3 = ?",
            answers = listOf("15", "13", "14", "12")),
        Q(dif = 2,
            text = "7 * 9 = ?",
            answers = listOf("54", "49", "63", "77")),
        Q(dif = 3,
            text = "lol?",
            answers = listOf("lol", "lmao", "rofl", "shrek")),
        Q(dif = 3,
            text = "2 - 2 = ?",
            answers = listOf("0", "1", "2", "3"))
    )

    lateinit var currentQuestion: Q
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