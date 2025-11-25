package com.github.android.unofficial.features.prs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.android.unofficial.ui.Nav

class PullRequestsActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    title = "Pull Requests"
    // Placeholder: navigate to renderer for now
    Nav.toMarkdown(this)
    finish()
  }
}
