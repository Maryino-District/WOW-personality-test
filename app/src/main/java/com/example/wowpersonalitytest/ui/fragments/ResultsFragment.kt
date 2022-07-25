package com.example.wowpersonalitytest.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wowpersonalitytest.R
import com.example.wowpersonalitytest.databinding.FragmentResultsBinding
import com.example.wowpersonalitytest.ui.interfaces.FragmentSwitchListener

class ResultsFragment : Fragment() {
    private var _bindingFragment: FragmentResultsBinding? = null
    private val bindingFragment get() = _bindingFragment!!
    private var switchListener: FragmentSwitchListener? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingFragment = FragmentResultsBinding.inflate(layoutInflater)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = requireActivity().resources.getString(R.string.result_of_quiz, 2,3)
        bindingFragment.textResult.setText(text)

    }


    companion object {
        fun newInstance(): ResultsFragment {
            return ResultsFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentSwitchListener) switchListener = context
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _bindingFragment = null
    }
}