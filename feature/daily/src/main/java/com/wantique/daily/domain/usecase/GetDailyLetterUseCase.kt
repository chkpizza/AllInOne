package com.wantique.daily.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.repository.DailyRepository
import com.wantique.firebase.model.DailyLetterDto
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDailyLetterUseCase @Inject constructor(private val dailyRepository: DailyRepository) {
    operator fun invoke() = dailyRepository.getDailyLetter()
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: DailyLetterDto): Daily.DailyLetter {
        return Daily.DailyLetter(dto.letter)
    }
}