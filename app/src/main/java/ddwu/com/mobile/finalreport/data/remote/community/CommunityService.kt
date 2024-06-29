package ddwu.com.mobile.finalreport.data.remote.community

import ddwu.com.mobile.finalreport.data.model.community.CommunityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityService {
    @GET("/api/diary/list")
    fun getCommunityList(@Query("keyword") keyword: String?): Call<ArrayList<CommunityResponse>>
}
