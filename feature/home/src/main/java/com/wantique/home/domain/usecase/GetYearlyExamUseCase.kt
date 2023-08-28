package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetYearlyExamUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke() = homeRepository.getYearlyExam().run {
        when(this) {
            is Resource.Success -> UiState.Success(data)
            is Resource.Error -> UiState.Error(error)
        }
    }
}