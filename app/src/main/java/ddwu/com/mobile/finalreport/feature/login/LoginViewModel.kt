package ddwu.com.mobile.finalreport.feature.login

import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ddwu.com.mobile.finalreport.MainActivity
import ddwu.com.mobile.finalreport.data.model.login.LoginRequest
import ddwu.com.mobile.finalreport.data.remote.RetrofitClient
import ddwu.com.mobile.finalreport.data.remote.login.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var email: String = ""
    var password: String = ""
    private val authService = RetrofitClient.createService<AuthService>()

    fun onLoginButtonClick() {
        val loginRequest = LoginRequest(email, password)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authService.login(loginRequest).execute()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val tokenResponse = response.body()
                        tokenResponse?.accessToken?.let { accessToken ->
                            saveAccessToken(accessToken)

                            getApplication<Application>().startActivity(Intent(getApplication(), MainActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            })
                        }
                    } else {
                        Toast.makeText(getApplication(), "로그인 실패: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication(), "네트워크 오류: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveAccessToken(accessToken: String) {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", accessToken)
        editor.apply()
    }
}