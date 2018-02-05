package io.github.massongit.hackathon.push.git.main.event

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import java.util.*

/**
 * Pushされた際のイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param htmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 * @param branch ブランチ
 * @param commitMessage コミットメッセージ
 */
class PushEvent(actorLogin: String, repoName: String, htmlUrl: Uri, actorAvatar: BitmapDrawable, createdAt: Date, branch: String, private val commitMessage: String) : Event(actorLogin, repoName, htmlUrl, actorAvatar, createdAt) {
    override val message: String = "%s pushed to %s in %s".format(this.actorLogin, branch.replace("refs/heads/", ""), this.repoName)
}
