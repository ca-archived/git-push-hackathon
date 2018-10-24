package io.github.hunachi.user_usecase.di

import io.github.hunachi.user_infra.UserLocalRepository
import io.github.hunachi.user_usecase.UserUseCaseImpl
import io.github.hunachi.user_infra.UserClientFactory
import io.github.hunachi.user_usecase.UserUseCase
import org.koin.dsl.module.module

val userModule = module {

    single { UserUseCaseImpl(get()) as UserUseCase }

    factory { UserLocalRepository(get()) }

    single { UserClientFactory.apiRepositoryInstance(get()) }
}