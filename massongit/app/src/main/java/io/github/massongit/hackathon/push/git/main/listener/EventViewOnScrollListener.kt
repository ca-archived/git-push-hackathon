package io.github.massongit.hackathon.push.git.main.listener

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.github.massongit.hackathon.push.git.main.helper.MainHelper

/**
 * イベントビューのスクロールイベント
 * @param helper Helper
 * @param onRefreshListener SwipeRefreshLayoutの更新イベント
 */
class EventViewOnScrollListener(private val helper: MainHelper, private val onRefreshListener: SwipeRefreshLayout.OnRefreshListener) : RecyclerView.OnScrollListener() {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = MainHelper::class.simpleName
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        Log.v(TAG, "onScrolled called")
        super.onScrolled(recyclerView, dx, dy)
        if (!recyclerView.canScrollVertically(-1)) {
            this.onRefreshListener.onRefresh()
        } else if (!recyclerView.canScrollVertically(1)) {
            this.helper.getTimeLine(false)
        }
    }
}