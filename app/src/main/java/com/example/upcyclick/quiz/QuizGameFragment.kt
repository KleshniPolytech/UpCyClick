package com.example.upcyclick.quiz

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.upcyclick.AppSingleton
import com.example.upcyclick.R
import com.example.upcyclick.database.entity.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizGameFragment : Fragment() {
    lateinit var questions: MutableList<Question>

    private lateinit var currentQuestion: Question
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

    private lateinit var questionImage: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_quiz_game, container, false)

        return view
    }

    override fun onResume() {
        super.onResume()
        appInstance = AppSingleton.getInstance(this.requireContext())

        questions = mutableListOf()

        difficult = appInstance.currentQuizDifficulty

        Log.d("testing", difficult.toString())

        val job = lifecycleScope.launch(Dispatchers.IO) {
            when (difficult) {
                1 ->  for (i in appInstance.upDB?.questionDao()?.getAllByDif(1)!!) {
                    questions.add(i)
                }
                2 -> for (i in appInstance.upDB?.questionDao()?.getAllByDif(2)!!) {
                    questions.add(i)
                }
                3 -> for (i in appInstance.upDB?.questionDao()?.getAllByDif(3)!!) {
                    questions.add(i)
                }
            }
        }

        lifecycleScope.launch {
            job.join()

            Log.d("testing", "= " + questions.toString())

            Log.d("testing", "dif =" + difficult.toString())

            view?.let { init(it) }
            view?.let { initListeners(it) }

            shuffleQuestions()
            drawQuestion()
            drawAnswers()
            drawImage()
        }

        coroutine = lifecycleScope.launch {
            delay(3000)
            updateCoinCount()
        }
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

        questionImage = v.findViewById(R.id.questionImage)

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
                Log.d("testing", currentQuestion.rightAnswer)
                Log.d("testing", checkbox.text.toString())
                if (checkbox.text.toString() == currentQuestion.rightAnswer) {
                    questionResult = true
                    showRightAnswer(index)
                    questionIndex++
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
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
                    showRightAnswer(answers.indexOf(currentQuestion.rightAnswer))
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
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.split("|").toMutableList()
        answers.shuffle()
        Log.d("testing", currentQuestion.toString())
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
        question.text = currentQuestion.question
    }

    private fun drawAnswers() {
        if (answers.size == 2) {
            thirdAnswer.visibility = View.GONE
            fourthAnswer.visibility = View.GONE
        }
        else {
            thirdAnswer.visibility = View.VISIBLE
            fourthAnswer.visibility = View.VISIBLE
            checkBox3.text = answers[2]
            checkBox4.text = answers[3]
        }
        checkBox1.text = answers[0]
        checkBox2.text = answers[1]
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

        descText.text = currentQuestion.description
    }

    private fun drawImage() {
        questionImage.background = this.context?.let { ContextCompat.getDrawable(it, appInstance.drawableIndexes[currentQuestion.imageId]) }
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
        drawImage()
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