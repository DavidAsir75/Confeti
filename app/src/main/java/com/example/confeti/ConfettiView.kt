package com.example.confeti

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class ConfettiView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private val confettiCount = 200// Reducimos el número de partículas para simplificar
    private val confettiX = FloatArray(confettiCount)
    private val confettiY = FloatArray(confettiCount)
    private val confettiColors = IntArray(confettiCount)
    private val confettiSpeed = FloatArray(confettiCount)

    init {
        for (i in 0 until confettiCount) {
            confettiX[i] = Random.nextFloat() * 1000  // Valor inicial corregido después
            confettiY[i] = Random.nextFloat() * 1000
            confettiColors[i] = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            confettiSpeed[i] = Random.nextFloat() * 5 + 2  // Velocidad sencilla
        }
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        for (i in 0 until confettiCount) {
            confettiX[i] = Random.nextFloat() * width
            confettiY[i] = Random.nextFloat() * height
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until confettiCount) {
            paint.color = confettiColors[i]
            canvas.drawCircle(confettiX[i], confettiY[i], 10f, paint)
            confettiY[i] += confettiSpeed[i]
            if (confettiY[i] > height) {
                confettiY[i] = 0f
                confettiX[i] = Random.nextFloat() * width
            }
        }
        invalidate()  // Redibujar continuamente para la animación
    }
}
