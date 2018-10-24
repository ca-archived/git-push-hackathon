package io.github.hunachi.oauth.di

import io.github.hunachi.oauth.OauthActionCreator
import io.github.hunachi.oauth.OAuthStore
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val oauthUiModule = module {

    viewModel { OAuthStore(get()) }

    factory { OauthActionCreator(get(), get(), get()) }
}