package io.github.massongit.hackathon.push.git.main.event.view

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.massongit.hackathon.push.git.R
import io.github.massongit.hackathon.push.git.chromeCustomTabs.ChromeCustomTabsHelper
import io.github.massongit.hackathon.push.git.main.event.Event
import java.text.SimpleDateFormat
import java.util.*

/**
 * イベントビューのアダプター
 * @param chromeCustomTabsHelper Chrome Custom Tabs Helper
 */
class EventViewAdapter(private val chromeCustomTabsHelper: ChromeCustomTabsHelper) : RecyclerView.Adapter<EventViewHolder>() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = EventViewAdapter::class.simpleName
    }

    /**
     * 全てのイベントが載っているイベントリスト
     */
    private var allEventList: List<Event> = emptyList()

    /**
     * フィルター済みのイベントリスト
     */
    private var filteredEventList: List<Event> = emptyList()

    /**
     * 表示するイベントの種類
     */
    private val eventKinds: MutableSet<String> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        Log.v(EventViewAdapter.TAG, "onCreateViewHolder called")
        return EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event, parent, false))
    }

    override fun getItemCount(): Int = this.filteredEventList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        Log.v(EventViewAdapter.TAG, "onBindViewHolder called")
        holder.apply {
            val item = filteredEventList[position]
            messageLayout.setOnClickListener {
                chromeCustomTabsHelper.launch(item.eventHtmlUrl)
            }
            actorAvatar.apply {
                setOnClickListener {
                    chromeCustomTabsHelper.launch(item.actorHtmlUrl)
                }
                setImageBitmap(item.actorAvatar)
            }
            message.text = Html.fromHtml(item.messageHTML, Html.FROM_HTML_MODE_COMPACT)
            createdAt.text = SimpleDateFormat("yyyy/MM/dd (E) HH:mm:ss", Locale.getDefault()).format(item.createdAt)
        }
    }

    /**
     * イベントリストを追加する
     * @param items イベントリスト
     * @param isTop 先頭に追加するかどうか
     */
    fun addItems(items: List<Event>, isTop: Boolean) {
        Log.v(EventViewAdapter.TAG, "addItems called")
        this.allEventList = if (isTop) {
            items + this.allEventList
        } else {
            this.allEventList + items
        }
        this.onDataChanged()
    }

    /**
     * 表示するイベントの種類を追加する
     * @param eventKind 表示するイベントの種類
     */
    fun addEventKind(eventKind: String) {
        Log.v(EventViewAdapter.TAG, "addEventKind called")
        this.eventKinds.add(eventKind)
        this.onDataChanged()
    }

    /**
     * 表示するイベントの種類を削除する
     * @param eventKind 表示するイベントの種類
     */
    fun removeEventKind(eventKind: String) {
        Log.v(EventViewAdapter.TAG, "removeEventKind called")
        this.eventKinds.remove(eventKind)
        this.onDataChanged()
    }

    /**
     * データが変化した際のイベント
     */
    private fun onDataChanged() {
        Log.v(EventViewAdapter.TAG, "onDataChanged called")
        if (this.allEventList.isNotEmpty()) {
            this.filteredEventList = this.allEventList.filter {
                this.eventKinds.contains(it::class.simpleName)
            }
            this.notifyDataSetChanged()
        }
    }
}