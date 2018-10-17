package io.github.hunachi.oauth.di

import io.github.hunachi.oauth.OAuthActionCreator
import io.github.hunachi.oauth.OAuthStore
import io.github.hunachi.oauth.data.OauthAdapter
import io.github.hunachi.oauth.data.OauthRepository
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val oauthModule = module {
    val OAUTH_URL = "oauthUri"

    factory { OauthAdapter.OauthApiInstans() }

    factory { OauthRepository(get(), get(OAUTH_URL)) }

    factory(name = OAUTH_URL) { OauthAdapter.url }

    viewModel { OAuthStore(get(), get()) }

    factory { OAuthActionCreator(get(), get()) }
}