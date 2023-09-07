package com.wantique.daily.ui.pastExam.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.wantique.daily.domain.model.Choice
import com.wantique.daily.domain.model.Description
import com.wantique.daily.domain.model.PastExam
import java.text.DecimalFormat

object PastExamBindingAdapter {
    @BindingAdapter("description")
    @JvmStatic
    fun setDescription(view: RecyclerView, item: List<Description>) {
        (view.adapter as DescriptionAdapter).submitList(item)
    }

    @BindingAdapter("choice")
    @JvmStatic
    fun setChoice(view: RecyclerView, item: List<Choice>) {
        (view.adapter as ChoiceAdapter).submitList(item)
    }


    @BindingAdapter("indicator")
    @JvmStatic
    fun setIndicator(view: ViewPager2, indicator: TextView) {
        indicator.text = "01 / ${DecimalFormat("00").format(view.adapter?.itemCount)}"
        view.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                indicator.text = "${DecimalFormat("00").format(position + 1)} / ${DecimalFormat("00").format((view.adapter?.itemCount))}"
            }
        })
    }
}