package com.example.testapplink

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testapplink.databinding.ActivityHandleLinkBinding

class HandleLinkActivity: AppCompatActivity() {

    private val binding by lazy { ActivityHandleLinkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val content = "action: ${intent.action}, uri: ${intent?.data}"
        binding.linkText.text = content
        Log.v("tag111", "action: ${intent.action}, uri: ${intent?.data}")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val content = "action: ${intent.action}, uri: ${intent?.data}"
        binding.linkText.text = content
        Log.v("tag111", "action: ${intent.action}, uri: ${intent?.data}")
    }
}
