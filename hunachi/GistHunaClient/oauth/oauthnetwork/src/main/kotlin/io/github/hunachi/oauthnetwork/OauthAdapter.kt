package io.github.hunachi.oauth.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.github.hunachi.oauthnetwork.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object OauthAdapter {

    private const val scopes = "repo,gist"
    private const val baseUrl = "https://github.com/login/oauth/authorize"
    private const val clientId: String = BuildConfig.CLIENT_ID

    const val url = baseUrl + "?" +
            "client_id=" + clientId +
            "&scope=" + scopes

    private val githubOauth: OauthService

    init {
        val moshi by lazy {
            Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
        }

        val logging = HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

        val httpClient = OkHttpClient.Builder()
                .apply {
                    addInterceptor(logging)
                }

        val retrofit by lazy {
            Retrofit.Builder()
                    .baseUrl("https://github.com/login/oauth/")
                    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(httpClient.build())
                    .build()
        }
        githubOauth = retrofit.create(OauthService::class.java)
    }

    fun OauthApiInstans(): OauthService = githubOauth
}