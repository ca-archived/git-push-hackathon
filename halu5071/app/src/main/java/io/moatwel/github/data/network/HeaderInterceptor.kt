package io.moatwel.github.data.network

import io.moatwel.github.domain.usecase.AuthDataUseCase
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
  private val authDataUseCase: AuthDataUseCase
): Interceptor{

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()

    val token = authDataUseCase.get()?.token ?: ""

    val newRequest = request.newBuilder()
      .addHeader("Authorization","token $token")
      .build()

    return chain.proceed(newRequest)
  }
}