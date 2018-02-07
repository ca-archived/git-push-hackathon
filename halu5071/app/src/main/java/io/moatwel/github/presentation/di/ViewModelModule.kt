package io.moatwel.github.presentation.di

import dagger.Module
import dagger.Provides
import io.moatwel.github.data.network.retrofit.EventApi
import io.moatwel.github.presentation.view.viewmodel.EventViewModel

@Module
class ViewModelModule {

  @Provides
  fun provideEventViewModel(api: EventApi): EventViewModel {
    return EventViewModel(api)
  }
}