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
import androidx.fragment.app.viewModels
import com.example.wowpersonalitytest.R
import com.example.wowpersonalitytest.data.Question
import com.example.wowpersonalitytest.databinding.FragmentQuizBinding
import com.example.wowpersonalitytest.ui.interfaces.FragmentSwitchListener
import com.example.wowpersonalitytest.ui.viewmodel.QuestionViewModel

private const val LOG_TAG = "QuestionFragment"
private const val ARG_PARAM1 = "properAnswers"
private const val ARG_PARAM2 = "numberOfQuestions"
private const val BUNDLE_KEY_INDEX = "KeyIndex"
private const val BUNDLE_KEY_ANSWERED = "KeyAnswered"
private const val BUNDLE_KEY_WRONG_ANSWERS = "KeyWrongAnswers"

class QuestionFragment : Fragment() {
    private var switchListener: FragmentSwitchListener? = null
    private var _fragmentBinding: FragmentQuizBinding? = null
    private val fragmentBinding get() = _fragmentBinding!!
    private val viewModel: QuestionViewModel by viewModels<QuestionViewModel>()

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
        _fragmentBinding = FragmentQuizBinding.inflate(layoutInflater)
        savedInstanceState?.let {
            viewModel.setCurrentQuestion(it.getInt(BUNDLE_KEY_INDEX))
            viewModel.answeredQuestions = it.getIntArray(BUNDLE_KEY_ANSWERED)?.toMutableList() ?: mutableListOf()
            viewModel.wrongAnswers = it.getInt(BUNDLE_KEY_WRONG_ANSWERS)
        }
        setQuestion(viewModel.data[viewModel.currentQuestion])
        if(viewModel.isAnswered(viewModel.currentQuestion)) disableButtons(fragmentBinding.buttonFalseAnswer, fragmentBinding.buttonTrue)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        Log.d(LOG_TAG, "onViewCreatedFragment")
    }

    private fun initListeners() {
        fragmentBinding.buttonTrue.setOnClickListener(View.OnClickListener {
            answerTheQuestion(true)
        })

        fragmentBinding.buttonFalseAnswer.setOnClickListener(View.OnClickListener {
            answerTheQuestion(false)
        })

        fragmentBinding.buttonNextQuestion.setOnClickListener(View.OnClickListener {
            setQuestion(getNextQuestion())
            if (!viewModel.isAnswered(viewModel.currentQuestion)) {
                enableButtons(
                    fragmentBinding.buttonTrue,
                    fragmentBinding.buttonFalseAnswer
                )
            } else disableButtons(fragmentBinding.buttonTrue, fragmentBinding.buttonFalseAnswer)
        })

        fragmentBinding.buttonPreviousQuestion.setOnClickListener(View.OnClickListener {
            setQuestion(getPreviousQuestion())
            if (!viewModel.isAnswered(viewModel.currentQuestion)) {
                enableButtons(
                    fragmentBinding.buttonTrue,
                    fragmentBinding.buttonFalseAnswer
                )
            } else disableButtons(fragmentBinding.buttonTrue, fragmentBinding.buttonFalseAnswer)
        })

        fragmentBinding.buttonResults.setOnClickListener {
            switchListener?.switch(
                ResultsFragment.newInstance(
                    viewModel.data.size - viewModel.wrongAnswers,
                    viewModel.data.size
                )
            )
        }

    }


    //Stay in ui because of context
    private fun checkAnswer(userAnswer: Boolean) {
        if (viewModel.isProperAnswer(userAnswer)) {
            Toast.makeText(context, "Good!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "naaah, try again", Toast.LENGTH_SHORT).show()
            viewModel.wrongAnswers++
        }
    }

//ui
    private fun answerTheQuestion(answer: Boolean) {
        viewModel.markAsAnswered()
        disableButtons(fragmentBinding.buttonFalseAnswer, fragmentBinding.buttonTrue)
        checkAnswer(answer)
        if (viewModel.isEndOfQuiz(viewModel.data.size, viewModel.answeredQuestions.size)) {
           // hideViews(fragmentBinding.root)
            fragmentBinding.buttonResults.isVisible = true
        }
    }

//ui
    private fun disableButtons(vararg view: Button) {
        view.forEach {
            it.isClickable = false
            it.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.buttons_solid))
        }
    }

//ui
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

//ui
    private fun setQuestion(question: Question) {
        fragmentBinding.apply {
            imageView.setImageResource(question.imageResId)
            textView.setText(question.questionResId)
        }
    }


    private fun getNextQuestion(): Question {
        viewModel.increaseQuestionsNumber()
        return viewModel.data[viewModel.currentQuestion]
    }

//ui
    private fun getPreviousQuestion(): Question {
        viewModel.decreaseQuestionNumber()
        return viewModel.data[viewModel.currentQuestion]
    }

//ui
    private fun hideViews(vararg invisibleView: View) {
        invisibleView.forEach { it.isVisible = false }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentSwitchListener) switchListener = context
        Log.d(LOG_TAG, "onAttachFragment")
    }


    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStartFragment and current is ${viewModel.currentQuestion}")

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "save state")
        outState.apply {
            putInt(BUNDLE_KEY_INDEX, viewModel.currentQuestion)
            putIntArray(BUNDLE_KEY_ANSWERED, viewModel.answeredQuestions.toIntArray())
            putInt(BUNDLE_KEY_WRONG_ANSWERS, viewModel.wrongAnswers)
        }
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



