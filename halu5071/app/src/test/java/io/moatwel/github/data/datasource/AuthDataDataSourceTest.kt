package io.moatwel.github.data.datasource

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
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
  private lateinit var crypto: Crypto
  private lateinit var authDataDataSource: AuthDataDataSource

  @Before
  fun before() {
    moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    context = PowerMockito.mock(Context::class.java)
    crypto = PowerMockito.mock(Crypto::class.java)
    authDataDataSource = AuthDataDataSource(context, moshi)
  }

  @Test
  fun testReadAuthDataWithFailure() {
    val mockSharedPreferences = PowerMockito.mock(SharedPreferences::class.java)
    val mockEditor = PowerMockito.mock(SharedPreferences.Editor::class.java)

    `when`(context.getSharedPreferences(ARG_PREFERENCE_NAME, Context.MODE_PRIVATE))
      .thenReturn(mockSharedPreferences)
    `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
    `when`(mockSharedPreferences.getString(ARG_AUTH_DATA, ""))
      .thenReturn("")

    val authData = authDataDataSource.readFromSharedPreference(Crypto(context))

    assertNull(authData)
  }

  companion object {
    const val ARG_PREFERENCE_NAME = "GITHUB_CLIENT_PREFERENCE"

    const val ARG_AUTH_DATA = "GITHUB_AUTH_DATA"
  }
}