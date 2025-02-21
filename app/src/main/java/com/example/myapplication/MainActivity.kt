package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { Adapter(this) }
    private var pageChanged = false
    private var isScroll = false
    private val delayTime = 3000L

    private val autoSlide = Runnable {
        val currentItem = binding.vp.currentItem
        val nextItem = max(0, (currentItem + 1) % adapter.itemCount)
        binding.vp.setCurrentItem(nextItem, true)
    }

    private val slideHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.vp.adapter = adapter
        binding.vp.offscreenPageLimit = 2
        binding.vp.setCurrentItem(1, true)
        slideHandler.removeCallbacksAndMessages(null)
        slideHandler.postDelayed(autoSlide, delayTime)
        binding.indicator.attachToPager(binding.vp)

        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pageChanged = true
                Log.v("tag111", "onPageSelected: $position")
                if (isScroll.not() && (position == 1 || adapter.itemCount - 2 == position)) {
                    slideHandler.removeCallbacksAndMessages(null)
                    slideHandler.postDelayed(autoSlide, delayTime)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        Log.v("tag111", "SCROLL_STATE_IDLE")
                        handleScrollIdle()
                    }
                    ViewPager2.SCROLL_STATE_SETTLING -> {
                        Log.v("tag111", "SCROLL_STATE_SETTLING")
                        isScroll = true
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        Log.v("tag111", "SCROLL_STATE_DRAGGING")
                        slideHandler.removeCallbacksAndMessages(null)
                    }
                }
            }
        })
    }


    private fun handleScrollIdle() {
        if (pageChanged) {
            pageChanged = false
            isScroll = false
            slideHandler.removeCallbacksAndMessages(null)
            val position = binding.vp.currentItem
            if (position == adapter.itemCount - 1) {
                binding.vp.setCurrentItem(1, false)
            } else if (position == 0) {
                binding.vp.setCurrentItem(adapter.itemCount - 2, false)
            } else {
                slideHandler.postDelayed(autoSlide, delayTime)
            }
        }
    }
}