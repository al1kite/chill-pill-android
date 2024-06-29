package ddwu.com.mobile.finalreport.feature.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator

class CustomPillButton(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val leftPaint = Paint().apply {
        color = 0xFFD7E2F1.toInt() // 왼쪽 반원의 색상
        isAntiAlias = true
    }

    private val rightPaint = Paint().apply {
        color = 0xFFCD2B5B.toInt() // 오른쪽 반원의 색상
        isAntiAlias = true
    }

    private var isAnimating = false

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val radius = height / 2

        // 왼쪽 약 그리기
        val leftPath = Path().apply {
            addRoundRect(0f, 0f, width, height, radius, radius, Path.Direction.CW)
        }
        canvas.save()
        canvas.clipRect(0f, 0f, width / 2, height)
        canvas.drawPath(leftPath, leftPaint)
        canvas.restore()

        // 오른쪽 약 그리기
        val rightPath = Path().apply {
            addRoundRect(0f, 0f, width, height, radius, radius, Path.Direction.CW)
        }
        canvas.save()
        canvas.clipRect(width / 2, 0f, width, height)
        canvas.drawPath(rightPath, rightPaint)
        canvas.restore()
    }

    fun animatePill() {
        isAnimating = true

        val distance = width / 8f // 분리 후 각 반의 이동 거리

        val animator = ObjectAnimator.ofFloat(this, "rotation", 0f, 45f).apply {
            duration = 500 // 애니메이션 지속 시간
            interpolator = AccelerateInterpolator() // 가속도 보정
        }

        // AnimatorSet to play both animations together
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator)
        animatorSet.start()

        // Reset animation state after animation ends
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                isAnimating = false
                // Optionally handle any post-animation logic here
            }
        })
    }
}