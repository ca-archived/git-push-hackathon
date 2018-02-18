package io.github.massongit.hackathon.push.git.main.event.github

import android.graphics.Bitmap
import android.net.Uri
import io.github.massongit.hackathon.push.git.main.event.Event
import java.util.*

/**
 * Watchした際のイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param actorHtmlUrl イベントを行ったユーザーのURL
 * @param eventHtmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 */
class WatchEvent(actorLogin: String, repoName: String, actorHtmlUrl: Uri, eventHtmlUrl: Uri, actorAvatar: Bitmap, createdAt: Date) : Event(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt) {
    override val messageHTML: String = "<strong>%s</strong> starred <strong>%s</strong>".format(this.actorLogin, this.repoName)
}
