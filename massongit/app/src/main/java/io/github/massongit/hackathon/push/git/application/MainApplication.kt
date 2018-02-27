package io.github.massongit.hackathon.push.git.application

import android.app.Application
import com.github.scribejava.core.oauth.OAuth20Service

/**
 * Application
 */
class MainApplication : Application() {
    /**
     * GitHub APIのサービス
     */
    var service: OAuth20Service? = null
}