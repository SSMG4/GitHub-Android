package com.github.android.unofficial.features.repos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.android.unofficial.ui.Nav

class RepositoryActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    title = "Repository"
    // Placeholder: navigate to renderer for now
    Nav.toMarkdown(this)
    finish()
  }
}
