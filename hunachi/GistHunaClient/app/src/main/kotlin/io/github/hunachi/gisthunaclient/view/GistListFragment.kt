package io.github.hunachi.gisthunaclient.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import io.github.hunachi.gisthunaclient.databinding.FragmentGistListBinding
import io.github.hunachi.gisthunaclient.flux.GistListActionCreator
import io.github.hunachi.gisthunaclient.flux.GistListStore
import io.github.hunachi.shared.*
import io.github.hunachi.shared.network.NetWorkError
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GistListFragment : Fragment() {

    private val listAdapter = GistListAdapter()
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
            swipeRefresh.setOnRefreshListener {
                refreshList()
            }
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
                listAdapter.submitList(it)
                binding.swipeRefresh.isRefreshing = false
            }

            isLoadingState.observe(this@GistListFragment) {
                binding.swipeRefresh.isRefreshing = it
            }

            errorState.observe(this@GistListFragment) {
                activity?.let { activity ->
                    activity.toast(
                            when {
                                !activity.netWorkCheck() -> "ネット環境の確認をお願いにゃ！"
                                it == NetWorkError.FIN -> "読み込めるものがもうないにゃ！"
                                else -> "えらーにゃーん"
                            })

                }
            }
        }.run {
            onCreate()
        }
    }

    private fun refreshList() {
        preference.token()?.let {
            gistListActionCreator.updateList(preference.ownerName(), it)
        } ?: (activity as? MainActivity)?.tokenIsDuplicatedOrFailed()
    }

    companion object {
        fun newInstance() =
                GistListFragment().apply { arguments = bundleOf() }
    }
}
