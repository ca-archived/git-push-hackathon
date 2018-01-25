package io.moatwel.github.domain.repository

import io.moatwel.github.domain.entity.AuthData

interface AuthDataRepository {

  /**
   *  save user access token.
   *
   *  @param authData : auth data contains user access token
   */
  fun save(authData: AuthData)

  /**
   *  read auth data from some where
   *
   *  @return AuthData
   */
  fun get(): AuthData

  /**
   *  remove auth data
   */
  fun delete()
}