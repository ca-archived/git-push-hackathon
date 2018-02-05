package io.github.massongit.hackathon.push.git.main.eventView

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
    val messageLayout: LinearLayout = this.itemView.findViewById(R.id.message_layout)
    val avatar: ImageView = this.itemView.findViewById(R.id.avatar)
    val message: TextView = this.itemView.findViewById(R.id.message)
    val createdAt: TextView = this.itemView.findViewById(R.id.created_at)
}
