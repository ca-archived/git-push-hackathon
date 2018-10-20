package io.github.hunachi.gisthunaclient

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import io.github.hunachi.shared.nonNullObserve
import io.github.hunachi.shared.observe
import io.github.hunachi.shared.savedToken
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GistListFragment : Fragment() {

    private val listAdapter = GistListAdapter()
    private val gistListActionCreator: GistListActionCreator by inject()
    private val preference: SharedPreferences by inject()
    private val gistListStore: GistListStore by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return FragmentGistListBinding.inflate(inflater, container, false).apply {
            list.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gistListStore.apply {
            gistsState.nonNullObserve(this@GistListFragment) {
                listAdapter.submitList(it)
            }

            isLoadingState.observe(this@GistListFragment) {

            }

            errorState.observe(this@GistListFragment) {
                activity?.let { Toast.makeText(it, "にゃーん", Toast.LENGTH_SHORT).show() }
            }
        }.run {
            onCreate()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        preference.savedToken()?.let {
            gistListActionCreator.updateList("Hunachi", it)
        }?: (activity as? MainActivity)?.tokenIsDuplicatedOrFailed()
    }

    companion object {
        fun newInstance() =
                GistListFragment().apply { arguments = bundleOf() }
    }
}
