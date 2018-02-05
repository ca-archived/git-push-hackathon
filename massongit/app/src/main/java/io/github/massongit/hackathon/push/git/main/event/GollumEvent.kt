package io.github.massongit.hackathon.push.git.main.event

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import java.util.*

/**
 * Wikiに関するイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param htmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 * @param action Wikiに対するアクション
 * @param wikiTitle Wikiのタイトル
 */
class GollumEvent(actorLogin: String, repoName: String, htmlUrl: Uri, actorAvatar: BitmapDrawable, createdAt: Date, action: String, private val wikiTitle: String) : Event(actorLogin, repoName, htmlUrl, actorAvatar, createdAt) {
    override val message: String = "%s %s a wiki page in %s".format(this.actorLogin, action, this.repoName)
}
