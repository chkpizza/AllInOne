package com.wantique.daily.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.model.Record
import com.wantique.daily.domain.model.RecordHeader
import com.wantique.daily.domain.repository.DailyRepository
import com.wantique.firebase.model.DailyRecordDto
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDailyRecordUseCase @Inject constructor(private val dailyRepository: DailyRepository) {
    operator fun invoke() = dailyRepository.getDailyRecord()
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: DailyRecordDto): Daily.DailyRecord {
        val header = RecordHeader(dto.recordHeader.title, dto.recordHeader.subTitle)
        val record = dto.record.map {
            Record(it.authorUid, it.documentId, it.date, it.imageUrl, it.body, it.nickName, it.profileImageUrl)
        }

        return Daily.DailyRecord(header, record)
    }
}