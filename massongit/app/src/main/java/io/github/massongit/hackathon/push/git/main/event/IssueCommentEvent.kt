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
 * @param comment コメント
 */
class IssueCommentEvent(actorLogin: String, repoName: String, actorHtmlUrl: Uri, eventHtmlUrl: Uri, actorAvatar: Bitmap, createdAt: Date, number: Int, comment: String) : Event(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt) {
    override val messageHTML: String = "<strong>%s</strong> commented on an issue <strong>%s#%d</strong><br/><small><i>%s</i></small>".format(this.actorLogin, this.repoName, number, comment)
}
