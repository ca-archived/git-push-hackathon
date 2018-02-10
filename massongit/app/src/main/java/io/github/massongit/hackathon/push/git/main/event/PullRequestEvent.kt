package io.github.massongit.hackathon.push.git.main.event

import android.graphics.Bitmap
import android.net.Uri
import java.util.*

/**
 * Pull Request関連のイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param actorHtmlUrl イベントを行ったユーザーのURL
 * @param eventHtmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 * @param action Pull Requestに対するアクション
 * @param number Pull Requestの番号
 * @param title Pull Requestのタイトル
 */
class PullRequestEvent(actorLogin: String, repoName: String, actorHtmlUrl: Uri, eventHtmlUrl: Uri, actorAvatar: Bitmap, createdAt: Date, action: String, number: Int, title: String) : IssuesPullRequestEvent(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt, action, number, title, "pull request")
