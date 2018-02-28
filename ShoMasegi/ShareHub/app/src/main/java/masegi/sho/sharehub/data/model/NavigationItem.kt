package masegi.sho.sharehub.data.model

import android.support.annotation.DrawableRes
import masegi.sho.sharehub.R
import masegi.sho.sharehub.presentation.event.MainActivity
import masegi.sho.sharehub.presentation.NavigationController
import kotlin.reflect.KClass

/**
 * Created by masegi on 2018/02/20.
 */

enum class NavigationItem(
        @DrawableRes val drawableRes: Int,
        val activityClass: KClass<*>,
        val navigate: NavigationController.() -> Unit
)
{

    FAVORITE(
            R.drawable.ic_favorite_white_36dp,
            MainActivity::class,
            {}
    ),
    PINNED(
            R.drawable.ic_bookmark_white_36dp,
            MainActivity::class,
            {}
    ),
    NOTIFICATION(
            R.drawable.ic_notifications_white_36dp,
            MainActivity::class,
            {}
    ),
    GIST(
            R.drawable.ic_view_list_white_36dp,
            MainActivity::class,
            {}
    ),
    BACK(
            R.drawable.ic_subdirectory_arrow_right_white_36dp,
            MainActivity::class,
            {}
    )
}