package io.moatwel.github.data.datasource

import io.moatwel.github.data.network.UserApi
import io.moatwel.github.domain.entity.User
import io.reactivex.Observable
import javax.inject.Inject

class CloudUserDatasource @Inject constructor(
  private val api: UserApi) {

  fun getUser(): Observable<User> {
    return api.get()
  }
}