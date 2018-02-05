package io.github.massongit.hackathon.push.git.main.event

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import java.util.*

/**
 * GitHub APIのイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param htmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 */
abstract class Event(protected val actorLogin: String, protected val repoName: String, val htmlUrl: Uri, val actorAvatar: BitmapDrawable, val createdAt: Date) {
    /**
     * イベントの通知メッセージ
     */
    abstract val messageHTML: String
}
