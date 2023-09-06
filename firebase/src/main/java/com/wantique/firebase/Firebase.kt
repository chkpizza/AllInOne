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
import com.wantique.firebase.model.ChoiceDto
import com.wantique.firebase.model.CoverDto
import com.wantique.firebase.model.DailyLetterDto
import com.wantique.firebase.model.DailyPastExamDto
import com.wantique.firebase.model.DailyRecordDto
import com.wantique.firebase.model.DescriptionDto
import com.wantique.firebase.model.PastExamDto
import com.wantique.firebase.model.PastExamHeaderDto
import com.wantique.firebase.model.ProfessorInfoDto
import com.wantique.firebase.model.ProfessorPreviewDto
import com.wantique.firebase.model.ProfessorPreviewItemDto
import com.wantique.firebase.model.RecordDto
import com.wantique.firebase.model.RecordHeaderDto
import com.wantique.firebase.model.ReferenceKey
import com.wantique.firebase.model.ReportDto
import com.wantique.firebase.model.TodayPastExamDto
import com.wantique.firebase.model.TodayRecordDto
import com.wantique.firebase.model.UserDto
import com.wantique.firebase.model.YearlyCurriculumDto
import com.wantique.firebase.model.YearlyExamPlanDto
import kotlinx.coroutines.tasks.await
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar

class Firebase private constructor() {
    fun signOut() {
        Firebase.auth.signOut()
    }

