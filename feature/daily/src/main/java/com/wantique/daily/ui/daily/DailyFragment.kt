package com.wantique.daily.ui.daily

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.wantique.base.ui.BaseFragment
import com.wantique.daily.R
import com.wantique.daily.databinding.FragmentDailyBinding
import com.wantique.daily.di.DailyComponentProvider
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.model.PastExam
import com.wantique.daily.domain.model.Promise
import com.wantique.daily.ui.daily.adapter.DailyAdapter
import com.wantique.daily.ui.daily.adapter.listener.OnPastExamClickListener
import javax.inject.Inject


class DailyFragment : BaseFragment<FragmentDailyBinding>(R.layout.fragment_daily) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[DailyViewModel::class.java] }
    private lateinit var dailyAdapter: DailyAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as DailyComponentProvider).getDailyComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateTopInsets()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val onPastExamClickListener = object : OnPastExamClickListener {
            override fun onClick(position: Int, pastExam: List<PastExam>) {
                Log.d("PastExamClick", "$position / $pastExam")
            }
        }

        dailyAdapter = DailyAdapter(onPastExamClickListener)
        binding.dailyRv.adapter = dailyAdapter

        dailyAdapter.submitList(makeDaily())
    }

    private fun makeDaily(): List<Daily> {
        val daily = mutableListOf<Daily>().apply {
            add(Daily.DailyLetter("날씨가 쌀쌀하네요!\n오늘 하루도\n화이팅해봐요!!"))
            add(Daily.DailyPromise(
                "매일 아침 하루의 다짐으로!",
                "오늘의 다짐들",
                listOf(
                    Promise("", "https://cdn.topstarnews.net/news/photo/202208/14725276_939158_583.jpg"),
                    Promise("[1] 오늘 행정법 기본서 1회독 가즈아!!", "https://cdn.topstarnews.net/news/photo/202208/14725276_939158_583.jpg"),
                    Promise("[2] 오늘 행정법 기본서 1회독 가즈아!!", "https://cdn.topstarnews.net/news/photo/202208/14725276_939158_583.jpg"),
                    Promise("[3] 오늘 행정법 기본서 1회독 가즈아!!", ""),
                    Promise("[4] 오늘 행정법 기본서 1회독 가즈아!!", "https://cdn.topstarnews.net/news/photo/202208/14725276_939158_583.jpg"),
                    Promise("[5] 오늘 행정법 기본서 1회독 가즈아!!", ""),
                    Promise("[6] 오늘 행정법 기본서 1회독 가즈아!!", "https://cdn.topstarnews.net/news/photo/202208/14725276_939158_583.jpg")
                )
            ))
            add(
                Daily.DailyPastExam(
                    "하루 3문제 매일 다른 기출!",
                    "오늘의 기출문제 리스트",
                    listOf(
                        PastExam(
                            "DEFAULT",
                            "질문",
                            emptyList(),
                            emptyList(),
                            1,
                            "",
                            "2023년 지방직 9급 행정법 1번"
                        ),
                        PastExam(
                            "DEFAULT",
                            "질문",
                            emptyList(),
                            emptyList(),
                            1,
                            "",
                            "2023년 지방직 9급 행정법 2번"
                        ),
                        PastExam(
                            "DEFAULT",
                            "질문",
                            emptyList(),
                            emptyList(),
                            1,
                            "",
                            "2023년 지방직 9급 행정법 3번"
                        )
                    )
                )
            )
        }
        return daily
    }
}