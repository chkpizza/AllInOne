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
        Firebase.firestore.collection("home").document("banner").get().await().also {
            return it.toObject<BannerDto>()
        }
    }

    suspend fun getCategory(): CategoryDto? {
        Firebase.firestore.collection("home").document("category").get().await().also {
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

    suspend fun bannerTest() {
        /*
        Firebase.firestore.collection("home").document("banner").set(
            Banner(
                "공지사항입니다",
                listOf(BannerItem("htts://www.google.com", "123123"), BannerItem("https://www.naver.com", "123123123"))
            )
        ).await()

         */
        /*
        Firebase.firestore.collection("home").document("banner").get().await().also {
            it.toObject<Banner>()?.let { banner ->
                Log.d("BannerTest", banner.toString())
            }
        }

         */
    }
}

/*
data class Banner(
    val item: List<BannerItem> = emptyList()
)

data class BannerItem(
    val url: String = "",
    val id: String = ""
)

 */

/*
data class Banner(
    val notice: String = "",
    val item: List<BannerItem> = emptyList()
)

data class BannerItem(
    val url: String = "",
    val id: String = ""
)

 */