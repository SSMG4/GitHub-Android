package com.github.android.unofficial.ui

import android.content.Context
import android.content.Intent
import com.github.android.unofficial.features.issues.IssuesActivity
import com.github.android.unofficial.features.prs.PullRequestsActivity
import com.github.android.unofficial.features.repos.RepositoryActivity
import com.github.android.unofficial.webview.MarkdownActivity

object Nav {
  fun toMarkdown(context: Context) {
    context.startActivity(Intent(context, MarkdownActivity::class.java))
  }

  fun toIssues(context: Context) {
    context.startActivity(Intent(context, IssuesActivity::class.java))
  }

  fun toPullRequests(context: Context) {
    context.startActivity(Intent(context, PullRequestsActivity::class.java))
  }

  fun toRepository(context: Context) {
    context.startActivity(Intent(context, RepositoryActivity::class.java))
  }
}
