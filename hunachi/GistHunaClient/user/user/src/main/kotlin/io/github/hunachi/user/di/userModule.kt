package io.github.hunachi.user.di

import io.github.hunachi.user.UserLocalRepository
import io.github.hunachi.user.UserRepository
import io.github.hunachi.usernetwork.UserClientFactory
import org.koin.dsl.module.module

val userModule = module {
    val USER_CLIENT = "userClient"

    single { UserRepository(get(USER_CLIENT), get()) }

    factory { UserLocalRepository(get()) }

    single(USER_CLIENT) { UserClientFactory.userClientInstance() }
}