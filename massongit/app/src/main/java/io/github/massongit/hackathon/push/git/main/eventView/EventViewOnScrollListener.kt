package io.github.massongit.hackathon.push.git.main.eventView

import android.support.v7.widget.RecyclerView
import android.util.Log
import io.github.massongit.hackathon.push.git.main.helper.MainHelper

/**
 * イベントビューのスクロールイベント
 * @param helper Helper
 */
class EventViewOnScrollListener(private val helper: MainHelper) : RecyclerView.OnScrollListener() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = EventViewOnScrollListener::class.simpleName
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        Log.v(EventViewOnScrollListener.TAG, "onScrolled called")
        super.onScrolled(recyclerView, dx, dy)
        if (!recyclerView.canScrollVertically(1)) {
            this.helper.getTimeLine(false)
        }
    }
}