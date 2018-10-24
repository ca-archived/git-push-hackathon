package io.github.hunachi.gist_usecase.di

import io.github.hunachi.gist_usecase.impl.GistPostUseCaseImpl
import io.github.hunachi.gist_usecase.impl.GistUseCaseImpl
import io.github.hunachi.gist_infra.GistClientFactory
import io.github.hunachi.gist_infra.GistLocalRepository
import io.github.hunachi.gist_usecase.GistPostUserCase
import io.github.hunachi.gist_usecase.GistUseCase
import org.koin.dsl.module.module

val gistModule = module {

    factory { GistUseCaseImpl(get(), get()) as GistUseCase }

    factory { GistPostUseCaseImpl(get()) as GistPostUserCase }

    single { GistLocalRepository(get()) }

    single { GistClientFactory.gistClientInstance(get()) }
}