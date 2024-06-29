package ddwu.com.mobile.finalreport

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport.databinding.ActivitySplashBinding
import ddwu.com.mobile.finalreport.feature.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            with(binding) {
                pillButton.animatePill()

                Handler(Looper.getMainLooper()).postDelayed({
                    chillPillTitleView.visibility = View.VISIBLE
                }, 500) // pillButton 애니메이션 후 0.5초 뒤에 TextView 보이기

                Handler(Looper.getMainLooper()).postDelayed({
                    chillPillSubTitleView.visibility = View.VISIBLE
                    val appearAnimation =
                        AnimationUtils.loadAnimation(this@SplashActivity, R.anim.text_appear)
                    chillPillSubTitleView.startAnimation(appearAnimation)
                }, 1200) // pillButton 애니메이션 후 0.7초 뒤에 TextView 보이기
            }
        }, 1000) // 1초 뒤에 pillButton 애니메이션 실행

        // 3.5초 딜레이 이후 LoginActivity로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3500)
    }
}
