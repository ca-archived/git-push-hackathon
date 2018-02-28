package masegi.sho.sharehub.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by masegi on 2018/02/02.
 */

@Module
internal object AppModule {

    @Singleton
    @Provides @JvmStatic
    fun provideContext(application: Application): Context = application
}