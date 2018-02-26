package com.dev.touyou.gitme.Data

import android.content.SharedPreferences
import io.reactivex.Observable
import java.net.URL

/**
 * Created by touyou on 2018/02/25.
 */

object GitHubAPI {

    val isLoggedIn: Boolean
        get() {

            return false
        }

    init {


    }

    fun logIn(): Observable<User> {

        return Observable.empty()
    }

    fun fetchUser(): Observable<User> {

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
    private val cache: SharedPreferences? = null

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