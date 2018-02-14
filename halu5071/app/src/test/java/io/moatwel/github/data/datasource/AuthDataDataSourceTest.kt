package io.moatwel.github.data.datasource

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.moatwel.github.BuildConfig
import io.moatwel.github.R
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(AuthDataDataSource::class)
class AuthDataDataSourceTest {

  private lateinit var moshi: Moshi
  private lateinit var context: Context
  private lateinit var authDataDataSource: AuthDataDataSource

  @Before
  fun before() {
    moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    context = PowerMockito.mock(Context::class.java)
    authDataDataSource = AuthDataDataSource(context, moshi)
  }

  @Test
  fun testReadAuthData() {
    val mockSharedPreferences = PowerMockito.mock(SharedPreferences::class.java)
    val mockEditor = PowerMockito.mock(SharedPreferences.Editor::class.java)

    `when`(context.getSharedPreferences(ARG_PREFERENCE_NAME, Context.MODE_PRIVATE))
      .thenReturn(mockSharedPreferences)
    `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
    `when`(mockSharedPreferences.getString(ARG_AUTH_DATA, ""))
      .thenReturn("{ \"token\": \"hogehogeToken\"}")

    val authData = authDataDataSource.readFromSharedPreference()

    assertNotNull(authData)
  }

  @Test
  fun testFetchAccessToken() {
    val mockServer = MockWebServer()
    mockServer.start()
    mockServer.enqueue(MockResponse().setBody("hogehogeAccessToken"))

    val code = "fugafuga"

    `when`(context.getString(R.string.str_access_token_url, code, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET))
      .thenReturn(mockServer.url("").toString())

    authDataDataSource.fetchFromApi(code)
      .test()
      .assertValue("hogehogeAccessToken")
      .assertComplete()

    mockServer.shutdown()
  }

  companion object {
    const val ARG_PREFERENCE_NAME = "GITHUB_CLIENT_PREFERENCE"

    const val ARG_AUTH_DATA = "GITHUB_AUTH_DATA"
  }
}