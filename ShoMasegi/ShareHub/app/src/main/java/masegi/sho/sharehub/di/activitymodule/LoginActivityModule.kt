package masegi.sho.sharehub.di.activitymodule

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import masegi.sho.sharehub.di.ViewModelKey
import masegi.sho.sharehub.presentation.login.LoginActivity
import masegi.sho.sharehub.presentation.login.LoginFragment
import masegi.sho.sharehub.presentation.login.LoginViewModel
import masegi.sho.sharehub.presentation.login.SplashScreenFragment

/**
 * Created by masegi on 2018/02/03.
 */

@Module
interface LoginActivityModule {

    @Binds
    fun providesAppCompatActivity(activity: LoginActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    fun contributeSplashFragment(): SplashScreenFragment

    @Binds @IntoMap @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel
}
