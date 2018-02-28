package net.matsudamper.git_push_hackathon.ui.license

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.matsudamper.git_push_hackathon.R
import net.matsudamper.git_push_hackathon.databinding.LicenseItemBinding

class LicenseAdapter(context: Context) : RecyclerView.Adapter<LicenseAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    val items: MutableList<LicenseItemViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(inflater.inflate(R.layout.license_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.vm = items[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: LicenseItemBinding = DataBindingUtil.bind(itemView)
    }
}