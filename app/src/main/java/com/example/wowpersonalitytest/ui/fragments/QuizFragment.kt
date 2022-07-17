package com.example.wowpersonalitytest.ui.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wowpersonalitytest.R
import com.example.wowpersonalitytest.data.Question
import com.example.wowpersonalitytest.databinding.FragmentQuizBinding
import java.lang.ref.WeakReference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var _fragmentBinding: FragmentQuizBinding? = null
private val fragmentBinding get() = _fragmentBinding!!
private lateinit var data: List<Question>
private var currentQuestion: Int = 0 // Counter of question number

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _fragmentBinding = FragmentQuizBinding.inflate(layoutInflater)
        initListeners()
        initTempData()

        fragmentBinding.textView.setText(data.first().questionResId)
        return fragmentBinding.root

        //return inflater.inflate(R.layout.fragment_question, container, false)
    }

    private fun initListeners() {
        fragmentBinding.buttonFirstAnswer.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "ok, it's you", Toast.LENGTH_SHORT).show()
        })

        fragmentBinding.buttonSecondAnswer.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "ok, it's not you", Toast.LENGTH_SHORT).apply {
                setGravity(Gravity.TOP,0,0)
                show()
            }
        })

        fragmentBinding.buttonNextQuestion.setOnClickListener(View.OnClickListener {
            val question = getNextQuestion(currentQuestion)
            fragmentBinding.textView.setText(question.questionResId)
        })

        fragmentBinding.buttonPreviousQuestion.setOnClickListener(View.OnClickListener {
            // TODO: implement
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentBinding = null
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

    private fun getNextQuestion(counter: Int) : Question {
        increaseQuestionsNumber()
        return data[currentQuestion]
    }

    private fun increaseQuestionsNumber() : Boolean {
        currentQuestion = (currentQuestion + 1) % getNumberOfQuestions(data)
        return true
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
//+1create counter
//3create cycle plus counter function
//+2create get number of questions for function 3
//4create setquestion(id) incapsulate set
//5getid function(question)
//6getquestionObject
