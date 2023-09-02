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
import com.wantique.firebase.model.CoverDto
import com.wantique.firebase.model.DailyLetterDto
import com.wantique.firebase.model.DailyRecordDto
import com.wantique.firebase.model.ProfessorDto
import com.wantique.firebase.model.ProfessorInfoDto
import com.wantique.firebase.model.RecordDto
import com.wantique.firebase.model.RecordHeaderDto
import com.wantique.firebase.model.ReferenceKey
import com.wantique.firebase.model.UserDto
import com.wantique.firebase.model.YearlyCurriculumDto
import com.wantique.firebase.model.YearlyExamPlanDto
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class Firebase private constructor() {
    /** 로그인 화면에 출력할 배경 이미지를 받아오는 메서드 */
    suspend fun getCoverImage(): CoverDto? {
        return Firebase.firestore.collection("app_image").document("cover").get().await().run {
            toObject<CoverDto>()
        }
    }

    /** 현재 사용자가 이미 Firestore 에 등록된 사용자인지 확인하는 메서드 */
    suspend fun isExistUser(): Boolean {
        return Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).get().await().exists()
    }

    /** 회원탈퇴를 신청한 사용자인지 확인하는 메서드 */
    suspend fun checkWithdrawalUser(): Boolean {
        return Firebase.firestore.collection("withdrawal").document(Firebase.auth.uid.toString()).get().await().exists()
    }

    /** Firestore 에 사용자 정보를 등록하는 메서드 */
    suspend fun registerUser(imageUri: String, nickName: String): Boolean {
        if(imageUri.isNotEmpty()) {
            Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).set(UserDto(Firebase.auth.uid.toString(), nickName, uploadProfileImage(imageUri))).await()
            return Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).get().await().exists()
        } else {
            Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).set(UserDto(Firebase.auth.uid.toString(), nickName, imageUri)).await()
            return Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).get().await().exists()
        }
    }

    /** 회원탈퇴를 철회하는 메서드 */
    suspend fun redoUser(): Boolean {
        Firebase.firestore.collection("withdrawal").document(Firebase.auth.uid.toString()).delete().await()
        return !Firebase.firestore.collection("withdrawal").document(Firebase.auth.uid.toString()).get().await().exists()
    }

    /** 중복된 닉네임인지 확인하는 메서드 */
    suspend fun isDuplicateNickName(nickName: String): Boolean {
        return !Firebase.firestore.collection("user").whereEqualTo("nickName", nickName).get().await().isEmpty
    }

    /** 홈 상단 배너 정보를 가져오는 메서드 */
    suspend fun getBanner(): BannerDto? {
        return Firebase.firestore.collection("home").document("banner").get().await().run {
            toObject<BannerDto>()
        }
    }

    /** 과목별 카테고리를 가져오는 메서드 */
    suspend fun getCategory(): CategoryDto? {
        return Firebase.firestore.collection("home").document("category").get().await().run {
            toObject<CategoryDto>()
        }
    }

    /** 과목별 교수님 정보를 가져오는 메서드 */
    suspend fun getProfessors(): List<ProfessorDto> {
        val professorDtoList = mutableListOf<ProfessorDto>()

        Firebase.firestore.collection("home").document("korean").get().await().also { snapshot ->
            snapshot.toObject<ProfessorDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorDto(emptyList()))
            }
        }

        Firebase.firestore.collection("home").document("english").get().await().also { snapshot ->
            snapshot.toObject<ProfessorDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorDto(emptyList()))
            }
        }

        Firebase.firestore.collection("home").document("history").get().await().also { snapshot ->
            snapshot.toObject<ProfessorDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorDto(emptyList()))
            }
        }

        Firebase.firestore.collection("home").document("administrative_law").get().await().also { snapshot ->
            snapshot.toObject<ProfessorDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorDto(emptyList()))
            }
        }

        Firebase.firestore.collection("home").document("public_administration").get().await().also { snapshot ->
            snapshot.toObject<ProfessorDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorDto(emptyList()))
            }
        }

        return professorDtoList
    }

    /** 연간 시험 정보를 가져오는 메서드 */
    suspend fun getYearlyExam(): YearlyExamPlanDto? {
        return Firebase.firestore.collection("home").document("yearly_exam").get().await().run {
            toObject<YearlyExamPlanDto>()
        }
    }

    /** 특정 교수님의 연간 커리큘럼 정보를 가져오는 메서드 */
    suspend fun getProfessorCurriculum(professorId: String): YearlyCurriculumDto? {
        getProfessorDetailsReferenceKey()?.let { referenceKey ->
            return Firebase.firestore.collection("professor_details").document(professorId).collection(referenceKey.key).document("curriculum").get().await().run {
                toObject<YearlyCurriculumDto>()
            }
        } ?: run {
            return null
        }
    }

    /** 특정 교수님의 이름, 슬로건, 공식 홈페이지 정보를 가져오는 메서드 */
    suspend fun getProfessorInfo(professorId: String): ProfessorInfoDto? {
        getProfessorDetailsReferenceKey()?.let { referenceKey ->
            return Firebase.firestore.collection("professor_details").document(professorId).collection(referenceKey.key).document("info").get().await().run {
                toObject<ProfessorInfoDto>()
            }
        } ?: run {
            return null
        }
    }

    suspend fun registerRecord(imageUri: String, body: String): Boolean {
        getRecordReferenceKey()?.let { referenceKey ->
            val documentId = "${referenceKey.key}-(${SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)})-${System.currentTimeMillis()}-${Firebase.auth.uid.toString()}"
            val imageUrl = if(imageUri.isNotEmpty()) uploadRecordImage(imageUri) else ""
            val record = RecordDto(Firebase.auth.uid.toString(), documentId, referenceKey.key, false, imageUrl, body)
            Firebase.firestore.collection("daily").document("recordHeader").collection("record").document(documentId).set(record).await()
            Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).collection("record").document(documentId).set(record).await()

            return Firebase.firestore.collection("daily").document("recordHeader").collection("record").document(documentId).get().await().exists()
                    && Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).collection("record").document(documentId).get().await().exists()
        } ?: run {
            return false
        }
    }

    suspend fun getDailyLetter(): DailyLetterDto? {
        return Firebase.firestore.collection("daily").document("letter").get().await().run {
            toObject<DailyLetterDto>()
        }
    }

    suspend fun getDailyRecord(): DailyRecordDto? {
        getRecordReferenceKey()?.let { referenceKey ->
            val header = Firebase.firestore.collection("daily").document("recordHeader").get().await().run {
                toObject<RecordHeaderDto>()
            }

            val records = Firebase.firestore.collection("daily").document("recordHeader").collection("record")
                .whereEqualTo("date", referenceKey.key).whereEqualTo("enable", true).get().await().run {
                    toObjects<RecordDto>()
                }
            header?.let {
                return DailyRecordDto(it, records)
            } ?: return null
        } ?: return null
    }

    private suspend fun getProfessorDetailsReferenceKey(): ReferenceKey? {
        return Firebase.firestore.collection("reference").document("professor_details").get().await().run {
            toObject<ReferenceKey>()
        }
    }

    private suspend fun getRecordReferenceKey(): ReferenceKey? {
        return Firebase.firestore.collection("reference").document("record").get().await().run {
            toObject<ReferenceKey>()
        }
    }

    /** 사용자 프로필 이미지를 Storage 에 저장하는 메서드 */
    private suspend fun uploadProfileImage(imageUri: String): String {
        val ref = Firebase.storage.reference.child("profile").child(Firebase.auth.uid.toString()).child("profileImage.jpg")
        return ref.putFile(imageUri.toUri()).await().storage.downloadUrl.await().toString()
    }

    private suspend fun uploadRecordImage(imageUri: String): String {
        val ref = Firebase.storage.reference.child("record").child(Firebase.auth.uid.toString()).child("${System.currentTimeMillis()}.jpg")
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
    suspend fun chainingTest() {
        /*
        Firebase.firestore.collection("daily").document("record").collection("record").document(System.currentTimeMillis().toString()).set(
            RecordDto(
                Firebase.auth.uid.toString(),
                "2023_09_01",
                "whereEqualTo Chaining 테스트",
                false
            )
        ).await()

         */
        Firebase.firestore.collection("daily").document("record").collection("record").whereEqualTo("date", "2023_09_01").whereEqualTo("enable", true).get().await().also {
            it.toObjects<RecordDto>().apply {
                Log.d("RecordTest", this.toString())
            }
        }
    }
}
