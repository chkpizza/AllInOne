package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.firebase.model.ProfessorInfoDto
import com.wantique.home.domain.model.ProfessorInfo
import com.wantique.home.domain.repository.ProfessorDetailsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProfessorInfoUseCase @Inject constructor(private val professorDetailsRepository: ProfessorDetailsRepository) {
    operator fun invoke(professorId: String) = professorDetailsRepository.getProfessorInfo(professorId)
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: ProfessorInfoDto): ProfessorInfo {
        return ProfessorInfo(dto.name, dto.slogan, dto.url)
    }
}