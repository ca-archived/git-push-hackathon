/*
 *  GitHub-Client
 *
 *  UserUsecase.kt
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

package io.moatwel.github.domain.usecase

import io.moatwel.github.domain.entity.User
import io.moatwel.github.domain.repository.UserRepository
import io.moatwel.github.presentation.util.observeOnMainThread
import io.moatwel.github.presentation.util.subscribeOnIoThread
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class UserUseCase (
  private val userRepository: UserRepository
) {

  private val loadUserSubject: PublishSubject<Unit> = PublishSubject.create()

  val loadUserObservable: Observable<Unit>
    get() = loadUserSubject

  var user: User? = null
    private set

  fun loadUserData() {
    userRepository.get()
      .subscribeOnIoThread()
      .observeOnMainThread()
      .subscribe({
        this.user = it
        this.loadUserSubject.onComplete()
      }, {
        this.loadUserSubject.onError(it)
      })
  }
}