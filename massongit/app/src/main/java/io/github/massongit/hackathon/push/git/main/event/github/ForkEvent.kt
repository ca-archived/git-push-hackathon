package io.github.massongit.hackathon.push.git.main.event.github

import android.graphics.Bitmap
import android.net.Uri
import io.github.massongit.hackathon.push.git.main.event.Event
import java.util.*

/**
 * フォークされた際のイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param forkeeRepoName フォーク先のリポジトリ名
 * @param actorHtmlUrl イベントを行ったユーザーのURL
 * @param eventHtmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 * @param forkerRepoName フォーク元のリポジトリ名
 */
class ForkEvent(actorLogin: String, forkeeRepoName: String, actorHtmlUrl: Uri, eventHtmlUrl: Uri, actorAvatar: Bitmap, createdAt: Date, forkerRepoName: String) : Event(actorLogin, forkeeRepoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt) {
    override val messageHTML: String = "<strong>%s</strong> forked <strong>%s</strong> from <strong>%s</strong>".format(this.actorLogin, this.repoName, forkerRepoName)
}
