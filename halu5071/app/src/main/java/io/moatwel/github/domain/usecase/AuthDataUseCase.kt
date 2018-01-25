package io.moatwel.github.domain.usecase

import io.moatwel.github.domain.entity.AuthData
import io.moatwel.github.domain.repository.AuthDataRepository
import javax.inject.Inject

class AuthDataUseCase @Inject constructor(
  private val tokenRepository: AuthDataRepository
) {

  fun save(token: String) {
    val authData = AuthData(token)
    tokenRepository.save(authData)
  }
}