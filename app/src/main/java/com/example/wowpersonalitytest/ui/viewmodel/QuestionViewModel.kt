package com.example.wowpersonalitytest.ui.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wowpersonalitytest.R
import com.example.wowpersonalitytest.data.Question

private const val VIEWMODEL_TAG = "ViewModelTag"
class QuestionViewModel : ViewModel() {
    private var _currentQuestion: Int = 0
    val currentQuestion: Int get() = _currentQuestion

    var wrongAnswers: Int = 0
    private val _data: MutableList<Question> = mutableListOf()
    val data: List<Question> get() = _data
    var answeredQuestions: MutableList<Int> = mutableListOf()
    init {
        Log.d(VIEWMODEL_TAG, "Init of ViewModel")
        _data.apply {
            add(Question(R.string.question_first, R.drawable.image_first_question, true))
            add(Question(R.string.question_second, R.drawable.image_dwarf, false))
            add(Question(R.string.question_third, R.drawable.image_pandaria, false))
            add(Question(R.string.question_fourth, R.drawable.image_leathercraft, true))
            add(Question(R.string.question_fifth, R.drawable.image_azeroth, false))
        }

    }
    fun getNumberOfQuestions(questions: List<Question>): Int = questions.size

    fun decreaseQuestionNumber(): Boolean {
        _currentQuestion =
            (getNumberOfQuestions(data) + (currentQuestion - 1)) % getNumberOfQuestions(data)
        return true
    }

    fun setBundleCurrentQuestion(currentQuestion: Int) {
        _currentQuestion = currentQuestion
    }

    fun increaseQuestionsNumber(): Boolean {
        _currentQuestion = (currentQuestion + 1) % getNumberOfQuestions(data)
        return true
    }

    fun markAsAnswered() {
        answeredQuestions.add(currentQuestion)
    }

    fun isAnswered(currentQuestion: Int) = answeredQuestions.contains(currentQuestion)

    fun isEndOfQuiz(numberOfQuestions: Int, answeredQuestions: Int) = numberOfQuestions == answeredQuestions

    fun isProperAnswer(userAnswer: Boolean): Boolean {
        return getProperAnswer(data[currentQuestion]) == userAnswer
    }

    private fun getProperAnswer(question: Question) = question.answer


    override fun onCleared() {
        super.onCleared()
        Log.d(VIEWMODEL_TAG, "onCleared ViewModel")
    }


}