    fun getCurrentUserUid(): String {
        return Firebase.auth.uid.toString()
    }

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
    suspend fun getProfessors(): List<ProfessorPreviewDto> {
        val professorDtoList = mutableListOf<ProfessorPreviewDto>()

        Firebase.firestore.collection("home").document("professor").collection("preview").document("korean").get().await().also { snapshot ->
            snapshot.toObject<ProfessorPreviewDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorPreviewDto(emptyList()))
            }
        }

        Firebase.firestore.collection("home").document("professor").collection("preview").document("english").get().await().also { snapshot ->
            snapshot.toObject<ProfessorPreviewDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorPreviewDto(emptyList()))
            }
        }

        Firebase.firestore.collection("home").document("professor").collection("preview").document("history").get().await().also { snapshot ->
            snapshot.toObject<ProfessorPreviewDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorPreviewDto(emptyList()))
            }
        }

        Firebase.firestore.collection("home").document("professor").collection("preview").document("administrative_law").get().await().also { snapshot ->
            snapshot.toObject<ProfessorPreviewDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorPreviewDto(emptyList()))
            }
        }

        Firebase.firestore.collection("home").document("professor").collection("preview").document("public_administration").get().await().also { snapshot ->
            snapshot.toObject<ProfessorPreviewDto>()?.let {
                professorDtoList.add(it)
            } ?: run {
                professorDtoList.add(ProfessorPreviewDto(emptyList()))
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
            return Firebase.firestore.collection("home").document("professor").collection("details").document(professorId).collection(referenceKey.key).document("curriculum").get().await().run {
                toObject<YearlyCurriculumDto>()
            }
        } ?: run {
            return null
        }
    }

    /** 특정 교수님의 이름, 슬로건, 공식 홈페이지 정보를 가져오는 메서드 */
    suspend fun getProfessorInfo(professorId: String): ProfessorInfoDto? {
        getProfessorDetailsReferenceKey()?.let { referenceKey ->
            return Firebase.firestore.collection("home").document("professor").collection("details").document(professorId).collection(referenceKey.key).document("information").get().await().run {
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

            val record = Firebase.firestore.collection("daily").document("recordHeader").collection("record")
                .whereEqualTo("date", referenceKey.key).whereEqualTo("enable", true).get().await().run {
                    toObjects<RecordDto>()
                }

            val report = Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).collection("reportRecord").get().await().run {
                toObjects<ReportDto>()
            }

            val filteredRecord = (record.associateBy { it.documentId } - report.map { it.documentId }.toSet()).values.toList()

            header?.let { 
                return DailyRecordDto(it, filteredRecord.mapNotNull { _record ->
                    getUserProfile(_record.authorUid)?.let { user ->
                        TodayRecordDto(_record.authorUid, _record.documentId, _record.date, _record.imageUrl, _record.body, user.nickName, user.profileImageUrl)
                    }
                })
            } ?: return null
        } ?: return null
    }

    suspend fun removeRecord(documentId: String): Boolean {
        Firebase.firestore.collection("daily").document("recordHeader").collection("record").document(documentId).delete().await()
        Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).collection("record").document(documentId).delete().await()

        return !Firebase.firestore.collection("daily").document("recordHeader").collection("record").document(documentId).get().await().exists()
                && !Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).collection("record").document(documentId).get().await().exists()
    }

    suspend fun reportRecord(documentId: String, reason: String): Boolean {
        val report = ReportDto(
            documentId,
            reason,
            Firebase.auth.uid.toString()
        )

        val reportId = "${System.currentTimeMillis()}-${Firebase.auth.uid.toString()}"

        Firebase.firestore.collection("reportRecord").document(reportId).set(report).await()
        Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).collection("reportRecord").document(reportId).set(report).await()

        return Firebase.firestore.collection("reportRecord").document(reportId).get().await().exists() &&
                Firebase.firestore.collection("user").document(Firebase.auth.uid.toString()).collection("reportRecord").document(reportId).get().await().exists()
    }

    suspend fun getDailyPastExam(): DailyPastExamDto? {
        val header = Firebase.firestore.collection("daily").document("pastExamHeader").get().await().run {
            toObject<PastExamHeaderDto>()
        }

        val todayPastExam = Firebase.firestore.collection("daily").document("pastExamHeader").collection("pastExam").document(DecimalFormat("00").format(LocalDate.now().dayOfMonth)).get().await().run {
            toObject<TodayPastExamDto>()
        }

        if(header == null || todayPastExam == null) {
            return null
        }

        return DailyPastExamDto(header, todayPastExam)
    }

    private suspend fun getUserProfile(uid: String): UserDto? {
        return Firebase.firestore.collection("user").document(uid).get().await().run {
            toObject<UserDto>()
        }
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

    suspend fun set() {
        /*
        Firebase.firestore.collection("daily").document("pastExamHeader").set(
            PastExamHeaderDto(
                "매일 3개의 기출문제 정복 프로젝트",
                "자투리 시간 극한의 활용!"
            )
        ).await()

         */
        Firebase.firestore.collection("daily").document("pastExamHeader").collection("pastExam").document(DecimalFormat("00").format(LocalDate.now().dayOfMonth))
            .set(
                TodayPastExamDto(
                    listOf(
                        PastExamDto(
                            "DESCRIPTION",
                            "다음 중 행정심판법 에 따른 행정심판을 제기할 수 없는 경우만을 모두 고르면? (다툼이 있는 경우 판례에 의함)",
                            listOf(
                                DescriptionDto(
                                    "ㄱ.",
                                    "공공기관의 정보공개에 관한 법률 상 정보공개와 관련한 공공기관의 비공개결정에 대하여 이의신청을 한 경우"
                                ),
                                DescriptionDto(
                                    "ㄴ.",
                                    "공익사업을 위한 토지 등의 취득 및 보상에 관한 법률 상 토지수용위원회의 수용재결에 이의가 있어 중앙토지수용위원회에 이의를 신청한 경우"
                                ),
                                DescriptionDto(
                                    "ㄷ.",
                                    "난민법 상 난민불인정결정에 대해 법무부장관에게 이의신청을 한 경우"
                                ),
                                DescriptionDto(
                                    "ㄹ.",
                                    "민원 처리에 관한 법률 상 법정민원에 대한 행정기관의 장의 거부처분에 대해 그 행정기관의 장에게 이의신청을 한 경우"
                                )
                            ),
                            listOf(
                                ChoiceDto("①", "ㄱ,ㄴ"),
                                ChoiceDto("②", "ㄱ,ㄹ"),
                                ChoiceDto("③", "ㄴ,ㄷ"),
                                ChoiceDto("④.", "ㄷ,ㄹ")
                            ),
                            2,
                            "",
                            "(2023년 지방직 9급 행정법 05번 문제)"
                        ),
                        PastExamDto(
                            "NORMAL",
                            "행정작용에 대한 설명으로 옳은 것은? (다툼이 있는 경우 판례에 의함)",
                            emptyList(),
                            listOf(
                                ChoiceDto(
                                    "①", "구체적인 계획을 입안함에 있어 지침이 되거나 특정 사업의 기본방향을 제시하는 내용의 행정계획은 항고소송의 대상인 행정처분에 해당하지 않는다."
                                ),
                                ChoiceDto(
                                    "②",
                                    "공법상 계약이 법령 위반 등의 내용상 하자가 있는 경우에도 그 하자가 중대명백한 것이 아니면 취소할 수 있는 하자에 불과하고 이에 대한 다툼은 당사자소송에 의하여야 한다"
                                ),
                                ChoiceDto(
                                    "③",
                                    "지도, 권고, 조언 등의 행정지도는 법령의 근거를 요하고 항고소송의 대상이 된다."
                                ),
                                ChoiceDto(
                                    "④",
                                    "국가를 당사자로 하는 계약에 관한 법률 에 따라 국가가 당사자가 되는 이른바 공공계약에 관한 법적 분쟁은 원칙적으로 행정법원의 관할 사항이다."
                                )
                            ),
                            1,
                            "",
                            "(2023년 지방직 9급 행정법 04번 문제)"
                        )
                    )
                )
            ).await()
    }

    suspend fun getPastExam() {
        Firebase.firestore.collection("daily").document("pastExamHeader").collection("pastExam").document(DecimalFormat("00").format(LocalDate.now().dayOfMonth)).get().await().run {
            toObject<TodayPastExamDto>()?.let {
                Log.d("TodayExamTest", it.toString())
            } ?: run {
                Log.d("TodayExamTest", "is null")
            }
        }
    }
}

