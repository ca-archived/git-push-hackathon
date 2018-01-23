package io.moatwel.github.data.repository

import io.moatwel.github.domain.entity.User
import io.moatwel.github.domain.repository.UserRepository
import io.reactivex.Observable

/**
 *  This class is a implementation class of [UserRepository] on domain layer
 *  This class read user data from somewhere
 *
 *  Actual operation class is [CloudUserDatasource] or [DiskUserDatasource]
 */
class UserDataRepository : UserRepository {

  override fun get(): Observable<User> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}