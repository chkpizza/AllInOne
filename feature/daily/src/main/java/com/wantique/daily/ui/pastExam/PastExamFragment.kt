package com.wantique.daily.ui.pastExam

import android.os.Bundle
import android.util.Log

import android.view.View
import androidx.navigation.fragment.navArgs
import com.wantique.base.ui.BaseFragment
import com.wantique.daily.R
import com.wantique.daily.databinding.FragmentPastExamBinding

class PastExamFragment : BaseFragment<FragmentPastExamBinding>(R.layout.fragment_past_exam) {
    private val args: PastExamFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateInsets()
    }
}