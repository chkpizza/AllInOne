package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.firebase.model.YearlyExamPlanDto
import com.wantique.home.domain.model.ExamPlanItem
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetYearlyExamPlanUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    operator fun invoke() = homeRepository.getYearlyExam()
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: YearlyExamPlanDto): Home.YearlyExamPlan {
        val examPlanItemList = mutableListOf<ExamPlanItem>().apply {
            dto.exam.forEach {
                add(ExamPlanItem(it.regDate, it.examDate, it.name, it.complete))
            }
        }
        return Home.YearlyExamPlan(dto.title, examPlanItemList)
    }
}