package masegi.sho.sharehub.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import masegi.sho.sharehub.App
import masegi.sho.sharehub.di.activitymodule.LoginActivityBuilder
import masegi.sho.sharehub.di.activitymodule.MainActivityBuilder
import javax.inject.Singleton

/**
 * Created by masegi on 2018/02/02.
 */

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    ViewModelModule::class,
    LoginActivityBuilder::class,
    MainActivityBuilder::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance fun application(application: App): Builder
        fun networkModule(networkModule: NetworkModule): Builder
        fun build(): AppComponent
    }

    override fun inject(app: App)
}