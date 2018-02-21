/*
 *  GitHub-Client
 *
 *  StringConverterFactoryTest.kt
 *
 *  Copyright 2018 moatwel.io
 *  author : halu5071 (Yasunori Horii)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.moatwel.github.data.network

import io.moatwel.github.TestUtil
import io.moatwel.github.data.network.retrofit.AccessTokenApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(PowerMockRunner::class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(StringConverterFactory::class)
class StringConverterFactoryTest {

  private lateinit var mockServer: MockWebServer
  private lateinit var retrofit: Retrofit
  private val resource: String = TestUtil.readResource("access_token.txt")

  @Before
  fun before() {
    mockServer = MockWebServer()
    retrofit = Retrofit.Builder()
      .baseUrl(mockServer.url("").toString())
      .addConverterFactory(StringConverterFactory())
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
  }

  @Test
  fun testGetAccessToken() {
    mockServer.enqueue(MockResponse().setBody(resource))

    val accessTokenApi = retrofit.create(AccessTokenApi::class.java)

    val resultObservable = accessTokenApi.fetchAccessToken(anyString(), anyString(), anyString())

    assertNotNull(resultObservable)

    resultObservable.test()
      .assertValue(resource)
      .assertComplete()
  }

  @After
  fun after() {
    mockServer.shutdown()
  }
}