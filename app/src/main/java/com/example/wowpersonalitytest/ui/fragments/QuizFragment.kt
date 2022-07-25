package com.example.wowpersonalitytest.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.wowpersonalitytest.R
import com.example.wowpersonalitytest.data.Question
import com.example.wowpersonalitytest.databinding.FragmentQuizBinding
import com.example.wowpersonalitytest.ui.interfaces.FragmentSwitchListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "properAnswers"
private const val ARG_PARAM2 = "numberOfQuestions"
private const val LOG_TAG = "QuestionFragment"
// Counter of question number

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment : Fragment() {
    private var switchListener: FragmentSwitchListener? = null
    private var _fragmentBinding: FragmentQuizBinding? = null
    private val fragmentBinding get() = _fragmentBinding!!
    private lateinit var data: List<Question>
    private lateinit var answeredQuestions: MutableList<Int>
    private var wrongAnswers: Int = 0
    private var currentQuestion: Int = 0

    // TODO: Rename and change types of parameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreateFragment")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "onCreateViewFragment")
        // Inflate the layout for this fragment
        _fragmentBinding = FragmentQuizBinding.inflate(layoutInflater)
        initTempData()
        answeredQuestions = mutableListOf()
        setQuestionsAttributes(data.first())
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        Log.d(LOG_TAG, "onViewCreatedFragment")
    }

    private fun initListeners() {
        fragmentBinding.buttonTrueAnswer.setOnClickListener(View.OnClickListener {
            answeredQuestions.add(currentQuestion)
            disableButtons(fragmentBinding.buttonFalseAnswer, fragmentBinding.buttonTrueAnswer)
            checkAnswer(true)
            if (isEndOfQuiz(data.size, answeredQuestions.size)) {
                prepareInterfaceForResults(
                    fragmentBinding.buttonTrueAnswer,
                    fragmentBinding.buttonFalseAnswer,
                    fragmentBinding.buttonPreviousQuestion,
                    fragmentBinding.buttonNextQuestion,
                    fragmentBinding.textView,
                )
                fragmentBinding.buttonResults.isVisible = true
            }
        })

        fragmentBinding.buttonFalseAnswer.setOnClickListener(View.OnClickListener {
            answeredQuestions.add(currentQuestion)
            disableButtons(fragmentBinding.buttonFalseAnswer, fragmentBinding.buttonTrueAnswer)
            checkAnswer(false)
            if (isEndOfQuiz(data.size, answeredQuestions.size)) {
                prepareInterfaceForResults(
                    fragmentBinding.buttonTrueAnswer,
                    fragmentBinding.buttonFalseAnswer,
                    fragmentBinding.buttonPreviousQuestion,
                    fragmentBinding.buttonNextQuestion,
                    fragmentBinding.textView,
                )
                fragmentBinding.buttonResults.isVisible = true
            }
        })

        fragmentBinding.buttonNextQuestion.setOnClickListener(View.OnClickListener {
            setQuestionsAttributes(getNextQuestion())
            if (!isAnswered(currentQuestion)) enableButtons(
                fragmentBinding.buttonTrueAnswer,
                fragmentBinding.buttonFalseAnswer
            )
            else disableButtons(fragmentBinding.buttonTrueAnswer, fragmentBinding.buttonFalseAnswer)

        })

        fragmentBinding.buttonPreviousQuestion.setOnClickListener(View.OnClickListener {
            setQuestionsAttributes(getPreviousQuestion())
            if (!isAnswered(currentQuestion)) enableButtons(
                fragmentBinding.buttonTrueAnswer,
                fragmentBinding.buttonFalseAnswer
            )
            else disableButtons(fragmentBinding.buttonTrueAnswer, fragmentBinding.buttonFalseAnswer)
        })

        fragmentBinding.buttonResults.setOnClickListener {
            switchListener?.switch(ResultsFragment.newInstance(data.size-wrongAnswers, data.size))
        }

    }

    private fun initTempData() {
        data = listOf(
            Question(R.string.question_first, R.drawable.image_first_question, true),
            Question(R.string.question_second, R.drawable.image_dwarf, false),
            Question(R.string.question_third, R.drawable.image_pandaria, false),
            Question(R.string.question_fourth, R.drawable.image_leathercraft, true),
            Question(R.string.question_fifth, R.drawable.image_azeroth, false)
        )
    }

    private fun disableButtons(vararg view: Button) {
        view.forEach {
            it.isClickable = false
            it.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.buttons_solid))
        }
    }

    private fun enableButtons(vararg view: Button) {
        view.forEach {
            it.isClickable = true
            it.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.yellow_background
                )
            )
        }
    }

    private fun getNumberOfQuestions(questions: List<Question>): Int = questions.size

    private fun getNextQuestion(): Question {
        increaseQuestionsNumber()
        return data[currentQuestion]
    }

    private fun getPreviousQuestion(): Question {
        decreaseQuestionNumber()
        return data[currentQuestion]
    }

    private fun decreaseQuestionNumber(): Boolean {
        currentQuestion =
            (getNumberOfQuestions(data) + (currentQuestion - 1)) % getNumberOfQuestions(data)
        return true
    }

    private fun increaseQuestionsNumber(): Boolean {
        currentQuestion = (currentQuestion + 1) % getNumberOfQuestions(data)
        return true
    }

    private fun isProperAnswer(userAnswer: Boolean): Boolean {
        return getProperAnswer(data[currentQuestion]) == userAnswer
    }

    private fun checkAnswer(userAnswer: Boolean) {
        if (isProperAnswer(userAnswer)) {
            Toast.makeText(context, "Good!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "naaah, try again", Toast.LENGTH_SHORT).show()
            wrongAnswers++
        }
    }

    private fun getProperAnswer(question: Question) = question.answer


    private fun setQuestionsAttributes(question: Question) {
        fragmentBinding.apply {
            imageView.setImageResource(question.imageResId)
            textView.setText(question.questionResId)
        }
    }

    private fun isAnswered(numberOfQuestion: Int): Boolean =
        answeredQuestions.contains(numberOfQuestion)


    private fun prepareInterfaceForResults(vararg invisibleView: View) {
        invisibleView.forEach { it.isVisible = false }
    }

    private fun isEndOfQuiz(numberOfQuestions: Int, numberOfAnswers: Int) =
        numberOfAnswers == numberOfQuestions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentSwitchListener) switchListener = context
        Log.d(LOG_TAG, "onAttachFragment")
    }


    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStartFragment")

    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResumeFragment")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPauseFragment")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStopFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(LOG_TAG, "onDestroyViewFragment")
        _fragmentBinding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroyFragment")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(LOG_TAG, "onDetachFragment")
        switchListener = null
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
