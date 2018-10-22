package io.github.hunachi.oauth.di

import io.github.hunachi.oauth.OAuthActionCreator
import io.github.hunachi.oauth.OAuthStore
import io.github.hunachi.oauthnetwork.OAuthAdapter
import io.github.hunachi.oauthnetwork.OauthClient
import io.github.hunachi.oauthnetwork.OauthClientFactory
import io.github.hunachi.oauthnetwork.OauthRepository
import io.github.hunachi.user.di.userModule
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val oauthModule = module {
    val OAUTH_URL = "oauthUri"
    val OAUTH_CLIENT = "oauthClient"

    factory(OAUTH_CLIENT) { OauthClientFactory.oauthClientInstance() }

    factory { OauthRepository(get(OAUTH_CLIENT), get(OAUTH_URL)) }

    factory(name = OAUTH_URL) { OauthClientFactory.url }

    viewModel { OAuthStore(get(), get()) }

    factory { OAuthActionCreator(get(), get(), get()) }
}