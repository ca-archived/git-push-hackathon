package masegi.sho.sharehub

import com.chibatching.kotpref.Kotpref
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import masegi.sho.sharehub.di.DaggerAppComponent
import masegi.sho.sharehub.di.NetworkModule

/**
 * Created by masegi on 2018/02/02.
 */

open class App : DaggerApplication() {

    override fun onCreate() {

        super.onCreate()
        Kotpref.init(this)
        AndroidThreeTen.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        return DaggerAppComponent.builder()
                .application(this)
                .networkModule(NetworkModule.instance)
                .build()
    }
}
