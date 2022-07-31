package com.example.wowpersonalitytest.ui.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wowpersonalitytest.R
import com.example.wowpersonalitytest.data.Question

private const val VIEWMODEL_TAG = "ViewModelTag"
class QuestionViewModel : ViewModel() {
    private var currentQuestion: Int = 0
    val data: MutableList<Question>
    init {
        Log.d(VIEWMODEL_TAG, "Init of ViewModel")
        data = mutableListOf(
                Question(R.string.question_first, R.drawable.image_first_question, true),
                Question(R.string.question_second, R.drawable.image_dwarf, false),
                Question(R.string.question_third, R.drawable.image_pandaria, false),
                Question(R.string.question_fourth, R.drawable.image_leathercraft, true),
                Question(R.string.question_fifth, R.drawable.image_azeroth, false)
        )

    }
    fun getNumberOfQuestions(questions: List<Question>): Int = questions.size

    fun decreaseQuestionNumber(): Boolean {
        currentQuestion =
            (getNumberOfQuestions(viewModel.data) + (currentQuestion - 1)) % getNumberOfQuestions(viewModel.data)
        return true
    }

    fun increaseQuestionsNumber(): Boolean {
        currentQuestion = (currentQuestion + 1) % getNumberOfQuestions(viewModel.data)
        return true
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(VIEWMODEL_TAG, "onCleared ViewModel")
    }
}