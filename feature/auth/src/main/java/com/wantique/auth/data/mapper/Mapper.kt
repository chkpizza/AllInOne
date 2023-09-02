package com.wantique.auth.data.mapper

import com.wantique.auth.domain.model.Cover
import com.wantique.firebase.model.CoverDto

object Mapper {
    fun mapperToDomain(dto: CoverDto) = Cover(dto.imageUrl)
}