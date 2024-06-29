package ddwu.com.mobile.finalreport

import android.app.Application
import ddwu.com.mobile.finalreport.data.remote.RetrofitClient

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.initialize(this)
    }
}
