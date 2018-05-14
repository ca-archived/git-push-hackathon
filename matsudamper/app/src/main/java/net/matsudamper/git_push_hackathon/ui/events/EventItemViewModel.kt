package net.matsudamper.git_push_hackathon.ui.events

import android.databinding.BaseObservable
import android.text.format.DateUtils
import net.matsudamper.git_push_hackathon.R
import net.matsudamper.git_push_hackathon.github.data.Event
import net.matsudamper.git_push_hackathon.github.data.EventType
import net.matsudamper.git_push_hackathon.util.getStringSafe

class EventItemViewModel(val item: Event) : BaseObservable() {

    var name: String? = item.actor?.login

    var action: String? =
            when (item.type) {
                EventType.CreateEvent -> "create ${item.payload?.getStringSafe("ref_type")}"
                EventType.DeleteEvent -> "delete ${item.payload?.getStringSafe("ref_type")}"
                EventType.GollumEvent -> {
                    item.payload?.getJSONArray("pages")?.let {

                        if (it.length() > 0) {
                            return@let "Wiki ${it.getJSONObject(0).getStringSafe("action")}"
                        }
                        return@let null
                    }
                }
                EventType.CommitCommentEvent, EventType.InstallationEvent,
                EventType.InstallationRepositoriesEvent,
                EventType.IssueCommentEvent, EventType.IssuesEvent,
                EventType.MarketplacePurchaseEvent, EventType.MemberEvent,
                EventType.MembershipEvent, EventType.OrgBlockEvent,
                EventType.ProjectCardEvent, EventType.ProjectColumnEvent,
                EventType.ProjectEvent, EventType.PullRequestEvent,
                EventType.PullRequestReviewEvent, EventType.PullRequestReviewCommentEvent,
                EventType.ReleaseEvent, EventType.WatchEvent -> {
                    item.payload?.getStringSafe("action")
                }
                else -> null
            }

    var title: String? = item.repo?.name

    var subText: String? =
            when (item.type) {
                EventType.CommitCommentEvent, EventType.IssueCommentEvent,
                EventType.PullRequestReviewCommentEvent -> item.payload?.getJSONObject("comment")?.getStringSafe("body")
                EventType.ForkEvent -> item.payload?.getJSONObject("forkee")?.getStringSafe("full_name")
                EventType.IssuesEvent -> item.payload?.getJSONObject("issue")?.getStringSafe("body")
                EventType.MemberEvent, EventType.MembershipEvent -> item.payload?.getJSONObject("member")?.getStringSafe("login")
                EventType.PullRequestEvent -> item.payload?.getJSONObject("pull_request")?.getStringSafe("title")
                EventType.PushEvent -> {
                    item.payload?.getJSONArray("commits")?.let {
                        if (it.length() > 0) {
                            return@let it.getJSONObject(0)?.getStringSafe("message")
                        }

                        return@let null
                    }
                }
                EventType.OrgBlockEvent -> item.payload?.getJSONObject("member")?.getStringSafe("blocked_user")
                EventType.ProjectColumnEvent -> item.payload?.getJSONObject("project_column")?.getStringSafe("name")
                EventType.ProjectEvent -> item.payload?.getJSONObject("project")?.getStringSafe("body")
                EventType.PullRequestReviewEvent -> item.payload?.getJSONObject("pull_request")?.getStringSafe("body")

                EventType.ReleaseEvent -> "Release"

                EventType.ProjectCardEvent -> "Project Card"
                EventType.MarketplacePurchaseEvent -> "Marketplace Purchase"

                EventType.PublicEvent -> "Public"
                EventType.InstallationEvent -> "Installation"
                EventType.InstallationRepositoriesEvent -> "Installation Repositories"

                EventType.CreateEvent,
                EventType.GollumEvent,
                EventType.DeleteEvent,
                EventType.WatchEvent -> null
            }

    val time: String?
        get() = item.created_at.let {
            DateUtils.getRelativeTimeSpanString(it.time).toString()
        }

    var avatarUrl: String? = item.actor?.avatar_url

    var iconResId: Int? =
            when (item.type) {
                EventType.WatchEvent -> R.drawable.eye
                EventType.ForkEvent -> R.drawable.repo_forked
                EventType.PushEvent -> R.drawable.repo_push
                EventType.DeleteEvent -> R.drawable.trashcan
                EventType.OrgBlockEvent -> R.drawable.circle_slash
                EventType.CommitCommentEvent -> R.drawable.git_commit

                EventType.PullRequestEvent,
                EventType.PullRequestReviewEvent,
                EventType.PullRequestReviewCommentEvent -> R.drawable.git_pull_request

                EventType.IssuesEvent,
                EventType.IssueCommentEvent -> R.drawable.issue_opened

                EventType.MemberEvent,
                EventType.MembershipEvent -> R.drawable.organization

                EventType.ProjectCardEvent,
                EventType.ProjectColumnEvent,
                EventType.ProjectEvent -> R.drawable.project

                EventType.CreateEvent, EventType.GollumEvent, EventType.InstallationEvent,
                EventType.InstallationRepositoriesEvent, EventType.MarketplacePurchaseEvent,
                EventType.PublicEvent, EventType.ReleaseEvent -> null
            }

    fun onClickIcon() {

    }
}