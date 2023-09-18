package com.wantique.daily.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.daily.domain.model.Choice
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.model.Description
import com.wantique.daily.domain.model.PastExam
import com.wantique.daily.domain.model.PastExamHeader
import com.wantique.daily.domain.model.TodayPastExam
import com.wantique.daily.domain.repository.DailyRepository
import com.wantique.firebase.model.DailyPastExamDto
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDailyPastExamUseCase @Inject constructor(private val dailyRepository: DailyRepository) {
    operator fun invoke() = dailyRepository.getDailyPastExam()
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: DailyPastExamDto): Daily.DailyPastExam {
        val header = PastExamHeader(dto.pastExamHeader.title, dto.pastExamHeader.subTitle)

        val todayPastExam = dto.todayPastExam.pastExam.map {
            val description = it.description.map { descriptionDto ->
                Description(descriptionDto.number, descriptionDto.description)
            }

            val choice = it.choice.map { choiceDto ->
                Choice(choiceDto.number, choiceDto.choice)
            }
            PastExam(it.type, it.question, description, choice, it.answer, it.commentary, it.source)
        }

        return Daily.DailyPastExam(header, TodayPastExam(todayPastExam))
    }
}