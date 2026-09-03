package com.example.food_app

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.PathInterpolator
import kotlin.random.Random

/**
 * Isim listesine gore dilimlere ayrilan, dokununca donen bir carktir.
 * Kazanan isim spin() cagrilmadan once rastgele secilir; donus,
 * o dilimi ustteki sabit ok isaretine getirecek sekilde hesaplanir.
 */
class WheelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var names: List<String> = emptyList()
        set(value) {
            field = value
            invalidate()
        }

    private var currentRotation = 0f
    private var spinAnimator: ValueAnimator? = null

    private val sliceColors = listOf(
        Color.parseColor("#2C2030"),
        Color.parseColor("#4A3524"),
        Color.parseColor("#3A2A3D"),
        Color.parseColor("#5A4028")
    )

    private val slicePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f
        color = Color.parseColor("#181019")
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#F3ECE2")
        textAlign = Paint.Align.CENTER
    }
    private val hubPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#181019")
    }
    private val hubStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.parseColor("#CAA15A")
    }

    private val arcRect = RectF()
    private val textBounds = Rect()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val n = names.size
        if (n == 0) return

        val cx = width / 2f
        val cy = height / 2f
        val radius = minOf(width, height) / 2f - 8f
        arcRect.set(cx - radius, cy - radius, cx + radius, cy + radius)

        val segAngle = 360f / n
        val top = -90f // 12 yonu, sabit ok buraya bakiyor

        canvas.save()
        canvas.rotate(currentRotation, cx, cy)

        for (i in 0 until n) {
            val start = top + i * segAngle
            slicePaint.color = sliceColors[i % sliceColors.size]
            canvas.drawArc(arcRect, start, segAngle, true, slicePaint)
            canvas.drawArc(arcRect, start, segAngle, true, strokePaint)

            val mid = start + segAngle / 2
            val labelRadius = radius * 0.62f
            textPaint.textSize = when {
                n > 8 -> 26f
                n > 5 -> 30f
                else -> 34f
            }
            val label = names[i].let { if (it.length > 12) it.take(11) + "…" else it }
            textPaint.getTextBounds(label, 0, label.length, textBounds)

            canvas.save()
            canvas.rotate(mid, cx, cy)
            // Alt yarimda kalan etiketleri ters donmesin diye kendi ekseninde 180 cevir
            val normalizedMid = ((mid % 360f) + 360f) % 360f
            if (normalizedMid in 90f..270f) {
                canvas.rotate(180f, cx, cy - labelRadius)
            }
            canvas.drawText(label, cx, cy - labelRadius + textBounds.height() / 2f, textPaint)
            canvas.restore()
        }

        canvas.drawCircle(cx, cy, radius * 0.16f, hubPaint)
        canvas.drawCircle(cx, cy, radius * 0.16f, hubStrokePaint)

        canvas.restore()
    }

    /**
     * Cark donusunu baslatir. Kazanan isim animasyon basinda belirlenir,
     * onResult donus bittiginde ana thread'de cagrilir.
     */
    fun spin(onResult: (String) -> Unit) {
        val n = names.size
        if (n < 2 || spinAnimator?.isRunning == true) return

        val winnerIndex = Random.nextInt(n)
        val segAngle = 360f / n
        val winnerCenter = winnerIndex * segAngle + segAngle / 2

        // Kazanan dilimin ortasi donus sonunda ust (0) noktasina gelmeli
        val targetMod = (360f - winnerCenter + 360f) % 360f
        val currentMod = ((currentRotation % 360f) + 360f) % 360f
        val spins = 6
        val delta = (targetMod - currentMod + 360f) % 360f + spins * 360f

        val start = currentRotation
        val end = currentRotation + delta

        spinAnimator?.cancel()
        spinAnimator = ValueAnimator.ofFloat(start, end).apply {
            duration = 4200
            interpolator = PathInterpolator(0.17f, 0.67f, 0.12f, 1f)
            addUpdateListener {
                currentRotation = it.animatedValue as Float
                invalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    onResult(names[winnerIndex])
                }
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
            start()
        }
    }
}