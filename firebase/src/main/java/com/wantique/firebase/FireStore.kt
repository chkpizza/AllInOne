package com.wantique.firebase

import androidx.core.net.toUri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.CategoryDto
import com.wantique.firebase.model.ProfessorsDto
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

    suspend fun getProfessors(): List<ProfessorsDto> {
        val professors = mutableListOf<ProfessorsDto>()
        Firebase.firestore.collection("professor_list").document("korean").get().await().also { snapshot ->
            snapshot.toObject<ProfessorsDto>()?.let {
                professors.add(it)
            } ?: run {
                professors.add(ProfessorsDto())
            }
        }


        Firebase.firestore.collection("professor_list").document("english").get().await().also { snapshot ->
            snapshot.toObject<ProfessorsDto>()?.let {
                professors.add(it)
            } ?: run {
                professors.add(ProfessorsDto())
            }
        }

        Firebase.firestore.collection("professor_list").document("history").get().await().also { snapshot ->
            snapshot.toObject<ProfessorsDto>()?.let {
                professors.add(it)
            } ?: run {
                professors.add(ProfessorsDto())
            }
        }

        Firebase.firestore.collection("professor_list").document("administrative_law").get().await().also { snapshot ->
            snapshot.toObject<ProfessorsDto>()?.let {
                professors.add(it)
            } ?: run {
                professors.add(ProfessorsDto())
            }
        }

        Firebase.firestore.collection("professor_list").document("public_administration").get().await().also { snapshot ->
            snapshot.toObject<ProfessorsDto>()?.let {
                professors.add(it)
            } ?: run {
                professors.add(ProfessorsDto())
            }
        }

        return professors
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
}