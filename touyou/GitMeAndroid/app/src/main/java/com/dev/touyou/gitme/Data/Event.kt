package com.dev.touyou.gitme.Data

import java.net.URL
import java.util.*

/**
 * Created by touyou on 2018/02/25.
 */

data class Event(
        val actor: Actor,
        val createdAt: Date,
        val id: String,
        val org: Organization?,
        val payload: Payload,
        val repo: Repository?,
        val isPublic: Boolean,
        val type: EventType
)

enum class EventType(val rawValue: String) {
    CommitComment("CommitCommentEvent"),
    Create("CreateEvent"),
    Delete("DeleteEvent"),
    Deployment("DeploymentEvent"),
    DeploymentStatus("DeploymentStatusEvent"),
    Fork("ForkEvent"),
    Gollum("GollumEvent"),
    Installation("InstallationEvent"),
    InstallationRepositories("InstallationRepositoriesEvent"),
    IssueComment("IssueCommentEvent"),
    Issues("IssuesEvent"),
    Label("LabelEvent"),
    MarketplacePurchase("MarketplacePurchaseEvent"),
    Member("MemberEvent"),
    Membership("MembershipEvent"),
    Milestone("MilestoneEvent"),
    Organization("OrganizationEvent"),
    OrgBlock("OrgBlockEvent"),
    PageBuild("PageBuildEvent"),
    ProjectCard("ProjectCardEvent"),
    ProjectColumn("ProjectColumnEvent"),
    Project("ProjectEvent"),
    Public("PublicEvent"),
    PullRequest("PullRequestEvent"),
    PullRequestReview("PullRequestReviewEvent"),
    PullRequestReviewComment("PullRequestReviewComment"),
    Push("PushEvent"),
    Release("ReleaseEvent"),
    Repository("RepositoryEvent"),
    Status("StatusEvent"),
    Team("TeamEvent"),
    TeamAdd("TeamAddEvent"),
    Watch("WatchEvent")
}

data class Actor(
        val avatarUrl: URL,
        val displayLogin: String,
        val gravatarId: String?,
        val id: Int,
        val login: String,
        val url: URL
)

data class Organization(
        val avatarUrl: URL,
        val gravatarId: String?,
        val id: Int,
        val login: String,
        val url: URL
)