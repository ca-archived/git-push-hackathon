package masegi.sho.sharehub.data.api.helper

import com.squareup.moshi.JsonAdapter
import se.ansman.kotshi.KotshiJsonAdapterFactory

/**
 * Created by masegi on 2018/02/14.
 */

@KotshiJsonAdapterFactory
abstract class ApplicationJsonAdapterFactory : JsonAdapter.Factory {

    companion object {

        val instance: ApplicationJsonAdapterFactory = KotshiApplicationJsonAdapterFactory()
    }
}