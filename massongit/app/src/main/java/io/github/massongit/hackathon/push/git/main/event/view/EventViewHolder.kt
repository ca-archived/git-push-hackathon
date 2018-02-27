package io.github.massongit.hackathon.push.git.main.event.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import io.github.massongit.hackathon.push.git.R

/**
 * イベントビューのホルダー
 * @param itemView View
 */
class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * メッセージ部のレイアウト
     */
    val messageLayout: LinearLayout = this.itemView.findViewById(R.id.event_message_layout)

    /**
     * サムネイル部
     */
    val actorAvatar: ImageView = this.itemView.findViewById(R.id.event_actor_avatar)

    /**
     * メッセージ部
     */
    val message: TextView = this.itemView.findViewById(R.id.event_message)

    /**
     * イベントの生成時刻表示部
     */
    val createdAt: TextView = this.itemView.findViewById(R.id.event_created_at)
}
