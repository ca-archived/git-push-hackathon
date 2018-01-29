package io.moatwel.github.domain.usecase

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import io.moatwel.github.domain.repository.AuthDataRepository
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.util.*

@RunWith(PowerMockRunner::class)
@PrepareForTest(AuthDataUseCase::class)
class AuthDataUseCaseTest {

  private lateinit var moshi: Moshi

  @Before
  fun before() {
    moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .add(Date::class.java, Rfc3339DateJsonAdapter())
      .build()
  }

  @Test
  fun testLoadAuthDataSuccessful() {
    val authDataRepository = PowerMockito.mock(AuthDataRepository::class.java)
    `when`(authDataRepository.get()).thenReturn("{\"token\": \"hogehogeToken\"}")

    val authDataUseCase = AuthDataUseCase(authDataRepository, moshi)

    val isLoaded = authDataUseCase.load()

    assertThat(isLoaded, `is`(true))
    assertNotNull(authDataUseCase.get())
  }

  @Test
  fun testLoadAuthDataFailed() {
    val authDataRepository = PowerMockito.mock(AuthDataRepository::class.java)
    `when`(authDataRepository.get()).thenReturn("")

    val authDataUseCase = AuthDataUseCase(authDataRepository, moshi)

    val isLoaded = authDataUseCase.load()

    assertThat(isLoaded, `is`(false))
    assertNull(authDataUseCase.get())
  }

}