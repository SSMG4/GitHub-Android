package com.github.android.unofficial.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.android.unofficial.databinding.ActivityMainBinding
import com.github.android.unofficial.webview.MarkdownActivity

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.buttonOpenRenderer.setOnClickListener {
      startActivity(Intent(this, MarkdownActivity::class.java))
    }
  }
}
