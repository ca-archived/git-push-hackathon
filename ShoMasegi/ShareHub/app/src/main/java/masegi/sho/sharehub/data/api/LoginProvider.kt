package masegi.sho.sharehub.data.api

import com.squareup.moshi.Moshi
import masegi.sho.sharehub.data.api.helper.ApplicationJsonAdapterFactory
import masegi.sho.sharehub.data.api.helper.OAuthIntercepter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by masegi on 2018/02/14.
 */

class LoginProvider {

    companion object {

        fun getLoginService(authToken: String, otp: String?): LoginService {

            return provideRetrofit(authToken, otp).create(LoginService::class.java)
        }

        fun getOauthLoginService(): LoginService {

            return provideRetrofit().create(LoginService::class.java)
        }

        private fun provideRetrofit(authToken: String, otp: String?): Retrofit {

            return Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .client(provideOkHttpClient(authToken, otp))
                    .addConverterFactory(
                            MoshiConverterFactory.create(
                                    Moshi.Builder()
                                            .add(ApplicationJsonAdapterFactory.instance)
                                            .build()
                            )
                    )
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                    .build()
        }

        private fun provideRetrofit(): Retrofit {

            return Retrofit.Builder()
                    .baseUrl("https://github.com/login/oauth/")
                    .client(OkHttpClient.Builder().build())
                    .addConverterFactory(
                            MoshiConverterFactory.create(
                                    Moshi.Builder()
                                            .add(ApplicationJsonAdapterFactory.instance)
                                            .build()
                            )
                    )
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                    .build()
        }

        private fun provideOkHttpClient(authToken: String, otp: String?): OkHttpClient {

            return OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(OAuthIntercepter(authToken, otp))
                    .build()
        }
    }
}