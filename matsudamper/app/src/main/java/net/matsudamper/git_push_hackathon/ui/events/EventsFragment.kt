package net.matsudamper.git_push_hackathon.ui.events

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch
import net.matsudamper.git_push_hackathon.MainActivity
import net.matsudamper.git_push_hackathon.R
import net.matsudamper.git_push_hackathon.appdata.AppData
import net.matsudamper.git_push_hackathon.databinding.EventsFragmentBinding
import net.matsudamper.git_push_hackathon.github.GitHubClient
import net.matsudamper.git_push_hackathon.ui.common.BaseAnimationFragment

class EventsFragment : BaseAnimationFragment() {

    private lateinit var binding: EventsFragmentBinding
    private lateinit var appData: AppData

    private var nextUrl: String? = null

    private val client by lazy {
        GitHubClient(resources.getString(R.string.client_id), resources.getString(R.string.client_secret), appData.token)
    }

    private val navigationController by lazy {
        (activity as MainActivity).navigationController
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate<EventsFragmentBinding>(inflater, R.layout.events_fragment, container, false).apply {
            recyclerView.layoutManager = LinearLayoutManager(this@EventsFragment.context)

            vm = ViewModelProviders.of(this@EventsFragment).get<EventsViewModel>(EventsViewModel::class.java).apply {
                adapter.displayPosition.observe(this@EventsFragment, Observer { position ->
                    position ?: return@Observer

                    // 先読み更新
                    if (position > adapter.items.count() - 5) {
                        val nextUrl = this@EventsFragment.nextUrl
                        this@EventsFragment.nextUrl = null

                        if (nextUrl != null) {
                            launch(UI) {
                                getEventActor.send(nextUrl)
                            }
                        }
                    }
                })

                swipeRefreshLayout.setOnRefreshListener {
                    swipeRefreshLayout.isRefreshing = false

                    launch(UI) {
                        getEventActor.send(null)
                    }
                }
            }

        }

        context?.let {
            appData = AppData(it)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (binding.vm?.adapter?.items?.count() == 0) {
            nextUrl = null
            launch(UI) {
                getEventActor.send(null)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.events, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                appData.token = ""
                navigationController.navigateToLogin()
            }
            R.id.license -> {
                navigationController.navigationToLicense()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private val getEventActor = actor<String?>(UI) {

        for (url in channel) {
            val results = if (url == null) {
                client.getReceivedEvents(appData.name, 0).await()
            } else {
                client.getReceivedEvents(url).await()
            }

            // Error
            if (results == null) {
                this@EventsFragment.nextUrl = url
                continue
            }


            val response = results.first.map { EventItemViewModel(it) }
            val nextUrl = results.second

            if (this@EventsFragment.nextUrl == null) {
                this@EventsFragment.nextUrl = nextUrl
            }


            // View
            val adapter = binding.vm?.adapter ?: continue

            val oldList = adapter.items.toMutableList()
            val newList = adapter.items.also {
                if (url == null) {

                    if (oldList.size > 0) {
                        val topItem = oldList[0]
                        val index = response.indexOfFirst { it.item.id == topItem.item.id }

                        // リストが繋がるか
                        if (index == -1) {
                            it.removeAll { true }
                            it.addAll(0, response)

                            this@EventsFragment.nextUrl = nextUrl
                        } else {
                            for (i in index - 1 downTo 0) {
                                it.add(0, response[i])
                            }

                            // Topなら
                            if (binding.recyclerView.getChildAt(0).top == 0) {
                                val manager = binding.recyclerView.layoutManager as LinearLayoutManager
                                manager.scrollToPositionWithOffset(index, 100)
                            }
                        }

                    } else {
                        // 初回
                        it.addAll(0, response)
                    }

                } else {
                    // 末尾追加
                    it.addAll(adapter.items.size, response)
                }
            }

            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize() = oldList.size
                override fun getNewListSize() = newList.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                        oldList[oldItemPosition] == newList[newItemPosition]


                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                        oldList[oldItemPosition].item.id == newList[newItemPosition].item.id
            })
            diffResult.dispatchUpdatesTo(adapter)
        }
    }
}
