package io.github.hunachi.gisthunaclient.ui.gistCreate

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.hunachi.gisthunaclient.R
import io.github.hunachi.gisthunaclient.databinding.FragmentGistCreateBinding
import io.github.hunachi.gisthunaclient.flux.FragmentState
import io.github.hunachi.gisthunaclient.flux.actionCreator.CreateGistActionCreator
import io.github.hunachi.gisthunaclient.flux.store.CreateGistStore
import io.github.hunachi.gisthunaclient.ui.MainActivity
import io.github.hunachi.model.File
import io.github.hunachi.shared.nonNullObserve
import io.github.hunachi.shared.observe
import io.github.hunachi.shared.toast
import io.github.hunachi.shared.token
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateGistFragment : Fragment(), FilesAdapter.FilesListener {

    private val filesAdapter = FilesAdapter(this)
    private val preference: SharedPreferences by inject()
    private val createActionCreator: CreateGistActionCreator by inject()
    private val createStore: CreateGistStore by viewModel()
    private lateinit var binding: FragmentGistCreateBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return FragmentGistCreateBinding.inflate(inflater, container, false).apply {
            list.apply {
                adapter = filesAdapter
                layoutManager = LinearLayoutManager(context)
            }
            binding = this
        }.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setupStore()
        // 最初から一個表示しとく．
        filesAdapter.submitList(listOf(File()))
    }

    private fun setupStore() = createStore.apply {
        requestSaveState.observe(this@CreateGistFragment, Observer {
            if (filesAdapter.allNotEmptyFile().isEmpty()) {
                activity?.toast(getString(R.string.cant_upload_file_is_empty))
                return@Observer
            }
            preference.token()?.let { token ->
                createActionCreator.uploadGist(
                        binding.descriptionText.text.toString(),
                        !binding.publicSwitch.isChecked,
                        filesAdapter.allNotEmptyFile(), token)
            } ?: (activity as? MainActivity)?.tokenIsDuplicatedOrFailed()
        })

        loadingState.nonNullObserve(this@CreateGistFragment) {
            binding.loadingDialog.run {
                if (it) show() else hide()
            }
        }

        postErrorState.nonNullObserve(this@CreateGistFragment) {
            (activity)?.toast("errorにゃ！")
        }

        successPostGist.nonNullObserve(this@CreateGistFragment) {
            (activity)?.toast(getString(R.string.success_post_gist))
            createActionCreator.successPostGist(it)
            (activity as? MainActivity)?.replaceFragment(FragmentState.GIST_LIST)
        }

        finishState.observe(this@CreateGistFragment) {
            (activity as? MainActivity)?.replaceFragment(FragmentState.GIST_LIST)
        }
    }.run {
        onCreate()
    }

    override fun addFile() {
        val list = filesAdapter.allFiles().toMutableList()
        list.add(File(id = list.size))
        filesAdapter.submitList(list.toList())
    }

    companion object {
        fun newInstance() =
                CreateGistFragment().apply { arguments = bundleOf() }
    }
}