package com.dev.touyou.gitme.Data

import android.content.SharedPreferences
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.URL
import java.util.prefs.Preferences
import kotlin.properties.Delegates

/**
 * Created by touyou on 2018/02/25.
 */

interface GitHubAPIInterface {

    @GET("/user")
    fun getCurrentUser(
            @Query("access_token") accessToken: String): Observable<User>

    @GET("/users/{userName}/received_events")
    fun getEvents(
            @Path("userName") userName: String,
            @Query("access_token") accessToken: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int): Observable<Array<Event>>

    companion object {

        fun create(): GitHubAPIInterface = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build()
                .create(GitHubAPIInterface::class.java)
    }
}

object GitHubAPI {

    /// Check log in or not
    val isLoggedIn: Boolean
        get() = cache.get(DefaultKeys.OAuthKey.rawValue, "") != ""

    init {
    }

    /// Log in function
    fun logIn(): Observable<User> = if (cache.get(DefaultKeys.OAuthKey.rawValue, "") != "") fetchUser() else authorize()

    /// Log out function
    fun logOut() {

        cache.put(DefaultKeys.OAuthKey.rawValue, "")
        cache.put(DefaultKeys.UserName.rawValue, "")
        cache.put(DefaultKeys.UserIcon.rawValue, "")
    }

    fun fetchUser(): Observable<User> = if (cache.get(DefaultKeys.UserName.rawValue, "") != "" && cache.get(DefaultKeys.UserIcon.rawValue, "") != "") {

        Observable.just(User(cache.get(DefaultKeys.UserName.rawValue, ""), 0, URL(cache.get(DefaultKeys.UserIcon.rawValue, ""))))
    } else {

        cache.get(DefaultKeys.OAuthKey.rawValue, "").let {

            base.getCurrentUser(it)
        }
        // TODO: ログインした後にとれたデータをどうにかして保存したい
    }

    fun fetchEvents(page: Int, perPage: Int): Observable<Array<Event> > = base.getEvents(
            cache.get(DefaultKeys.UserName.rawValue, ""),
            cache.get(DefaultKeys.OAuthKey.rawValue, ""),
            page,
            perPage
    )

    fun fetchRepositoryInfo(url: URL): Pair<Observable<Repository>, Observable<Readme>> {

        return Pair(Observable.empty(), Observable.empty())
    }

    private val base: GitHubAPIInterface = GitHubAPIInterface.create()
    private val cache: Preferences = Preferences.userRoot().node("GitMe")

    private fun authorize(): Observable<User> {

        return Observable.empty()
    }

//    private fun createRequestUrl(type: RequestURL): URL? {
//
//        val userName = "touyou"
//
//        val urlString = when(type) {
//            is RequestURL.GetCurrentUser -> this.base + "/user?access_token=$oauthKey"
//            is RequestURL.GetEvents -> this.base + "/users/$userName/received_events?access_token=$oauthKey&page=${type.page}&per_page=${type.perPage}"
//            is RequestURL.CustomRequest -> type.url.toString() + "?access_token=$oauthKey"
//        }
//
//        return URL(urlString)
//    }
}

enum class DefaultKeys(val rawValue: String) {
    OAuthKey("OAuthKey"),
    UserName("UserName"),
    UserIcon("UserIcon")
}