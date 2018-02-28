package masegi.sho.sharehub.presentation.event

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import masegi.sho.sharehub.R
import masegi.sho.sharehub.databinding.ActivityMainBinding
import masegi.sho.sharehub.presentation.NavigationController
import masegi.sho.sharehub.presentation.common.BaseActivity
import masegi.sho.sharehub.presentation.common.DrawerMenu
import javax.inject.Inject

class MainActivity : BaseActivity() {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var drawerMenu: DrawerMenu

    private val binding by lazy {

        DataBindingUtil.setContentView<ActivityMainBinding>(
                this,
                R.layout.activity_main
        )
    }

    internal var title: TextView? = null


    // MARK: - Activity

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {

            it.setDisplayHomeAsUpEnabled(false)
        }
        binding.toolbarTitle.text = resources.getString(R.string.main_title)
        title = binding.toolbarTitle
        binding.navContent?.let {

            drawerMenu.setup(binding.drawerLayout, binding.content, binding.drawer, it, binding.toolbar)
        }
        if (savedInstanceState == null && binding != null) {

            navigationController.navigateToMain()
        }
    }

    override fun onBackPressed() {

        if (drawerMenu.closeDrawerIfNeeded()) {

            super.onBackPressed()
        }
    }


    companion object {

        private fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java)

        fun start(context: Context) {

            createIntent(context).let {

                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(it)
            }
        }
    }
}
