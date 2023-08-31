package com.wantique.daily.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.daily.domain.repository.DailyRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDailyPromiseUseCase @Inject constructor(private val dailyRepository: DailyRepository) {
    operator fun invoke() = dailyRepository.getDailyPromise()
        .map {
            when (it) {
                is Resource.Success -> UiState.Success(it.data)
                is Resource.Error -> UiState.Error(it.error)
            }
        }
}