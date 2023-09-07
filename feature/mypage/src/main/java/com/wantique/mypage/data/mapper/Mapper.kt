package com.wantique.mypage.data.mapper

import com.wantique.firebase.model.UserDto
import com.wantique.mypage.domain.model.UserProfile

object Mapper {
    fun mapperToDomain(dto: UserDto): UserProfile {
        return UserProfile(dto.nickName, dto.profileImageUrl)
    }
}