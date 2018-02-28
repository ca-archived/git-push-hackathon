package masegi.sho.sharehub.presentation.common.adapter

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import masegi.sho.sharehub.R
import masegi.sho.sharehub.data.model.event.Event
import masegi.sho.sharehub.databinding.ItemEventBinding

/**
 * Created by masegi on 2018/02/17.
 */

class EventsAdapter(
        private val avatarTouchUpInside: ((event: Event?) -> Unit)? = null,
        private val itemTouchUpInside: ((event: Event?) -> Unit)? = null
) : PagedListAdapter<Event, EventsAdapter.ViewHolder>(DIFF_CALLBACK) {


    // MARK: - PagedListAdapter

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_event, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.bindEvent(getItem(position))
        holder?.setTouchUpInside(getItem(position), avatarTouchUpInside, itemTouchUpInside)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemEventBinding = DataBindingUtil.bind(itemView)

        fun bindEvent(event: Event?) {

            binding.event = event
        }

        fun setTouchUpInside(
                event: Event?,
                avatarTouchUpInside: ((event: Event?) -> Unit)? = null,
                itemTouchUpInside: ((event: Event?) -> Unit)? = null
        )
        {

            avatarTouchUpInside?.let {

                binding.actorAvatar.setOnClickListener { it(event) }
            }
            itemTouchUpInside?.let {

                binding.rootView.setOnClickListener { it(event) }
            }
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffCallback<Event>() {

            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {

                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {

                return oldItem == newItem
            }
        }
    }
}
