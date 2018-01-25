package io.moatwel.github.data.repository

import io.moatwel.github.data.datasource.CloudUserDatasource
import io.moatwel.github.domain.entity.User
import io.moatwel.github.domain.repository.UserRepository
import io.reactivex.Observable

/**
 *  This class is a implementation class of [UserRepository] on domain layer
 *  This class read user data from somewhere
 *
 *  Actual operation class is [CloudUserDatasource] or [DiskUserDatasource]
 */
class UserDataRepository(
  private val cloudUserDatasource: CloudUserDatasource) : UserRepository {

  override fun get(): Observable<User> {
    return cloudUserDatasource.getUser()
  }
}