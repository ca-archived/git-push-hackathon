package io.github.hunachi.shared.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

inline fun <reified T> createClient(
        baseUrl: String = "https://api.github.com/",
        adapter: CustomNetworkAdapter? = null,
        isLenientMode: Boolean = false
): T {

    val moshi by lazy {
        Moshi.Builder()
                .apply { if(adapter != null) add (adapter) }
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
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi).apply {
                    if (isLenientMode) asLenient()
                })
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(httpClient.build())
                .build()
    }
    return retrofit.create(T::class.java)
}