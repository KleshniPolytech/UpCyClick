package com.example.upcyclick.quiz

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.upcyclick.AppSingleton
import com.example.upcyclick.R
import com.example.upcyclick.database.entity.Question
import com.example.upcyclick.databinding.FragmentQuizGameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizGameFragment : Fragment() {
    var firstLaunched = true

    lateinit var questions: MutableList<Question>

    var currentQuestion = Question(1, "", "", "", "", 0)
    var answers: MutableList<String> = mutableListOf()
    private var questionIndex = 0
    private var numQuestions = 0

    private lateinit var checkBoxes: List<RadioButton>

    var quizDescriptionText = ""

    private var difficult: Int = 0

    private lateinit var appInstance: AppSingleton
    private lateinit var coroutine: Job

    private var questionResult = false

    var imageId: Drawable? = null

    private lateinit var binding: FragmentQuizGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate<FragmentQuizGameBinding>(inflater, R.layout.fragment_quiz_game, container, false)

        onResume()
        firstLaunched = false

        binding.quiz = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (!firstLaunched) return

        appInstance = AppSingleton.getInstance(this.requireContext())

        questions = mutableListOf()

        difficult = appInstance.currentQuizDifficulty

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

            init()
            view?.let { initListeners(it) }

            shuffleQuestions()
            drawAnswerViews()
            imageId = context?.let { ContextCompat.getDrawable(it, appInstance.drawableIndexes[currentQuestion.imageId]) }

            showViews()

            binding.invalidateAll()
        }

        coroutine = lifecycleScope.launch {
            updateCoinCount()
        }
    }

    private fun init() {
        checkBoxes = listOf(binding.checkbox1, binding.checkbox2, binding.checkbox3, binding.checkbox4)

        difficult = appInstance.currentQuizDifficulty

        numQuestions = questions.size
    }

    private fun initListeners(v: View) {
        binding.continueButton.setOnClickListener {
            if (questionResult) {
                if (questionIndex < numQuestions) {
                    currentQuestion = questions[questionIndex]
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

        binding.answer1.setOnClickListener {
            binding.checkbox1.isChecked = !binding.checkbox1.isChecked
            onClick()
        }

        binding.answer2.setOnClickListener {
            binding.checkbox2.isChecked = !binding.checkbox2.isChecked
            onClick()
        }

        binding.answer3.setOnClickListener {
            binding.checkbox3.isChecked = !binding.checkbox3.isChecked
            onClick()
        }

        binding.answer4.setOnClickListener {
            binding.checkbox4.isChecked = !binding.checkbox4.isChecked
            onClick()
        }
    }

    private fun onClick() {
        var index = 0
        for (checkbox in checkBoxes) {
            if (checkbox.isChecked) {
                Log.d("testing", currentQuestion.rightAnswer)
                Log.d("testing", checkbox.text.toString())
                if (checkbox.text.toString() == currentQuestion.rightAnswer) {
                    questionResult = true
                    showRightAnswer(index)
                    questionIndex++
                }
                else {
                    questionResult = false
                    when(index) {
                        0 -> {
                            binding.answer1.background =
                                this.context?.let { ContextCompat.getDrawable(it, R.drawable.wrong_quiz_button_selector) }
                        }
                        1 -> {
                            binding.answer2.background =
                                this.context?.let { ContextCompat.getDrawable(it, R.drawable.wrong_quiz_button_selector) }
                        }
                        2 -> {
                            binding.answer3.background =
                                this.context?.let { ContextCompat.getDrawable(it, R.drawable.wrong_quiz_button_selector) }
                        }
                        3 -> {
                            binding.answer4.background =
                                this.context?.let { ContextCompat.getDrawable(it, R.drawable.wrong_quiz_button_selector) }
                        }
                    }
                    showRightAnswer(answers.indexOf(currentQuestion.rightAnswer))
                }
                imageId = context?.let { ContextCompat.getDrawable(it, appInstance.drawableIndexes[currentQuestion.imageId]) }
                quizDescriptionText = currentQuestion.description

                binding.invalidateAll()

                binding.answer1.isEnabled = false
                binding.answer2.isEnabled = false
                binding.answer3.isEnabled = false
                binding.answer4.isEnabled = false
                drawDescriptionCorrectness()
                binding.quizDescription.visibility = View.VISIBLE
                binding.continueButton.visibility = View.VISIBLE
                binding.progressBar.progress = (((questionIndex) / numQuestions.toFloat()) * 100).toInt()
                binding.progressText.text = "${(((questionIndex) / numQuestions.toFloat()) * 100).toInt()}%"
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
    }

    private fun showRightAnswer(position: Int) {
        when(position) {
            0 -> {
                binding.answer1.background =
                    this.context?.let { ContextCompat.getDrawable(it, R.drawable.right_quiz_button_selector) }
            }
            1 -> {
                binding.answer2.background =
                    this.context?.let { ContextCompat.getDrawable(it, R.drawable.right_quiz_button_selector) }
            }
            2 -> {
                binding.answer3.background =
                    this.context?.let { ContextCompat.getDrawable(it, R.drawable.right_quiz_button_selector) }
            }
            3 -> {
                binding.answer4.background =
                    this.context?.let { ContextCompat.getDrawable(it, R.drawable.right_quiz_button_selector) }
            }
        }
    }

    private fun drawAnswerViews() {
        if (answers.size == 2) {
            binding.answer3.visibility = View.GONE
            binding.answer4.visibility = View.GONE
        }
        else {
            binding.answer3.visibility = View.VISIBLE
            binding.answer4.visibility = View.VISIBLE
        }
    }

    private fun drawDescriptionCorrectness() {
        if (questionResult) {
            binding.correctnessQuiz.text = "Correct"
            binding.correctnessQuiz.setTextColor(Color.parseColor("#57B920"))
        }
        else {
            binding.correctnessQuiz.text = "Wrong"
            binding.correctnessQuiz.setTextColor(Color.parseColor("#D56225"))
        }
    }

    private fun refreshAnswers() {
        setQuestion()
        imageId = context?.let { ContextCompat.getDrawable(it, appInstance.drawableIndexes[currentQuestion.imageId]) }

        binding.answer1.isEnabled = true
        binding.answer1.background = this.context?.let { ContextCompat.getDrawable(it, R.drawable.button_selector) }
        binding.answer2.isEnabled = true
        binding.answer2.background = this.context?.let { ContextCompat.getDrawable(it, R.drawable.button_selector) }
        binding.answer3.isEnabled = true
        binding.answer3.background = this.context?.let { ContextCompat.getDrawable(it, R.drawable.button_selector) }
        binding.answer4.isEnabled = true
        binding.answer4.background = this.context?.let { ContextCompat.getDrawable(it, R.drawable.button_selector) }
        binding.quizDescription.visibility = View.GONE
        binding.continueButton.visibility = View.GONE
        for (checkbox in checkBoxes) checkbox.isChecked = false
        drawAnswerViews()

        binding.invalidateAll()
    }

    private suspend fun updateCoinCount() {
        lifecycleScope.launch {
            while (true) {
                binding.quizGameCoinCount.text = appInstance.count.toString()
                delay(1000)
            }
        }
    }

    private fun showViews() {
        binding.questionImage.visibility = View.VISIBLE
        binding.answer1.visibility = View.VISIBLE
        binding.answer2.visibility = View.VISIBLE
        if (currentQuestion.answers.split("|").size != 2) {
            binding.answer3.visibility = View.VISIBLE
            binding.answer4.visibility = View.VISIBLE
        }
    }

    override fun onStop() {
        coroutine.cancel()
        super.onStop()
    }
}