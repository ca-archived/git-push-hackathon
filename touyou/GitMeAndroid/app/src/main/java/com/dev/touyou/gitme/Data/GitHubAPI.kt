package com.dev.touyou.gitme.Data

import android.content.SharedPreferences
import io.reactivex.Observable
import okhttp3.OkHttpClient
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
}

object GitHubAPI {

    /// Check log in or not
    val isLoggedIn: Boolean
        get() {


            return cache.get(DefaultKeys.OAuthKey.rawValue, "") != ""
        }

    init {
    }

    /// Log in function
    fun logIn(): Observable<User> {

        if (cache.get(DefaultKeys.OAuthKey.rawValue, "") != "") {

            return fetchUser()
        }

        return authorize()
    }

    /// Log out function
    fun logOut() {

        cache.put(DefaultKeys.OAuthKey.rawValue, "")
        cache.put(DefaultKeys.UserName.rawValue, "")
        cache.put(DefaultKeys.UserIcon.rawValue, "")
    }

    fun fetchUser(): Observable<User> {

        if (cache.get(DefaultKeys.UserName.rawValue) != "" && cache.get(DefaultKeys.UserIcon.rawValue) != "") {

            val userName = cache.get(DefaultKeys.UserName.rawValue, "")
            val userIconUrl = URL(cache.get(DefaultKeys.UserIcon.rawValue))
            val user = User(userName, 0, userIconUrl)
            return Observable.just(user)
        }

        // TODO: ここでGitHubAPIInterfaceのリクエストを叩く?

        return Observable.empty()
    }

    fun fetchEvents(page: Int, perPage: Int): Observable<Array<Event> > {

        return Observable.empty()
    }

    fun fetchRepositoryInfo(url: URL): Pair<Observable<Repository>, Observable<Readme>> {

        return Pair(Observable.empty(), Observable.empty())
    }

    private val base: String = "https://api.github.com"
    private val oauthKey: String? = null
    private val cache: Preferences = Preferences.userRoot().node("GitMe")
    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()

    private fun authorize(): Observable<User> {

        return Observable.empty()
    }

    private fun createRequestUrl(type: RequestURL): URL? {

        val userName = "touyou"

        val urlString = when(type) {
            is RequestURL.GetCurrentUser -> this.base + "/user?access_token=$oauthKey"
            is RequestURL.GetEvents -> this.base + "/users/$userName/received_events?access_token=$oauthKey&page=${type.page}&per_page=${type.perPage}"
            is RequestURL.CustomRequest -> type.url.toString() + "?access_token=$oauthKey"
        }

        return URL(urlString)
    }
}

enum class DefaultKeys(val rawValue: String) {
    OAuthKey("OAuthKey"),
    UserName("UserName"),
    UserIcon("UserIcon")
}

sealed class RequestURL {
    class GetCurrentUser: RequestURL()
    class GetEvents(val page: Int, val perPage: Int): RequestURL()
    class CustomRequest(val url: URL): RequestURL()
}