/*
 *  GitHub-Client
 *
 *  UserDataRepository.kt
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

package io.moatwel.github.data.repository

import io.moatwel.github.data.datasource.CloudUserDataSource
import io.moatwel.github.domain.entity.User
import io.moatwel.github.domain.repository.UserRepository
import io.moatwel.github.presentation.util.observeOnMainThread
import io.moatwel.github.presentation.util.subscribeOnIoThread
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import timber.log.Timber

/**
 *  This class is a implementation class of [UserRepository] on domain layer
 *  This class read user data from somewhere
 *
 *  Actual operation class is [CloudUserDataSource]
 */
class UserDataRepository (
  private val cloudUserDataSource: CloudUserDataSource) : UserRepository {

  private var user: User? = null

  private val userLoadSubject: Subject<Unit> = BehaviorSubject.create()
  val userLoadObservable: Observable<Unit>
    get() = userLoadSubject

  override fun me(): User? {
    return this.user
  }

  override fun loadUser() {
    cloudUserDataSource.getUser()
      .subscribeOnIoThread()
      .observeOnMainThread()
      .subscribe({
        this.user = it
        this.userLoadSubject.onComplete()
      }, {
        Timber.e(it)
        this.userLoadSubject.onError(it)
      })
  }
}