package io.moatwel.github.domain.usecase

import io.moatwel.github.domain.entity.AuthData
import io.moatwel.github.domain.repository.AuthDataRepository
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(AuthDataUseCase::class)
class AuthDataUseCaseTest {

  @Test
  fun testLoadAuthDataSuccessful() {
    val authDataRepository = PowerMockito.mock(AuthDataRepository::class.java)
    `when`(authDataRepository.get()).thenReturn(AuthData("hogehogeToken"))

    val authDataUseCase = AuthDataUseCase(authDataRepository)

    val isLoaded = authDataUseCase.load()

    assertThat(isLoaded, `is`(true))
    assertNotNull(authDataUseCase.get())
    assertThat(authDataUseCase.get()?.token, `is`("hogehogeToken"))
  }

  @Test
  fun testLoadAuthDataFailed() {
    val authDataRepository = PowerMockito.mock(AuthDataRepository::class.java)
    `when`(authDataRepository.get()).thenReturn(null)

    val authDataUseCase = AuthDataUseCase(authDataRepository)

    val isLoaded = authDataUseCase.load()

    assertThat(isLoaded, `is`(false))
    assertNull(authDataUseCase.get())
  }

}