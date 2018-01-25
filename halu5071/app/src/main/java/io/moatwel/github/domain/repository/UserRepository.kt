package io.moatwel.github.domain.repository

import io.moatwel.github.domain.entity.User
import io.reactivex.Observable

interface UserRepository {

  /**
   *  get user data from somewhere
   */
  fun get(): Observable<User>
}