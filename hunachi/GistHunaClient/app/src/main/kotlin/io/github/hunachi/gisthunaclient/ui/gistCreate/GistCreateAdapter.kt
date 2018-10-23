package io.github.hunachi.gisthunaclient.ui.gistCreate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.hunachi.gisthunaclient.R
import io.github.hunachi.gisthunaclient.databinding.FragmentFileBinding
import io.github.hunachi.model.File
import io.github.hunachi.shared.inflate

class GistCreateAdapter: ListAdapter<File, GistCreateAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.fragment_file, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {}
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<File>() {
            override fun areItemsTheSame(oldItem: File, newItem: File) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: File, newItem: File) = oldItem == newItem
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding: FragmentFileBinding = FragmentFileBinding.bind(view)
    }
}