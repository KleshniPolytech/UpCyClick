package com.example.upcyclick

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast

class QuizFragment : Fragment() {
    lateinit var easyButton: RelativeLayout
    lateinit var mediumButton: RelativeLayout
    lateinit var hardButton: RelativeLayout

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
    }

    private fun initListeners(v: View) {
        easyButton.setOnClickListener {
            Toast.makeText(context, "easy button", Toast.LENGTH_SHORT).show()
        }
        mediumButton.setOnClickListener {
            Toast.makeText(context, "medium button", Toast.LENGTH_SHORT).show()
        }
        hardButton.setOnClickListener {
            Toast.makeText(context, "hard button", Toast.LENGTH_SHORT).show()
        }

    }
}