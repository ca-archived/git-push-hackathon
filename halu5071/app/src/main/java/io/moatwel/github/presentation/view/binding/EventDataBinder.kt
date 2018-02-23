package io.moatwel.github.presentation.view.binding

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import io.moatwel.github.R
import io.moatwel.github.domain.entity.event.*
import io.moatwel.github.presentation.view.GlideApp

object EventDataBinder {

  @JvmStatic
  @BindingAdapter("imageUrl")
  fun loadUserImage(view: ImageView, avatarUrl: String) {
    GlideApp.with(view.context)
      .load(avatarUrl)
      .circleCrop()
      .into(view)
  }

  @JvmStatic
  @BindingAdapter("content")
  fun setEventContent(textView: TextView, event: Event) {
    val context = textView.context
    when (event.type) {
      EventType.CommitCommentEvent -> textView.text = event.type.name
      EventType.CreateEvent -> {
        textView.text =
          context.getString(R.string.str_create_event, event.actor?.login, event.repo?.name)
      }
      EventType.DeleteEvent -> {
        textView.text =
          context.getString(R.string.str_delete_event,
            event.actor?.login,
            (event.payload as DeletePayload).refType,
            event.repo?.name)
      }
      EventType.ForkEvent -> {
        textView.text = context.getString(R.string.str_fork_event, event.actor?.login, event.repo?.name)
      }
      EventType.GollumEvent -> textView.text = event.type.name
      EventType.IssueCommentEvent -> {
        textView.text =
          context.getString(R.string.str_issue_comment, event.actor?.login, event.repo?.name)
      }
      EventType.IssuesEvent -> {
        textView.text = context.getString(R.string.str_issue_event,
          event.actor?.login,
          (event.payload as IssuesPayload).action,
          event.repo?.name)
      }
      EventType.MemberEvent -> {
        textView.text =
          context.getString(R.string.str_member_event,
            event.actor?.login,
            (event.payload as MemberPayload).action,
            event.repo?.name)
      }
      EventType.PublicEvent -> {
        textView.text = context.getString(R.string.str_public_event, event.actor?.login, event.repo)
      }
      EventType.PullRequestEvent -> {
        textView.text =
          context.getString(R.string.str_pr_event,
            event.actor?.login,
            (event.payload as PullRequestPayload).action,
            event.repo?.name)
      }
      EventType.PullRequestReviewCommentEvent -> {
        textView.text = context.getString(R.string.str_pr_comment,
          event.actor?.login,
          event.repo?.name,
          (event.payload as PullRequestReviewCommentPayload).pullRequest.number)
      }
      EventType.PullRequestReviewEvent -> {
        textView.text =
          context.getString(R.string.str_pr_review,
            event.actor?.login,
            (event.payload as PullRequestReviewPayload).action)
      }
      EventType.PushEvent -> {
        textView.text = context.getString(R.string.str_push_event,
          event.actor?.login,
          (event.payload as PushPayload).ref.substring(11),
          event.repo?.name)
      }
      EventType.TeamAddEvent -> {
        textView.text =
          context.getString(R.string.str_team_add_event,
            event.repo?.name,
            (event.payload as TeamAddPayload).team.name,
            event.actor?.login)
      }
      EventType.WatchEvent -> {
        textView.text =
          context.getString(R.string.str_watch_event,
            event.actor?.login,
            (event.payload as WatchPayload).action,
            event.repo?.name)
      }
      else -> textView.text = "サンプル"
    }
  }

  @JvmStatic
  @BindingAdapter("comment")
  fun setComment(textView: TextView, event: Event) {
    textView.visibility = View.VISIBLE
    val context = textView.context
    when (event.type) {
      EventType.CommitCommentEvent -> {
        textView.text = (event.payload as CommitCommentPayload).comment.body
      }
      EventType.PullRequestReviewCommentEvent -> {
        textView.text = (event.payload as PullRequestReviewCommentPayload).comment.body
      }
      EventType.IssueCommentEvent -> {
        textView.text = (event.payload as IssueCommentPayload).comment.body
      }
      EventType.PullRequestReviewEvent -> {
        textView.text = (event.payload as PullRequestReviewPayload).comment.body
      }
      EventType.IssuesEvent -> {
        val payload = event.payload as IssuesPayload
        textView.text =
          context.getString(R.string.str_issue_title, payload.issue.number, payload.issue.title)
      }
      EventType.PullRequestEvent -> {
        val payload = event.payload as PullRequestPayload
        textView.text =
          context.getString(R.string.str_pr_title,
            payload.pullRequest.number,
            payload.pullRequest.title)
      }
      else -> textView.visibility = View.GONE
    }
  }
}