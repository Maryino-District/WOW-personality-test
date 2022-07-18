package com.example.wowpersonalitytest.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wowpersonalitytest.R
import com.example.wowpersonalitytest.data.Question
import com.example.wowpersonalitytest.databinding.FragmentQuizBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val LOG_TAG = "FragmentQuestion"
// Counter of question number

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment : Fragment() {
    private var _fragmentBinding: FragmentQuizBinding? = null
    private val fragmentBinding get() = _fragmentBinding!!
    private lateinit var data: List<Question>
    private var currentQuestion: Int = 0
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreateFragment")
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "onCreateViewFragment")
        // Inflate the layout for this fragment
        _fragmentBinding = FragmentQuizBinding.inflate(layoutInflater)
        initListeners()
        initTempData()
        setQuestionsAttributes(data.first())

        return fragmentBinding.root

        //return inflater.inflate(R.layout.fragment_question, container, false)
    }

    private fun initListeners() {
        fragmentBinding.buttonTrueAnswer.setOnClickListener(View.OnClickListener {
            checkAnswer(true)
        })

        fragmentBinding.buttonFalseAnswer.setOnClickListener(View.OnClickListener {
            checkAnswer(false)
        })

        fragmentBinding.buttonNextQuestion.setOnClickListener(View.OnClickListener {
            setQuestionsAttributes(getNextQuestion())
        })

        fragmentBinding.buttonPreviousQuestion.setOnClickListener(View.OnClickListener {
            setQuestionsAttributes(getPreviousQuestion())
        })

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

    private fun getNumberOfQuestions(questions: List<Question>) : Int = questions.size

    private fun getNextQuestion() : Question {
        increaseQuestionsNumber()
        return data[currentQuestion]
    }

    private fun getPreviousQuestion() : Question {
        decreaseQuestionNumber()
        return data[currentQuestion]
    }

    private fun decreaseQuestionNumber() : Boolean {
        currentQuestion = (getNumberOfQuestions(data) + (currentQuestion - 1)) % getNumberOfQuestions(data)
        return true
    }

    private fun increaseQuestionsNumber() : Boolean {
        currentQuestion = (currentQuestion + 1) % getNumberOfQuestions(data)
        return true
    }

    private fun isProperAnswer(userAnswer: Boolean) : Boolean {
        return getProperAnswer(data[currentQuestion]) == userAnswer
    }

    private fun checkAnswer(userAnswer: Boolean) {
        if (isProperAnswer(userAnswer)) {
            Toast.makeText(context,"Good!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "naaah, try again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getProperAnswer(question: Question) = question.answer


    private fun setQuestionsAttributes(question: Question) {
        fragmentBinding.apply {
            imageView.setImageResource(question.imageResId)
            textView.setText(question.questionResId)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(LOG_TAG, "onAttachFragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LOG_TAG, "onViewCreatedFragment")

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
        fun newInstance(param1: String, param2: String) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
