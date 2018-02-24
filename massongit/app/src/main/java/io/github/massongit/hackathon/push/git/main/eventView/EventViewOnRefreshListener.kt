package io.github.massongit.hackathon.push.git.main.eventView

import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import io.github.massongit.hackathon.push.git.main.helper.MainHelper

/**
 * イベントビューの更新イベント
 * @param helper Helper
 */
class EventViewOnRefreshListener(private val helper: MainHelper) : SwipeRefreshLayout.OnRefreshListener {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = EventViewOnRefreshListener::class.simpleName
    }

    override fun onRefresh() {
        Log.v(EventViewOnRefreshListener.TAG, "onRefresh called")
        this.helper.getTimeLine()
    }
}