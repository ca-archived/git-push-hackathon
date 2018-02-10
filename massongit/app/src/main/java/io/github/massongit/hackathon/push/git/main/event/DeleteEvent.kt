package io.github.massongit.hackathon.push.git.main.event

import android.graphics.Bitmap
import android.net.Uri
import java.util.*

/**
 * リポジトリ, ブランチ, タグのいずれかが削除された際のイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param actorHtmlUrl イベントを行ったユーザーのURL
 * @param eventHtmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 * @param thingType イベントの対象オブジェクトの種類
 * @param thing イベントの対象オブジェクト
 */
class DeleteEvent(actorLogin: String, repoName: String, actorHtmlUrl: Uri, eventHtmlUrl: Uri, actorAvatar: Bitmap, createdAt: Date, thingType: String, thing: String) : Event(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt) {
    override val messageHTML: String = "<strong>%s</strong> deleted %s <u>%s</u> at <strong>%s</strong>".format(this.actorLogin, this.getWordWithIndefiniteArticle(thingType), thing, this.repoName)
}
