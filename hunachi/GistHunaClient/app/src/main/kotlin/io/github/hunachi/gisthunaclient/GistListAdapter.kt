package io.github.hunachi.gisthunaclient

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.hunachi.gisthunaclient.databinding.FragmentGistBinding
import io.github.hunachi.shared.inflate

class GistListAdapter : PagedListAdapter<String, GistListAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.fragment_gist, false))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding: FragmentGistBinding = FragmentGistBinding.bind(view)
    }

    override fun onBindViewHolder(holder: GistListAdapter.ViewHolder, position: Int) {
        holder.binding.text.text = getItem(position)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
            override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        }
    }
}