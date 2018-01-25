package io.moatwel.github.data.repository

import io.moatwel.github.data.datasource.CloudUserDataSource
import io.moatwel.github.domain.entity.User
import io.moatwel.github.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 *  This class is a implementation class of [UserRepository] on domain layer
 *  This class read user data from somewhere
 *
 *  Actual operation class is [CloudUserDataSource]
 */
class UserDataRepository @Inject constructor(
  private val cloudUserDataSource: CloudUserDataSource) : UserRepository {

  override fun get(): Observable<User> {
    return cloudUserDataSource.getUser()
  }
}