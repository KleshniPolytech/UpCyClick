package com.example.upcyclick.quiz

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.upcyclick.R
import com.example.upcyclick.AppSingleton
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizGameFragment : Fragment() {
    data class Q(
        val dif: Int,
        val text: String,
        val answers: List<String>,
        val right: String,
        val desc: String)

    private val easyQuestions: MutableList<Q> = mutableListOf(
        Q(dif = 1,
            text = "2 + 2 = ?",
            answers = listOf("1", "2", "3", "4"),
            right = "4",
            desc = "2 + 2 = 4"),
        Q(dif = 1,
            text = "2 + 5 = ?",
            answers = listOf("4", "5", "6", "7"),
            right = "7",
            desc = "2 + 2 = 4")
    )

    private val mediumQuestions: MutableList<Q> = mutableListOf(
        Q(dif = 2,
            text = "5 * 3 = ?",
            answers = listOf("15", "13", "14", "12"),
            right = "15",
            desc = "2 + 2 = 4"),
        Q(dif = 2,
            text = "7 * 9 = ?",
            answers = listOf("54", "49", "63", "77"),
            right = "63",
            desc = "2 + 2 = 4")
    )

    private val hardQuestions: MutableList<Q> = mutableListOf(
        Q(dif = 3,
            text = "lol?",
            answers = listOf("lol", "lmao", "rofl", "shrek"),
            right = "lol",
            desc = "2 + 2 = 4"),
        Q(dif = 3,
            text = "2 - 2 = ?",
            answers = listOf("0", "1", "2", "3"),
            right = "0",
            desc = "2 + 2 = 4")
    )

    lateinit var questions: MutableList<Q>

    private lateinit var currentQuestion: Q
    private lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private var numQuestions = 0

    private lateinit var firstAnswer: RelativeLayout
    private lateinit var secondAnswer: RelativeLayout
    private lateinit var thirdAnswer: RelativeLayout
    private lateinit var fourthAnswer: RelativeLayout

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView

    private lateinit var checkBoxes: List<RadioButton>
    private lateinit var checkBox1: RadioButton
    private lateinit var checkBox2: RadioButton
    private lateinit var checkBox3: RadioButton
    private lateinit var checkBox4: RadioButton

    private lateinit var continueButton: Button

    private lateinit var question: TextView

    private lateinit var description: RelativeLayout
    private lateinit var descCorrectness: TextView
    private lateinit var descText: TextView

    private var difficult: Int = 0

    private lateinit var appInstance: AppSingleton
    private lateinit var coinCount: TextView
    private lateinit var coroutine: Job

    private var questionResult = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_quiz_game, container, false)

        Log.d("testing", difficult.toString())

        init(view)
        initListeners(view)

        coroutine = lifecycleScope.launch {
            updateCoinCount()
        }

        shuffleQuestions()
        drawQuestion()
        drawAnswers()

        return view
    }

    private fun init(v: View) {
        firstAnswer = v.findViewById(R.id.answer1)
        secondAnswer = v.findViewById(R.id.answer2)
        thirdAnswer = v.findViewById(R.id.answer3)
        fourthAnswer = v.findViewById(R.id.answer4)

        progressBar = v.findViewById(R.id.progressBar)
        progressText = v.findViewById(R.id.progressText)

        checkBox1 = v.findViewById(R.id.checkbox1)
        checkBox2 = v.findViewById(R.id.checkbox2)
        checkBox3 = v.findViewById(R.id.checkbox3)
        checkBox4 = v.findViewById(R.id.checkbox4)

        checkBoxes = listOf(checkBox1, checkBox2, checkBox3, checkBox4)

        continueButton = v.findViewById(R.id.continueButton)

        question = v.findViewById(R.id.question)

        description = v.findViewById(R.id.quizDescription)
        descCorrectness = v.findViewById(R.id.correctnessQuiz)
        descText = v.findViewById(R.id.descriptionQuiz)

        appInstance = AppSingleton.getInstance(this.requireContext())

        difficult = appInstance.currentQuizDifficulty

        when(difficult) {
            1 -> questions = easyQuestions
            2 -> questions = mediumQuestions
            3 -> questions = hardQuestions
        }

        numQuestions = questions.size

        coinCount = v.findViewById(R.id.quizGameCoinCount)
    }

    private fun initListeners(v: View) {
        continueButton.setOnClickListener {
            if (questionResult) {
                if (questionIndex < numQuestions) {
                    refreshAnswers()
                }
                else {
                    v.findNavController().navigate(QuizGameFragmentDirections.actionQuizGameFragmentToQuizVictoryFragment())
                }
            }
            else {
                v.findNavController().navigate(QuizGameFragmentDirections.actionQuizGameFragmentToQuizLossFragment())
            }
        }

        firstAnswer.setOnClickListener {
            checkBox1.isChecked = !checkBox1.isChecked
            onClick(v)
        }

        secondAnswer.setOnClickListener {
            checkBox2.isChecked = !checkBox2.isChecked
            onClick(v)
        }

        thirdAnswer.setOnClickListener {
            checkBox3.isChecked = !checkBox3.isChecked
            onClick(v)
        }

        fourthAnswer.setOnClickListener {
            checkBox4.isChecked = !checkBox4.isChecked
            onClick(v)
        }
    }

    private fun onClick(v: View) {
        var index = 0
        for (checkbox in checkBoxes) {
            if (checkbox.isChecked) {
                if (checkbox.text == currentQuestion.right) {
                    questionResult = true
                    showRightAnswer(index)
                    questionIndex++
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        for (i in answers) Log.d("testing", i)
                    }
                }
                else {
                    questionResult = false
                    when(index) {
                        0 -> {
                            firstAnswer.background =
                                this.context?.let { ContextCompat.getDrawable(it, R.drawable.wrong_quiz_button_selector) }
                        }
                        1 -> {
                            secondAnswer.background =
                                this.context?.let { ContextCompat.getDrawable(it, R.drawable.wrong_quiz_button_selector) }
                        }
                        2 -> {
                            thirdAnswer.background =
                                this.context?.let { ContextCompat.getDrawable(it, R.drawable.wrong_quiz_button_selector) }
                        }
                        3 -> {
                            fourthAnswer.background =
                                this.context?.let { ContextCompat.getDrawable(it, R.drawable.wrong_quiz_button_selector) }
                        }
                    }
                    showRightAnswer(currentQuestion.answers.indexOf(currentQuestion.right))
                }
                firstAnswer.isEnabled = false
                secondAnswer.isEnabled = false
                thirdAnswer.isEnabled = false
                fourthAnswer.isEnabled = false
                drawDescription()
                description.visibility = View.VISIBLE
                continueButton.visibility = View.VISIBLE
                progressBar.progress = (((questionIndex) / numQuestions.toFloat()) * 100).toInt()
                progressText.text = "${(((questionIndex) / numQuestions.toFloat()) * 100).toInt()}%"
                break
            }
            index++
        }
    }

    private fun shuffleQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        Log.d("testing", "here")
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()
    }

    private fun showRightAnswer(position: Int) {
        when(position) {
            0 -> {
                firstAnswer.background =
                    this.context?.let { ContextCompat.getDrawable(it, R.drawable.right_quiz_button_selector) }
            }
            1 -> {
                secondAnswer.background =
                    this.context?.let { ContextCompat.getDrawable(it, R.drawable.right_quiz_button_selector) }
            }
            2 -> {
                thirdAnswer.background =
                    this.context?.let { ContextCompat.getDrawable(it, R.drawable.right_quiz_button_selector) }
            }
            3 -> {
                fourthAnswer.background =
                    this.context?.let { ContextCompat.getDrawable(it, R.drawable.right_quiz_button_selector) }
            }
        }
    }

    private fun drawQuestion() {
        question.text = currentQuestion.text
    }

    private fun drawAnswers() {
        Log.d("testing", "drawAnswers")
        checkBox1.text = answers[0]
        checkBox2.text = answers[1]
        checkBox3.text = answers[2]
        checkBox4.text = answers[3]
    }

    private fun drawDescription() {
        if (questionResult) {
            descCorrectness.text = "Correct"
            descCorrectness.setTextColor(Color.parseColor("#57B920"))
        }
        else {
            descCorrectness.text = "Wrong"
            descCorrectness.setTextColor(Color.parseColor("#D56225"))
        }

        descText.text = currentQuestion.desc
    }

    private fun refreshAnswers() {
        firstAnswer.isEnabled = true
        firstAnswer.background = this.context?.let { ContextCompat.getDrawable(it, R.drawable.button_selector) }
        secondAnswer.isEnabled = true
        secondAnswer.background = this.context?.let { ContextCompat.getDrawable(it, R.drawable.button_selector) }
        thirdAnswer.isEnabled = true
        thirdAnswer.background = this.context?.let { ContextCompat.getDrawable(it, R.drawable.button_selector) }
        fourthAnswer.isEnabled = true
        fourthAnswer.background = this.context?.let { ContextCompat.getDrawable(it, R.drawable.button_selector) }
        description.visibility = View.INVISIBLE
        continueButton.visibility = View.INVISIBLE
        for (checkbox in checkBoxes) checkbox.isChecked = false
        drawQuestion()
        drawAnswers()
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