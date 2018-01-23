package io.moatwel.github.data.repository

import io.moatwel.github.data.datasource.AuthDataDatasource
import io.moatwel.github.domain.entity.AuthData
import io.moatwel.github.domain.repository.AuthDataRepository

/**
 *  This class is an implementation class of [AuthDataRepository] on domain layer.
 *  This class read and delete auth data from somewhere, and save it on somewhere.
 *
 *  Actual operation is implemented [AuthDataDatasource].
 */
class AuthDataDataRepository(
  val dataSource: AuthDataDatasource) : AuthDataRepository {

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