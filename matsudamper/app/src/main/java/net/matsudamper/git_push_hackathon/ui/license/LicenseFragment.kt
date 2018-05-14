package net.matsudamper.git_push_hackathon.ui.license

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.net.toUri
import net.matsudamper.git_push_hackathon.R
import net.matsudamper.git_push_hackathon.databinding.LicenseFragmentBinding
import net.matsudamper.git_push_hackathon.ui.common.BaseAnimationFragment

class LicenseFragment : BaseAnimationFragment() {

    private lateinit var binding: LicenseFragmentBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate<LicenseFragmentBinding>(inflater, R.layout.license_fragment, container, false).apply {
            vm = ViewModelProviders.of(this@LicenseFragment).get<LicenseViewModel>(LicenseViewModel::class.java)
            recyclerView.layoutManager = LinearLayoutManager(this@LicenseFragment.context)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.vm?.adapter?.items?.apply {
            add(LicenseItemViewModel("kotlin", "https://github.com/JetBrains/kotlin/blob/master/license/LICENSE.txt"))
            add(LicenseItemViewModel("android-ktx", "https://github.com/android/android-ktx/blob/master/LICENSE.txt"))
            add(LicenseItemViewModel("kotlinx.coroutines", "https://github.com/Kotlin/kotlinx.coroutines/blob/master/license/LICENSE.txt"))

            add(LicenseItemViewModel("octicons", "https://github.com/primer/octicons/blob/master/LICENSE"))
            add(LicenseItemViewModel("moshi", "https://github.com/square/moshi/blob/master/LICENSE.txt"))
            add(LicenseItemViewModel("Picasso", "http://square.github.io/picasso/"))

            add(LicenseItemViewModel("recyclerview-v7", "https://android.googlesource.com/platform/frameworks/support.git/+/master/LICENSE.txt"))
            add(LicenseItemViewModel("support-v4", "https://android.googlesource.com/platform/frameworks/support.git/+/master/LICENSE.txt"))
            add(LicenseItemViewModel("appcompat-v7", "https://android.googlesource.com/platform/frameworks/support.git/+/master/LICENSE.txt"))
            add(LicenseItemViewModel("customtabs", "https://android.googlesource.com/platform/frameworks/support.git/+/master/LICENSE.txt"))
            add(LicenseItemViewModel("constraint-layout", "https://android.googlesource.com/platform/frameworks/support.git/+/master/LICENSE.txt"))
            add(LicenseItemViewModel("multidex", "https://android.googlesource.com/platform/frameworks/support.git/+/master/LICENSE.txt"))
            add(LicenseItemViewModel("lifecycle extensions", "https://android.googlesource.com/platform/frameworks/support.git/+/master/LICENSE.txt"))
        }

        binding.vm?.adapter?.items?.forEach { viewModel ->
            viewModel.onClickListener = View.OnClickListener {
                Intent(Intent.ACTION_VIEW, viewModel.url.toUri()).let {
                    startActivity(it)
                }
            }
        }

        binding.vm?.adapter?.notifyDataSetChanged()
    }
}