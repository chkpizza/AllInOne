package com.wantique.firebase

import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.CategoryDto
import com.wantique.firebase.model.ExamDto
import com.wantique.firebase.model.ExamItemDto
import com.wantique.firebase.model.ProfessorDto
import com.wantique.firebase.model.UserDto
import kotlinx.coroutines.tasks.await

class FireStore private constructor() {
    suspend fun getCurrentUser(): Boolean {
        return Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).get().await().exists()
    }

    suspend fun checkWithdrawalUser(): Boolean {
        return Firebase.firestore.collection("withdrawal").document(Firebase.auth.uid.toString()).get().await().exists()
    }

    suspend fun registerUser(imageUri: String, nickName: String): Boolean {
        Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).set(UserDto(Firebase.auth.uid.toString(), nickName, imageUri)).await()
        return Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).get().await().exists()
    }

    suspend fun redoUser(): Boolean {
        Firebase.firestore.collection("withdrawal").document(Firebase.auth.uid.toString()).delete().await()
        return !Firebase.firestore.collection("withdrawal").document(Firebase.auth.uid.toString()).get().await().exists()
    }

    suspend fun getBanner(): BannerDto? {
        Firebase.firestore.collection("banner").document("home").get().await().also {
            return it.toObject<BannerDto>()
        }
    }

    suspend fun getCategory(): CategoryDto? {
        Firebase.firestore.collection("category").document("subject").get().await().also {
            return it.toObject<CategoryDto>()
        }
    }

    suspend fun getProfessors(): List<ProfessorDto> {
        val professorDtoList = mutableListOf<ProfessorDto>()

        Firebase.firestore.collection("professors").document("korean").get().await().also { snapshot ->
            snapshot.toObject<ProfessorDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorDto(emptyList()))
            }
        }

        Firebase.firestore.collection("professors").document("english").get().await().also { snapshot ->
            snapshot.toObject<ProfessorDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorDto(emptyList()))
            }
        }

        Firebase.firestore.collection("professors").document("history").get().await().also { snapshot ->
            snapshot.toObject<ProfessorDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorDto(emptyList()))
            }
        }

        Firebase.firestore.collection("professors").document("administrative_law").get().await().also { snapshot ->
            snapshot.toObject<ProfessorDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorDto(emptyList()))
            }
        }

        Firebase.firestore.collection("professors").document("public_administration").get().await().also { snapshot ->
            snapshot.toObject<ProfessorDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorDto(emptyList()))
            }
        }

        return professorDtoList
    }

    suspend fun getYearlyExam(): ExamDto? {
        Firebase.firestore.collection("exam").document("yearly").get().await().also {
            return it.toObject<ExamDto>()
        }
    }

    private suspend fun uploadProfileImage(imageUri: String): String {
        val ref = Firebase.storage.reference.child("profile").child(Firebase.auth.uid.toString()).child("profileImage.jpg")
        return ref.putFile(imageUri.toUri()).await().storage.downloadUrl.await().toString()
    }


    companion object {
        private lateinit var firestore: FireStore

        fun getInstance(): FireStore {
            if(!::firestore.isInitialized) {
                firestore = FireStore()
            }

            return firestore
        }
    }


/*
    suspend fun test() {
        Firebase.firestore.collection("exam").document("yearly").set(
            ExamDto(
                "2023년 공무원 시험(필기) 일정",
                listOf(
                    ExamItemDto("2월 9일 ~ 11일", "4월 8일", "국가직 9급", true),
                    ExamItemDto("5월 23일 ~ 25일", "7월 22일", "국가직 7급 1차", true),
                    ExamItemDto("3월 13일 ~ 17일", "6월 10일", "지방직 9급", true),
                    ExamItemDto("5월 23일 ~ 25일", "9월 23일", "국가직 7급 2차", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                    ExamItemDto("7월 17일 ~ 21일", "10월 28일", "지방직 7급", false),
                )
            )
        ).await()
    }


 */
}

