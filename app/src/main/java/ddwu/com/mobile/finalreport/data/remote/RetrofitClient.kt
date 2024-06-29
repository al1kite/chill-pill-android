package ddwu.com.mobile.finalreport.data.remote

import android.annotation.SuppressLint
import android.content.Context
import ddwu.com.mobile.finalreport.data.remote.login.AuthInterceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager

@SuppressLint("StaticFieldLeak")
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"
    private val cookieManager = CookieManager()
    private lateinit var context: Context

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }

    private val builder: OkHttpClient by lazy {
        if (!this::context.isInitialized) {
            throw IllegalStateException("RetrofitClient is not initialized. Call RetrofitClient.initialize(context) in your Application class.")
        }
        OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(cookieManager))
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builder)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    internal inline fun <reified T> createService(): T {
        return retrofit.create(T::class.java)
    }
}
