package io.github.massongit.hackathon.push.git.main.event

import android.graphics.Bitmap
import android.net.Uri
import java.util.*

/**
 * GitHub APIのイベント
 * @param actorLogin イベントを行ったユーザーのID
 * @param repoName リポジトリ名
 * @param actorHtmlUrl イベントを行ったユーザーのURL
 * @param eventHtmlUrl イベントのURL
 * @param actorAvatar イベントを行ったユーザーのサムネイル
 * @param createdAt イベントが作成された日時
 */
abstract class Event(protected val actorLogin: String, protected val repoName: String, val actorHtmlUrl: Uri, val eventHtmlUrl: Uri, val actorAvatar: Bitmap, val createdAt: Date) {
    /**
     * イベントの通知メッセージ (HTML)
     */
    abstract val messageHTML: String

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
