package io.moatwel.github.presentation.view.adapter

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.moatwel.github.R
import io.moatwel.github.databinding.ItemEventBinding
import io.moatwel.github.domain.entity.event.Event
import io.moatwel.github.presentation.view.GlideApp

class EventAdapter : PagedListAdapter<Event, EventAdapter.ViewHolder>(DIFF_CALLBACK) {

  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    holder?.bindTo(getItem(position))
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
    return ViewHolder(LayoutInflater.from(parent?.context)
      .inflate(R.layout.item_event, parent, false))
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding: ItemEventBinding? = DataBindingUtil.bind(itemView)

    fun bindTo(event: Event?) {
      binding?.event = event
      GlideApp.with(itemView.context)
        .load(event?.actor?.avatarUrl)
        .circleCrop()
        .into(binding?.imageUserLogin!!)
    }
  }

  companion object {
    val DIFF_CALLBACK = object : DiffCallback<Event>() {
      override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
      }

      override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }
}