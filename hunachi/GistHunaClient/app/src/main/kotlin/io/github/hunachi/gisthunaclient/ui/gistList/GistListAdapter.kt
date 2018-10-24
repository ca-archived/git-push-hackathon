package io.github.hunachi.gisthunaclient.ui.gistList

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.hunachi.gisthunaclient.R
import io.github.hunachi.gisthunaclient.databinding.FragmentGistBinding
import io.github.hunachi.model.Gist
import io.github.hunachi.shared.inflate

class GistListAdapter(
        private val listener: GistListListener
) : PagedListAdapter<Gist, GistListAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.fragment_gist, false))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding: FragmentGistBinding = FragmentGistBinding.bind(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            text.text = getItem(position)?.description ?: "猫だよ"
        }
    }

    interface GistListListener{
        fun onClickItem(): (String) -> Unit
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Gist>() {
            override fun areItemsTheSame(oldItem: Gist, newItem: Gist) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Gist, newItem: Gist) = oldItem == newItem
        }
    }
}