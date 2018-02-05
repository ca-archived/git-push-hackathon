package io.github.massongit.hackathon.push.git.main.event

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import java.util.*

/**
 * リリースされた際のイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param htmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 * @param version リリースのバージョン
 */
class ReleaseEvent(actorLogin: String, repoName: String, htmlUrl: Uri, actorAvatar: BitmapDrawable, createdAt: Date, version: String) : Event(actorLogin, repoName, htmlUrl, actorAvatar, createdAt) {
    override val messageHTML: String = "<strong>%s</strong> released <u>%s</u> at <strong>%s</strong>".format(this.actorLogin, version, this.repoName)
}
