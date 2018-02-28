package net.matsudamper.git_push_hackathon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.matsudamper.git_push_hackathon.appdata.AppData

class MainActivity : AppCompatActivity() {

    private val appData by lazy {
        AppData(this)
    }

    val navigationController by lazy {
        NavigationController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            if (appData.token.isNullOrEmpty()) {
                navigationController.navigateToLogin()
            } else {
                navigationController.navigationToEvents()
            }
        }
    }
}
