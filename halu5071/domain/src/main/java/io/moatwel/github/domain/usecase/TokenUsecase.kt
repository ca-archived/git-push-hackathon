package io.moatwel.github.domain.usecase

import io.moatwel.github.domain.repository.TokenRepository

class TokenUsecase(
  val tokenRepository: TokenRepository
)