package io.github.massongit.hackathon.push.git.main.event

import android.graphics.Bitmap
import android.net.Uri
import java.util.*

/**
 * Issueにコメントされた際のイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param actorHtmlUrl イベントを行ったユーザーのURL
 * @param eventHtmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 * @param number Issueの番号
 * @param title Issueのタイトル
 * @param comment コメント
 * @param isPullRequest Pull Requestかどうか
 */
class IssueCommentEvent(actorLogin: String, repoName: String, actorHtmlUrl: Uri, eventHtmlUrl: Uri, actorAvatar: Bitmap, createdAt: Date, number: Int, title: String, comment: String, isPullRequest: Boolean) : Event(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt) {
    override val messageHTML: String = "<strong>%s</strong> commented on %s <strong>%s#%d</strong><br/><u>%s</u><br/><i>%s</i>".format(this.actorLogin, if (isPullRequest) {
        "a pull request"
    } else {
        "an issue"
    }, this.repoName, number, title, comment)
}
