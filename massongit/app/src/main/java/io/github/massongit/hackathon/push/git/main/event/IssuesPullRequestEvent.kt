package io.github.massongit.hackathon.push.git.main.event

import android.graphics.Bitmap
import android.net.Uri
import java.util.*

/**
 * IssueやPull Request関連のイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param actorHtmlUrl イベントを行ったユーザーのURL
 * @param eventHtmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 * @param action Pull Requestに対するアクション
 * @param number Pull Requestの番号
 * @param title Pull Requestのタイトル
 * @param kind 種類 (issue or pull request)
 */
open class IssuesPullRequestEvent(actorLogin: String, repoName: String, actorHtmlUrl: Uri, eventHtmlUrl: Uri, actorAvatar: Bitmap, createdAt: Date, action: String, number: Int, title: String, kind: String) : Event(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt) {
    override val messageHTML: String = "<strong>%s</strong> %s %s <strong>%s#%d</strong><br/><u>%s</u>".format(this.actorLogin, action, this.getWordWithIndefiniteArticle(kind), this.repoName, number, title)
}
