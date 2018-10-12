package io.github.hunachi.gisthunaclient

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import io.github.hunachi.gisthunaclient.databinding.FragmentGistListBinding

class GistListFragment : Fragment() {

    private val listAdapter = GistListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return FragmentGistListBinding.inflate(inflater, container, false).apply {
            list.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    companion object {
        fun newInstance(columnCount: Int) =
                GistListFragment().apply { arguments = bundleOf() }
    }
}
