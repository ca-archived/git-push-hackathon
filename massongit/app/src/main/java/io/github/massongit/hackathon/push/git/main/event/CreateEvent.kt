package io.github.massongit.hackathon.push.git.main.event

import android.graphics.Bitmap
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
 * @param thingType イベントの対象オブジェクトの種類
 */
open class CreateEvent(actorLogin: String, repoName: String, actorHtmlUrl: Uri, eventHtmlUrl: Uri, actorAvatar: Bitmap, createdAt: Date, protected val thingType: String) : Event(actorLogin, repoName, actorHtmlUrl, eventHtmlUrl, actorAvatar, createdAt) {
    override val messageHTML: String = "<strong>%s</strong> created %s <strong>%s</strong>".format(this.actorLogin, this.getWordWithIndefiniteArticle(this.thingType), this.repoName)

    /**
     * 不定冠詞付きの単語を生成する
     * @param word 単語
     * @return 不定冠詞付きの単語
     */
    protected fun getWordWithIndefiniteArticle(word: String): String {
        return if (word.startsWith("[aiueo]")) {
            "an"
        } else {
            "a"
        } + " " + word
    }
}
