package com.dev.touyou.gitme.Data

import java.net.URL
import java.util.*

/**
 * Created by touyou on 2018/02/25.
 */

data class Payload(
        val action: String?,
        val comment: Comment?,
        val issue: Issue?,
        val before: String?,
        val commits: Array<Commit>?,
        val distinctSize: Int?,
        val head: String?,
        val pushId: Int?,
        val ref: String?,
        val refType: String?,
        val size: Int?,
        val description: String?,
        val masterBranch: String?,
        val pusherType: String?,
        val forkee: Forkee?
)

data class Comment(
        val authorAssociation: String,
        val body: String,
        val createdAt: Date,
        val htmlUrl: URL,
        val id: Int,
        val issueUrl: URL,
        val updatedAt: Date,
        val url: URL,
        val user: User
)

data class Issue(
        val assignee: String?,
        val assignees: Array<String>,
        val authorAssociation: String?,
        val body: String,
        val closedAt: Date?,
        val commentsCount: Int,
        val commentsUrl: URL,
        val createdAt: Date,
        val eventsUrl: URL,
        val htmlUrl: URL,
        val id: Int,
        val labels: Array<Label>,
        val labelsUrl: String,
        val lockedCount: Int,
        val milestone: String?,
        val number: Int,
        val repositoryUrl: URL,
        val state: String,
        val title: String,
        val updatedAt: Date,
        val url: String,
        val user: User
)

data class Author(
        val email: String,
        val name: String
)

data class Commit(
        val author: Author,
        val distinct: Int,
        val message: String,
        val sha: String,
        val url: URL
)

data class Label(
        val color: String,
        val default: Int,
        val id: Int,
        val name: String,
        val url: URL
)

data class Forkee()