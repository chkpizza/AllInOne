package com.wantique.firebase

import androidx.core.net.toUri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.CategoryDto
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

    suspend fun test() {
        Firebase.firestore.collection("professor_list").document("korean").set(
            Professor(
                listOf(ProfessorItem("8e4cf5c70c93a506889bf9e43835cc9f", "이선재","공단기"), ProfessorItem("b19d46253ba9ce5becc22d2a18580e14", "이유진", "메가"))
            )
        ).await()
    }
}

/*
data class Professors(
    val item: List<Professor>
)

data class Professor(
    val subject: String,
    val item: List<ProfessorItem>
)

data class ProfessorItem(
    val name: String,
    val belong: String
)

 */
data class Professor(
    val item: List<ProfessorItem>
)

data class ProfessorItem(
    val id: String,
    val name: String,
    val belong: String
)