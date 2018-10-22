package io.github.hunachi.oauth.di

import io.github.hunachi.oauth.OauthActionCreator
import io.github.hunachi.oauth.OAuthStore
import io.github.hunachi.oauthnetwork.OauthClientFactory
import io.github.hunachi.oauth.OauthRepository
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val oauthModule = module {
    val OAUTH_URL = "oauthUri"
    val OAUTH_CLIENT = "oauthClient"

    factory(OAUTH_CLIENT) { OauthClientFactory.oauthClientInstance() }

    factory { OauthRepository(get(OAUTH_CLIENT), get(OAUTH_URL)) }

    factory(name = OAUTH_URL) { OauthClientFactory.url }

    viewModel { OAuthStore(get()) }

    factory { OauthActionCreator(get(), get(), get()) }
}