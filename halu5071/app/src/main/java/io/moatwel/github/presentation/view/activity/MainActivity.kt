/*
 *  GitHub-Client
 *
 *  MainActivity.kt
 *
 *  Copyright 2018 moatwel.io
 *  author : halu5071 (Yasunori Horii)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.moatwel.github.presentation.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import io.moatwel.github.R
import io.moatwel.github.data.network.NetworkState
import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.data.repository.UserDataRepository
import io.moatwel.github.databinding.ActivityMainBinding
import io.moatwel.github.domain.repository.AuthDataRepository
import io.moatwel.github.presentation.view.adapter.EventAdapter
import io.moatwel.github.presentation.view.viewmodel.EventViewModel
import io.moatwel.github.presentation.view.viewmodel.EventViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var authDataRepository: AuthDataRepository

  @Inject
  lateinit var eventApi: EventApi

  @Inject
  lateinit var userRepository: UserDataRepository

  private lateinit var binding: ActivityMainBinding
  private val adapter = EventAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (authDataRepository.get() == null) {
      val intent = Intent(this, LoginActivity::class.java)
      startActivity(intent)
      return
    }

    userRepository.userLoadObservable
      .subscribe({
        // do nothing
      }, {
        Timber.e(it)
      }, {
        initViewModel()
      })
  }

  private fun initViewModel() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    val eventViewModelFactory = EventViewModelFactory(eventApi, userRepository)
    val viewModel = ViewModelProviders.of(this, eventViewModelFactory)
      .get(EventViewModel::class.java)

    binding.recycler.layoutManager = LinearLayoutManager(this)
    binding.recycler
      .addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    binding.recycler.adapter = adapter

    viewModel.events.observe(this, Observer { pagedList ->
      adapter.setList(pagedList)
      adapter.notifyDataSetChanged()
    })
    initSwipeRefresh(viewModel)
  }

  private fun initSwipeRefresh(viewModel: EventViewModel) {
    val swipeLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
    swipeLayout.setOnRefreshListener {
      viewModel.refresh()
    }

    viewModel.getNetworkState()?.observe(this, Observer {
      swipeLayout.isRefreshing = it == NetworkState.LOADING
    })
  }
}
