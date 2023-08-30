package com.wantique.firebase

import androidx.core.net.toUri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.CategoryDto
import com.wantique.firebase.model.ExamDto
import com.wantique.firebase.model.ProfessorDto
import com.wantique.firebase.model.ProfessorInfoDto
import com.wantique.firebase.model.ReferenceKey
import com.wantique.firebase.model.UserDto
import com.wantique.firebase.model.YearlyCurriculumDto
import kotlinx.coroutines.tasks.await

class Firebase private constructor() {
    suspend fun getCurrentUser(): Boolean {
        return Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).get().await().exists()
    }

    suspend fun checkWithdrawalUser(): Boolean {
        return Firebase.firestore.collection("withdrawal").document(Firebase.auth.uid.toString()).get().await().exists()
    }

    suspend fun registerUser(imageUri: String, nickName: String): Boolean {
        if(imageUri.isNotEmpty()) {
            Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).set(UserDto(Firebase.auth.uid.toString(), nickName, uploadProfileImage(imageUri))).await()
            return Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).get().await().exists()
        } else {
            Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).set(UserDto(Firebase.auth.uid.toString(), nickName, imageUri)).await()
            return Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).get().await().exists()
        }
    }

    suspend fun redoUser(): Boolean {
        Firebase.firestore.collection("withdrawal").document(Firebase.auth.uid.toString()).delete().await()
        return !Firebase.firestore.collection("withdrawal").document(Firebase.auth.uid.toString()).get().await().exists()
    }

    suspend fun isDuplicateNickName(nickName: String): Boolean {
        return !Firebase.firestore.collection("user").whereEqualTo("nickName", nickName).get().await().isEmpty
    }

    suspend fun getBanner(): BannerDto? {
        return Firebase.firestore.collection("banner").document("home").get().await().run {
            toObject<BannerDto>()
        }
    }

    suspend fun getCategory(): CategoryDto? {
        return Firebase.firestore.collection("category").document("subject").get().await().run {
            toObject<CategoryDto>()
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
        return Firebase.firestore.collection("exam").document("yearly").get().await().run {
             toObject<ExamDto>()
        }
    }

    suspend fun getProfessorCurriculum(professorId: String): YearlyCurriculumDto? {
        getReferenceKey()?.let { referenceKey ->
            return Firebase.firestore.collection("professor_details").document(professorId).collection(referenceKey.key).document("curriculum").get().await().run {
                toObject<YearlyCurriculumDto>()
            }
        } ?: run {
            return null
        }
    }

    suspend fun getProfessorInfo(professorId: String): ProfessorInfoDto? {
        getReferenceKey()?.let { referenceKey ->
            return Firebase.firestore.collection("professor_details").document(professorId).collection(referenceKey.key).document("info").get().await().run {
                toObject<ProfessorInfoDto>()
            }
        } ?: run {
            return null
        }
    }

    private suspend fun getReferenceKey(): ReferenceKey? {
        return Firebase.firestore.collection("professor_details").document("reference").get().await().run {
            toObject<ReferenceKey>()
        }
    }

    private suspend fun uploadProfileImage(imageUri: String): String {
        val ref = Firebase.storage.reference.child("profile").child(Firebase.auth.uid.toString()).child("profileImage.jpg")
        return ref.putFile(imageUri.toUri()).await().storage.downloadUrl.await().toString()
    }


    companion object {
        private lateinit var firestore: com.wantique.firebase.Firebase

        fun getInstance(): com.wantique.firebase.Firebase {
            if(!::firestore.isInitialized) {
                firestore = Firebase()
            }

            return firestore
        }
    }
}


