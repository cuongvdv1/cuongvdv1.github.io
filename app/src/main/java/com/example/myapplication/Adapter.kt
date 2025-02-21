package com.example.myapplication

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class Adapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val listIcon = listOf(
        R.drawable.frame_3,
        R.drawable.frame_1,
        R.drawable.frame_2,
        R.drawable.frame_3,
        R.drawable.frame_1,
    )

    override fun getItemCount() = listIcon.size

    override fun createFragment(p0: Int) = TestFragment.getInstance(listIcon[p0])
}
