package io.github.massongit.hackathon.push.git.main.event

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import java.util.*

/**
 * Wikiに関するイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param actorHtmlUrl イベントを行ったユーザーのURL
 * @param eventHtmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 * @param action Wikiに対するアクション
 * @param wikiTitle Wikiのタイトル
 */
class GollumEvent(actorLogin: String, repoName: String, actorHtmlUrl: Uri, eventHtmlUrl: Uri, actorAvatar: BitmapDrawable, createdAt: Date, action: String, wikiTitle: String) : Event(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt) {
    override val messageHTML: String = "<strong>%s</strong> %s a wiki page in <strong>%s</strong><br/><u>%s</u>".format(this.actorLogin, action, this.repoName, wikiTitle)
}
