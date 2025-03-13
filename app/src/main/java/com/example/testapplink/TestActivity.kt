package com.example.testapplink

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.testapplink.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTestBinding.inflate(layoutInflater) }

    private var startX = 0F
    private var minX = 0f
    private var maxX = 0f
    private var isFirstTime = true
    private var isDone = false
    private var animator: ValueAnimator? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imgCall.setOnTouchListener { view, motionEvent ->
            val layoutParams = (view?.layoutParams as? ConstraintLayout.LayoutParams)
                ?: return@setOnTouchListener false
            return@setOnTouchListener when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> handleDown(motionEvent, layoutParams)
                MotionEvent.ACTION_MOVE -> handleMove(motionEvent, view, layoutParams)
                MotionEvent.ACTION_UP -> handleUp(view, layoutParams)
                else -> {
                    false
                }
            }
        }
    }

    private fun handleDown(
        motionEvent: MotionEvent,
        layoutParams: ConstraintLayout.LayoutParams
    ): Boolean {
        if (animator?.isRunning == true || isDone) return false
        startX = motionEvent.rawX
        if (isFirstTime) {
            minX = layoutParams.marginStart - 300f
            maxX = layoutParams.marginStart.toFloat()
            isFirstTime = false
        }
        return true
    }

    private fun handleMove(
        motionEvent: MotionEvent,
        view: View,
        layoutParams: ConstraintLayout.LayoutParams
    ): Boolean {
        val endX = motionEvent.rawX
        val dx = startX - endX
        val newMarginStart = (layoutParams.marginStart - dx * 1.5f)
        if (newMarginStart < 0 && newMarginStart >= minX) {
            layoutParams.marginStart = newMarginStart.toInt()
            startX = endX
            view.layoutParams = layoutParams
            isDone = newMarginStart == minX
        } else if (newMarginStart < minX) {
            isDone = true
            layoutParams.marginStart = minX.toInt()
        } else {
            isDone = false
        }
        return true
    }

    private fun handleUp(
        view: View,
        layoutParams: ConstraintLayout.LayoutParams
    ): Boolean {
        if (isDone) {
            actionDone()
            return true
        }
        animator =
            ValueAnimator.ofFloat(layoutParams.marginStart.toFloat(), maxX).apply {
                addUpdateListener { value ->
                    val params =
                        (view.layoutParams as? ConstraintLayout.LayoutParams)
                    (value.animatedValue as? Float)?.let {
                        params?.marginStart = it.toInt()
                    }
                    view.layoutParams = params
                }
                duration = 300
                interpolator = LinearInterpolator()
                start()
            }
        return true
    }

    private fun actionDone() {
        Toast.makeText(this, "doneeeee", Toast.LENGTH_LONG).show()
    }
}
