package io.github.massongit.hackathon.push.git.main.event.github

import android.graphics.Bitmap
import android.net.Uri
import io.github.massongit.hackathon.push.git.main.event.Event
import java.util.*

/**
 * Pushされた際のイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param actorHtmlUrl イベントを行ったユーザーのURL
 * @param eventHtmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 * @param branch ブランチ
 * @param commitMessage コミットメッセージ
 */
class PushEvent(actorLogin: String, repoName: String, actorHtmlUrl: Uri, eventHtmlUrl: Uri, actorAvatar: Bitmap, createdAt: Date, branch: String, commitMessage: String) : Event(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt) {
    override val messageHTML: String = "<strong>%s</strong> pushed to <u>%s</u> in <strong>%s</strong><br/><u>%s</u>".format(this.actorLogin, branch.replace("refs/heads/", ""), this.repoName, commitMessage)
}
