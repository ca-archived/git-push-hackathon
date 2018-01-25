/*
 *  GitHub-Client
 *
 *  AuthDataDataRepository.kt
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

import io.moatwel.github.data.datasource.AuthDataDataSource
import io.moatwel.github.domain.entity.AuthData
import io.moatwel.github.domain.repository.AuthDataRepository
import javax.inject.Inject

/**
 *  This class is an implementation class of [AuthDataRepository] on domain layer.
 *  This class read and delete auth data from somewhere, and save it on somewhere.
 *
 *  Actual operation is implemented on [AuthDataDataSource].
 */
class AuthDataDataRepository @Inject constructor(
  val dataSource: AuthDataDataSource) : AuthDataRepository {

  override fun save(authData: AuthData) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun get(): AuthData {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun delete() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}