package com.wantique.firebase

import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.wantique.firebase.model.BannerDto
import com.wantique.firebase.model.CategoryDto
import com.wantique.firebase.model.CurriculumDto
import com.wantique.firebase.model.DetailCurriculumDto
import com.wantique.firebase.model.ExamDto
import com.wantique.firebase.model.ExamItemDto
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

    suspend fun test() {
        Firebase.firestore.collection("professor_details").document("70C178F56E832D3D15ACFFEC70C181C8F37434B1F8A365FEF11EE99DA4B5024A")
            .collection("2023").document("info")
            .set(ProfessorInfoDto("이선재", "공무원\\n국어 학습의\\n확실한 기준!", ""))
            .await()
    }

    suspend fun readTest() {
        Firebase.firestore.collection("professor_details").document("70C178F56E832D3D15ACFFEC70C181C8F37434B1F8A365FEF11EE99DA4B5024A")
            .collection("2023").document("info").get().await().also {
                it.toObject<ProfessorInfoDto>()?.let { info ->
                    Log.d("ProfessorInfoTest", info.toString())
                }
            }
    }

    suspend fun set() {
        Firebase.firestore.collection("professor_details").document("70C178F56E832D3D15ACFFEC70C181C8F37434B1F8A365FEF11EE99DA4B5024A")
            .collection("2023").document("curriculum")
            .set(YearlyCurriculumDto(
                "70C178F56E832D3D15ACFFEC70C181C8F37434B1F8A365FEF11EE99DA4B5024A",
                "2023",
                "https://cafe.daum.net/sjexam/EnwV/341",
                listOf(
                    CurriculumDto(
                        "기본심화",
                        listOf(
                            DetailCurriculumDto(lecture = "수비니겨 전범위"),
                            DetailCurriculumDto(lecture = "수비니겨 개념 강화(문법/독해/문학/어휘·한자)")
                        )
                    ),
                    CurriculumDto(
                        "기출",
                        listOf(
                            DetailCurriculumDto(lecture = "신(新)기출실록")
                        )
                    ),
                    CurriculumDto(
                        "문제풀이",
                        listOf(
                            DetailCurriculumDto(lecture = "매일국어 Season 1"),
                            DetailCurriculumDto(lecture = "매일국어 Season 2"),
                            DetailCurriculumDto(lecture = "매일국어 Season 3"),
                            DetailCurriculumDto(lecture = "매일국어 Season 4")
                        )
                    ),
                    CurriculumDto(
                        "파이널",
                        listOf(
                            DetailCurriculumDto(lecture = "Final① 한 권으로 정리하는 마무리"),
                            DetailCurriculumDto(lecture = "Final② 반반 난이도 모의고사"),
                            DetailCurriculumDto(lecture = "Final③ 기출 변형 모의고사"),
                            DetailCurriculumDto(lecture = "Final④ 국가직 대비 실전 봉투 모의고사"),
                            DetailCurriculumDto(lecture = "Final⑤ 지방직 대비 실전 봉투 모의고사"),
                        )
                    ),
                    CurriculumDto(
                        "테마",
                        listOf(
                            DetailCurriculumDto(lecture = "독해야 산다"),
                            DetailCurriculumDto(lecture = "약점격파 선재공격(전 범위/영역별 특강)")
                        )
                    )
                )
            )).await()
    }

    suspend fun get() {
        Firebase.firestore.collection("professor_details").document("70C178F56E832D3D15ACFFEC70C181C8F37434B1F8A365FEF11EE99DA4B5024A").collection("2023").document("curriculum").get().await().also {
            it.toObject<YearlyCurriculumDto>()?.let { curriculum ->
                Log.d("CurriculumTest", curriculum.toString())
            }
        }
    }
}


