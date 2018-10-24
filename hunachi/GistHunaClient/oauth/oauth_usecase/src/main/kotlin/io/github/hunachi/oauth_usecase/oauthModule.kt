package io.github.hunachi.oauth_usecase

import io.github.hunachi.oauth_infra.OauthClientFactory
import org.koin.dsl.module.module

val oauthModule = module {
    val OAUTH_URL = "oauthUri"
    val OAUTH_CLIENT = "oauthClient"

    factory(OAUTH_CLIENT) { OauthClientFactory.oauthClientInstance() }

    factory { OauthUseCaseImpl(get(OAUTH_CLIENT), get(OAUTH_URL)) as OauthUseCase }

    factory(name = OAUTH_URL) { OauthClientFactory.url }
}