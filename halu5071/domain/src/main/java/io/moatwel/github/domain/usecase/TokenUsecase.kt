package io.moatwel.github.domain.usecase

import io.moatwel.github.domain.repository.AuthDataRepository

class TokenUsecase(
  val tokenRepository: AuthDataRepository
)