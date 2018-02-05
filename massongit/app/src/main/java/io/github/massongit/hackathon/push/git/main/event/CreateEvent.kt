package io.github.massongit.hackathon.push.git.main.event

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import java.util.*

/**
 * リポジトリ, ブランチ, タグのいずれかが作成された際のイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param actorHtmlUrl イベントを行ったユーザーのURL
 * @param eventHtmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 * @param thing イベントの対象オブジェクト
 */
class CreateEvent(actorLogin: String, repoName: String, actorHtmlUrl: Uri, eventHtmlUrl: Uri, actorAvatar: BitmapDrawable, createdAt: Date, thing: String) : Event(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt) {
    override val messageHTML: String = "<strong>%s</strong> created %s %s <strong>%s</strong>".format(this.actorLogin, if (thing.startsWith("[aiueo]")) {
        "an"
    } else {
        "a"
    }, thing, this.repoName)
}
