package com.wantique.daily.data.mapper

import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.model.Promise
import com.wantique.firebase.model.DailyLetterDto
import com.wantique.firebase.model.DailyPromiseTitleDto
import com.wantique.firebase.model.PromiseDto

object Mapper {
    fun mapperToDomain(dto: DailyLetterDto): Daily.DailyLetter {
        return Daily.DailyLetter(dto.todayLetter)
    }

    fun mapperToDomain(dailyPromiseTitleDto: DailyPromiseTitleDto, promiseDto: List<PromiseDto>): Daily.DailyPromise {
        promiseDto.map {
            Promise(it.imageUrl, it.body, it.authorId, it.documentId)
        }.also {
            return Daily.DailyPromise(dailyPromiseTitleDto.title, dailyPromiseTitleDto.subTitle, it)
        }
    }
}