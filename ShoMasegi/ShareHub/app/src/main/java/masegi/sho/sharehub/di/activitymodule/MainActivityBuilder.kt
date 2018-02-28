package masegi.sho.sharehub.di.activitymodule

import dagger.Module
import dagger.android.ContributesAndroidInjector
import masegi.sho.sharehub.presentation.event.MainActivity

/**
 * Created by masegi on 2018/02/14.
 */

@Module
interface MainActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun contributeMainActivity(): MainActivity
}