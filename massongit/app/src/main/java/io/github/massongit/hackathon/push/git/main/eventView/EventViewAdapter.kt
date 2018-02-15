package io.github.massongit.hackathon.push.git.main.eventView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.helper.ChromeCustomTabsHelper
import io.github.massongit.hackathon.push.git.main.event.Event
import java.text.SimpleDateFormat
import java.util.*

/**
 * イベントビューのアダプター
 * @param context Activity
 * @param chromeCustomTabsHelper Chrome Custom Tabs Helper
 */
class EventViewAdapter(private val context: Context, private val chromeCustomTabsHelper: ChromeCustomTabsHelper) : RecyclerView.Adapter<EventViewHolder>() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = EventViewAdapter::class.simpleName
    }

    /**
     * イベントリスト
     */
    private var items: List<Event> = emptyList()

    /**
     * 表示するイベントの種類
     */
    private val eventKinds: MutableSet<String> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        Log.v(EventViewAdapter.TAG, "onCreateViewHolder called")
        return EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event, parent, false))
    }

    override fun getItemCount(): Int = this.items.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        Log.v(EventViewAdapter.TAG, "onBindViewHolder called")
        holder.apply {
            val item = items[position]
            if (eventKinds.contains(item::class.simpleName)) {
                messageLayout.setOnClickListener {
                    chromeCustomTabsHelper.launch(item.eventHtmlUrl)
                }
                avatar.apply {
                    setOnClickListener {
                        chromeCustomTabsHelper.launch(item.actorHtmlUrl)
                    }
                    setImageBitmap(item.actorAvatar)
                }
                message.setText(Html.fromHtml(item.messageHTML, Html.FROM_HTML_MODE_COMPACT), TextView.BufferType.SPANNABLE)
                createdAt.text = SimpleDateFormat("yyyy/MM/dd (E) HH:mm:ss", Locale.getDefault()).format(item.createdAt)
            }
        }
    }

    /**
     * イベントリストを追加する
     * @param items イベントリスト
     * @param isTop 先頭に追加するかどうか
     */
    fun addItems(items: List<Event>, isTop: Boolean) {
        Log.v(EventViewAdapter.TAG, "addItems called")
        this.items = if (isTop) {
            items + this.items
        } else {
            this.items + items
        }
        this.notifyDataSetChanged()
    }

    /**
     * 表示するイベントの種類を追加する
     * @param eventKind 表示するイベントの種類
     */
    fun addEventKind(eventKind: String) {
        Log.v(EventViewAdapter.TAG, "addEventKind called")
        this.eventKinds.add(eventKind)
        if (0 < this.itemCount) {
            this.notifyDataSetChanged()
        }
    }

    /**
     * 表示するイベントの種類を削除する
     * @param eventKind 表示するイベントの種類
     */
    fun removeEventKind(eventKind: String) {
        Log.v(EventViewAdapter.TAG, "removeEventKind called")
        this.eventKinds.remove(eventKind)
        if (0 < this.itemCount) {
            this.notifyDataSetChanged()
        }
    }
}