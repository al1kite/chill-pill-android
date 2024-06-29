package ddwu.com.mobile.finalreport.data.remote.dose

import ddwu.com.mobile.finalreport.data.model.community.CommunityResponse
import ddwu.com.mobile.finalreport.data.model.dose.DiaryRequest
import ddwu.com.mobile.finalreport.data.model.dose.DiaryResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DiaryService {
    @POST("/api/diary")
    fun createDiary(@Body diaryRequest: DiaryRequest): Call<ResponseBody>

    @POST("/api/diary/{diarySeq}")
    fun updateDiary(@Path("diarySeq") seq : Long, @Body diaryRequest: DiaryRequest): Call<ResponseBody>

    @GET("/api/diary")
    fun getTodayDiary() : Call<DiaryResponse>

    @GET("/api/diary/my-list")
    fun getMyDairyList(@Query("keyword") keyword: String?): Call<ArrayList<CommunityResponse>>

    @DELETE("/api/diary/{diarySeq}")
    fun deleteDiaryItem(@Path("diarySeq") seq : Long?) : Call<ResponseBody>
}