package io.github.massongit.hackathon.push.git.main.event

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import java.util.*

/**
 * Watchした際のイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param htmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 */
class WatchEvent(actorLogin: String, repoName: String, htmlUrl: Uri, actorAvatar: BitmapDrawable, createdAt: Date) : Event(actorLogin, repoName, htmlUrl, actorAvatar, createdAt) {
    override val message: String = "%s starred %s".format(this.actorLogin, this.repoName)
}
