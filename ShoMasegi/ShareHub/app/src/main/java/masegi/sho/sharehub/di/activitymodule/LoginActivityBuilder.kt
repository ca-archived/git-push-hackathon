package masegi.sho.sharehub.di.activitymodule

import dagger.Module
import dagger.android.ContributesAndroidInjector
import masegi.sho.sharehub.presentation.login.LoginActivity

/**
 * Created by masegi on 2018/02/08.
 */

@Module
interface LoginActivityBuilder {

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    fun contributeLoginActivity(): LoginActivity
}