package net.matsudamper.git_push_hackathon.github.data

// https://developer.github.com/v3/activity/events/types/
enum class EventType {
    CommitCommentEvent,
    CreateEvent,
    DeleteEvent,
    ForkEvent,
    GollumEvent,
    InstallationEvent,
    InstallationRepositoriesEvent,
    IssueCommentEvent,
    IssuesEvent,
    MarketplacePurchaseEvent,
    MemberEvent,
    MembershipEvent,
    OrgBlockEvent,
    ProjectCardEvent,
    ProjectColumnEvent,
    ProjectEvent,
    PublicEvent,
    PullRequestEvent,
    PullRequestReviewEvent,
    PullRequestReviewCommentEvent,
    PushEvent,
    ReleaseEvent,
    WatchEvent;
}
/*
    Events of this type are not visible in timelines. These events are only used to trigger hooks.
    DeploymentEvent
    DeploymentStatusEvent
    LabelEvent
    MembershipEvent
    MilestoneEvent
    PageBuildEvent
    RepositoryEvent
    StatusEvent
    TeamAddEvent

    Events of this type are not visible in timelines. These events are only used to trigger organization hooks.
    OrganizationEvent
    TeamEvent

    @deprecated
    FollowEvent
    DownloadEvent
    ForkApplyEvent
    GistEvent
 */