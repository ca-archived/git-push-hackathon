package io.github.massongit.hackathon.push.git.main.eventKinds

import android.util.Log
import android.widget.CompoundButton
import io.github.massongit.hackathon.push.git.main.eventView.EventViewAdapter
import io.github.massongit.hackathon.push.git.main.helper.MainHelper

/**
 * イベントの種類が追加/削除されたときのイベント
 * @param eventViewAdapter イベントビューのアダプター
 * @param eventKind イベントの種類
 */
class EventKindsOnCheckedChangeListener(private val eventViewAdapter: EventViewAdapter, private val eventKind: String) : CompoundButton.OnCheckedChangeListener {
    companion object {
        /**
         * ログ用タグ
         */
        private val TAG: String? = MainHelper::class.simpleName
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        Log.v(EventKindsOnCheckedChangeListener.TAG, "onCheckedChanged called")
        if (isChecked) {
            this.eventViewAdapter.addEventKind(this.eventKind)
        } else {
            this.eventViewAdapter.removeEventKind(this.eventKind)
        }
    }
}
