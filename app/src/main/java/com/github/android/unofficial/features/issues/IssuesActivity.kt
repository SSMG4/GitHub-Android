package com.github.android.unofficial.features.issues

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.android.unofficial.ui.Nav

class IssuesActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    title = "Issues"
    // Placeholder: navigate to renderer for now
    Nav.toMarkdown(this)
    finish()
  }
}
