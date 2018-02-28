package masegi.sho.sharehub.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import masegi.sho.sharehub.data.api.helper.ApplicationJsonAdapterFactory
import masegi.sho.sharehub.data.api.GithubApi
import masegi.sho.sharehub.data.api.helper.EventTypeAdapter
import masegi.sho.sharehub.data.api.helper.LocalDateTimeAdapter
import masegi.sho.sharehub.data.api.helper.OAuthIntercepter
import masegi.sho.sharehub.data.model.event.Event
import masegi.sho.sharehub.presentation.common.pref.Prefs
import okhttp3.OkHttpClient
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by masegi on 2018/02/11.
 */

@Module
open class NetworkModule {

    companion object {

        val instance = NetworkModule()
    }

    @Singleton @Provides
    fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(OAuthIntercepter(
                            Prefs.accessToken,
                            Prefs.otp))
                    .build()

    @RetrofitGithub @Singleton @Provides
    fun provideRetrofitForGithub(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.github.com/")
                .addConverterFactory(
                        MoshiConverterFactory.create(
                                Moshi.Builder()
                                        .add(ApplicationJsonAdapterFactory.instance)
                                        .add(LocalDateTime::class.java, LocalDateTimeAdapter())
                                        .add(Event.EventType::class.java, EventTypeAdapter())
                                        .build())
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
    }

    @Singleton @Provides
    open fun provideGithubApi(@RetrofitGithub retrofit: Retrofit): GithubApi {

        return retrofit.create(GithubApi::class.java)
    }
}
