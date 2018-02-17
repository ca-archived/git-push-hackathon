/*
 *  GitHub-Client
 *
 *  EventAdapter.kt
 *
 *  Copyright 2018 moatwel.io
 *  author : halu5071 (Yasunori Horii)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.moatwel.github.presentation.view.adapter

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.moatwel.github.R
import io.moatwel.github.databinding.ItemEventBinding
import io.moatwel.github.domain.entity.event.Event

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