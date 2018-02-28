package masegi.sho.sharehub.util.ext

import retrofit2.HttpException

/**
 * Created by masegi on 2018/02/16.
 */

val Throwable.code: Int
    get() = (this as? HttpException)?.code() ?: -1
