package com.example.wowpersonalitytest.ui.fragments

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wowpersonalitytest.R
import com.example.wowpersonalitytest.databinding.FragmentResultsBinding
import com.example.wowpersonalitytest.ui.interfaces.FragmentSwitchListener
private const val CORRECT_ANSWERS = "properAnswers"
private const val NUMBER_OF_QUESTIONS = "numberOfQuestions"
class ResultsFragment : Fragment() {
    private var res: Resources? = null
    private var _bindingFragment: FragmentResultsBinding? = null
    private val bindingFragment get() = _bindingFragment!!
    private var switchListener: FragmentSwitchListener? = null
    private var correctAnswers: Int = 0
    private var numberOfQuestions: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingFragment = FragmentResultsBinding.inflate(layoutInflater)
        return bindingFragment.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            arguments?.let {
                correctAnswers = it.getInt(CORRECT_ANSWERS)
                numberOfQuestions = it.getInt(NUMBER_OF_QUESTIONS)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = requireActivity().resources.getString(R.string.result_of_quiz, 2,3)
        bindingFragment.textResult.setText(res?.getString(R.string.text_results, correctAnswers, numberOfQuestions))
    }



    companion object {
        fun newInstance(numberOfProperAnswers: Int, numberOfQuestions: Int): ResultsFragment {
           return ResultsFragment().apply {
                arguments = Bundle().apply {
                    putInt(NUMBER_OF_QUESTIONS, numberOfQuestions)
                    putInt(CORRECT_ANSWERS, numberOfProperAnswers)
                }
           }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentSwitchListener) switchListener = context
        res = context.resources
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _bindingFragment = null
        switchListener = null
    }

    override fun onDetach() {
        super.onDetach()
        res = null

    }
}