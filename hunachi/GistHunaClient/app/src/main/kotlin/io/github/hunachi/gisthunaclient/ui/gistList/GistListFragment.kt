package io.github.hunachi.gisthunaclient.ui.gistList

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import io.github.hunachi.gisthunaclient.databinding.FragmentGistListBinding
import io.github.hunachi.gisthunaclient.flux.FragmentState
import io.github.hunachi.gisthunaclient.flux.actionCreator.GistListActionCreator
import io.github.hunachi.gisthunaclient.flux.store.GistListStore
import io.github.hunachi.gisthunaclient.ui.MainActivity
import io.github.hunachi.shared.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GistListFragment : Fragment(), GistListAdapter.GistListListener {

    private val listAdapter = GistListAdapter(this)
    private val gistListActionCreator: GistListActionCreator by inject()
    private val preference: SharedPreferences by inject()
    private val gistListStore: GistListStore by viewModel()
    private lateinit var binding: FragmentGistListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return FragmentGistListBinding.inflate(inflater, container, false).apply {
            list.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(context)
            }
            swipeRefresh.setOnRefreshListener { refreshList() }
            binding = this
        }.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setupStore()
        refreshList()
    }

    private fun setupStore() {
        gistListStore.apply {
            gistsState.nonNullObserve(this@GistListFragment) {
                binding.emptyList = if (it.size <= 0) true else false
                listAdapter.submitList(it)
                binding.swipeRefresh.isRefreshing = false
            }

            isLoadingState.nonNullObserve(this@GistListFragment) {
                binding.swipeRefresh.isRefreshing = it
            }

            errorState.nonNullObserve(this@GistListFragment) {
                activity?.let { activity -> activity.toastNetworkError(it) }
            }

            startCreateGistState.observe(this@GistListFragment) {
                (activity as? MainActivity)?.let { it.replaceFragment(FragmentState.CREATE_GIST) }
            }

            finishState.observe(this@GistListFragment) {
                (activity as? MainActivity)?.finish()
            }
        }.run {
            onCreate()
        }
    }

    private fun refreshList() {
        if (preference.login() && preference.token() == null) (activity as? MainActivity)?.tokenIsDuplicatedOrFailed()
        else gistListActionCreator.updateList(preference.ownerName(), preference.token())
    }

    override fun onClickItem(): (String) -> Unit = {
        // 詳細画面にいく．
    }

    companion object {
        fun newInstance() =
                GistListFragment().apply { arguments = bundleOf() }
    }
}
