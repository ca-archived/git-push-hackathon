package net.matsudamper.git_push_hackathon.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView


@BindingAdapter("bind:adapter")
fun <T : RecyclerView.ViewHolder> RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<T>) {
    this.adapter = adapter
}