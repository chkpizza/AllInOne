package com.wantique.home.domain.usecase

import com.wantique.base.state.Resource
import com.wantique.base.state.UiState
import com.wantique.firebase.model.YearlyCurriculumDto
import com.wantique.home.domain.model.Curriculum
import com.wantique.home.domain.model.DetailCurriculum
import com.wantique.home.domain.model.YearlyCurriculum
import com.wantique.home.domain.repository.ProfessorDetailsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProfessorCurriculumUseCase @Inject constructor(private val professorDetailsRepository: ProfessorDetailsRepository) {
    operator fun invoke(professorId: String) = professorDetailsRepository.getProfessorCurriculum(professorId)
        .map {
            when(it) {
                is Resource.Success -> UiState.Success(mapper(it.data))
                is Resource.Error -> UiState.Error(it.error)
            }
        }

    private fun mapper(dto: YearlyCurriculumDto): YearlyCurriculum {
        mutableListOf<Curriculum>().apply {
            dto.curriculum.forEach {
                val detailCurriculumList = mutableListOf<DetailCurriculum>()
                it.detailCurriculum.forEach { detail ->
                    detailCurriculumList.add(DetailCurriculum(detail.lecture, detail.description, detail.start))
                }
                add(Curriculum(it.tag, detailCurriculumList))
            }
            return YearlyCurriculum(dto.id, dto.year, dto.url, this)
        }
    }
}