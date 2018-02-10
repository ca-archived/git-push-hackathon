package io.moatwel.github.presentation.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.moatwel.github.domain.entity.event.Event

class EventAdapter : PagedListAdapter<Event, EventAdapter.ViewHolder>(DIFF_CALLBACK) {

  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

  }

  companion object {
    val DIFF_CALLBACK = object : DiffCallback<Event>() {
      override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
      }
    }
  }
}