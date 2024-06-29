package ddwu.com.mobile.finalreport.data.remote.login

import ddwu.com.mobile.finalreport.data.model.login.LoginRequest
import ddwu.com.mobile.finalreport.data.model.login.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<TokenResponse>
}