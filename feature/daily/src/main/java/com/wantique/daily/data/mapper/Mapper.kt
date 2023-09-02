package com.wantique.daily.data.mapper

import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.model.Record
import com.wantique.daily.domain.model.RecordHeader
import com.wantique.firebase.model.DailyLetterDto
import com.wantique.firebase.model.DailyRecordDto

object Mapper {
    fun mapperToDomain(dto: DailyLetterDto) = Daily.DailyLetter(dto.letter)

    fun mapperToDomain(dto: DailyRecordDto): Daily.DailyRecord {
        val header = RecordHeader(dto.recordHeader.title, dto.recordHeader.subTitle)
        val record = dto.record.map {
            Record(it.authorUid, it.documentId, it.date, it.imageUrl, it.body)
        }

        return Daily.DailyRecord(header, record)
    }
}