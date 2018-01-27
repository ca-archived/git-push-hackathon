package io.moatwel.github.data.network

import io.moatwel.github.domain.entity.AuthData
import io.moatwel.github.domain.usecase.AuthDataUseCase
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.core.classloader.annotations.PowerMockIgnore

import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(AuthDataUseCase::class)
class HeaderInterceptorTest {
  @Test
  fun testHeaderOAuthToken() {
    val mockWebServer = MockWebServer()
    mockWebServer.start()
    mockWebServer.enqueue(MockResponse())

    val authDataUseCase = PowerMockito.mock(AuthDataUseCase::class.java)
    `when`(authDataUseCase.get()).thenReturn(AuthData("hogehogeToken"))

    val okHttpClient = OkHttpClient.Builder()
      .addInterceptor(HeaderInterceptor(authDataUseCase))
      .build()

    okHttpClient.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()

    val response = mockWebServer.takeRequest()

    assertNotNull(response)
    assertThat(response.getHeader("Authorization"), `is`("token hogehogeToken"))

    mockWebServer.shutdown()
  }
}