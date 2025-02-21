package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentTestBinding

class TestFragment: Fragment() {

    val binding by lazy { FragmentTestBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(KEY_POSITION) ?: -1
        binding.image.setImageResource(position)
    }

    companion object {
        const val KEY_POSITION = "KEY_POSITION"

        fun getInstance(position: Int) = TestFragment().apply {
            arguments = bundleOf(
                KEY_POSITION to position
            )
        }
    }
}
