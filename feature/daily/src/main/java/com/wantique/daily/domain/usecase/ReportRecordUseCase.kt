package com.wantique.daily.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.daily.domain.repository.RecordRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReportRecordUseCase @Inject constructor(private val recordRepository: RecordRepository) {
    operator fun invoke(documentId: String, reason: String) = recordRepository.reportRecord(documentId, reason)
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(it.data)
                is Resource.Error -> UiState.Error(it.error)
            }
        }
}