package masegi.sho.sharehub.presentation.event


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

import masegi.sho.sharehub.data.model.NetworkState
import masegi.sho.sharehub.data.model.event.Event
import masegi.sho.sharehub.databinding.FragmentMainBinding
import masegi.sho.sharehub.presentation.NavigationController
import masegi.sho.sharehub.presentation.common.adapter.EventsAdapter
import masegi.sho.sharehub.util.ext.observe
import javax.inject.Inject

class MainFragment : DaggerFragment() {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private lateinit var adapter: EventsAdapter

    private val avatarTouchUpInside: (event: Event?) -> Unit = { event ->

        event?.actor?.let {

            mainViewModel.getUserInfo(it.login)
        }
    }


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentMainBinding.inflate(inflater, container!!, false)
        adapter = EventsAdapter(
                avatarTouchUpInside,
                EventItemClickEventFactory(navigationController).build()
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setup()
        mainViewModel.events.observe(this) {

            adapter.setList(it)
        }
    }


    // MARK: - Private

    private fun setup() {

        setupLayout()
        setupSwipeToRefresh()
        setupScrollToTop()
        mainViewModel.userHtmlUrl.observe(this) {

            if (it != null) {

                navigationController.navigationToExternalBrowser(it)
            }
        }
    }

    private fun setupLayout() {

        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.recycler.adapter = adapter
    }

    private fun setupSwipeToRefresh() {

        mainViewModel.initialLoad.observe(this) {

            binding.swipeRefresh.isRefreshing = it == NetworkState.RUNNING
        }
        binding.swipeRefresh.setOnRefreshListener {

            mainViewModel.refresh()
        }
    }

    private fun setupScrollToTop() {

        val title = (activity as MainActivity).title
         title?.let {

             it.setOnClickListener {

                 binding.recycler.smoothScrollToPosition(0)
             }
        }
    }


    companion object {

        fun newInstance(): MainFragment = MainFragment()
    }
}// Required empty public constructor
