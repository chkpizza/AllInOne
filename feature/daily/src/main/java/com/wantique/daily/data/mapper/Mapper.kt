package com.wantique.daily.data.mapper

import com.wantique.daily.domain.model.Choice
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.model.Description
import com.wantique.daily.domain.model.PastExam
import com.wantique.daily.domain.model.PastExamHeader
import com.wantique.daily.domain.model.Record
import com.wantique.daily.domain.model.RecordHeader
import com.wantique.daily.domain.model.TodayPastExam
import com.wantique.firebase.model.DailyLetterDto
import com.wantique.firebase.model.DailyPastExamDto
import com.wantique.firebase.model.DailyRecordDto

object Mapper {
    fun mapperToDomain(dto: DailyLetterDto) = Daily.DailyLetter(dto.letter)

    fun mapperToDomain(dto: DailyRecordDto): Daily.DailyRecord {
        val header = RecordHeader(dto.recordHeader.title, dto.recordHeader.subTitle)
        val record = dto.record.map {
            Record(it.authorUid, it.documentId, it.date, it.imageUrl, it.body, it.nickName, it.profileImageUrl)
        }

        return Daily.DailyRecord(header, record)
    }

    fun mapperToDomain(dto: DailyPastExamDto): Daily.DailyPastExam {
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