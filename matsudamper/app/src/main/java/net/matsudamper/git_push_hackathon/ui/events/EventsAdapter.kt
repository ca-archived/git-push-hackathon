package net.matsudamper.git_push_hackathon.ui.events

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.matsudamper.git_push_hackathon.R
import net.matsudamper.git_push_hackathon.databinding.EventsItemBinding

class EventsAdapter(context: Context) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    val items: MutableList<EventItemViewModel> = mutableListOf()

    val displayPosition: MutableLiveData<Int> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(inflater.inflate(R.layout.events_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.vm = items[position]

        displayPosition.value = position
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: EventsItemBinding = DataBindingUtil.bind(itemView)
    }
}

/**
package net.matsudamper.git_push_hackathon.ui.events

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.matsudamper.git_push_hackathon.R
import net.matsudamper.git_push_hackathon.databinding.EventsItemBinding

class EventsAdapter(context: Context) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
enum class ViewType {
BODY, FOOTER
}

private val inflater = LayoutInflater.from(context)
val items: MutableList<EventItemViewModel> = mutableListOf()

val displayPosition: MutableLiveData<Int> = MutableLiveData()

override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
return when (viewType) {
ViewType.BODY.ordinal -> {
BodyViewHolder(inflater.inflate(R.layout.events_item, parent, false))
}
ViewType.FOOTER.ordinal -> {
FooterViewHolder(inflater.inflate(R.layout.events_item, parent, false))
}
else -> throw IllegalStateException()
}
}

override fun getItemCount(): Int = items.size + 1

override fun getItemViewType(position: Int): Int {
return if (items.size > position) ViewType.BODY.ordinal else ViewType.FOOTER.ordinal
}

override fun onBindViewHolder(holder: ViewHolder, position: Int) {
when (holder) {
is BodyViewHolder -> {
holder.binding.vm = items[position].apply {
title = position.toString()
}

displayPosition.value = position
}
is FooterViewHolder -> {

}
}
}

abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

inner class BodyViewHolder(itemView: View) : ViewHolder(itemView) {
val binding: EventsItemBinding = DataBindingUtil.bind(itemView)
}

inner class FooterViewHolder(itemView: View) : ViewHolder(itemView) {
val binding: EventsItemBinding = DataBindingUtil.bind(itemView)
}
}
 */