package masegi.sho.sharehub.data.api.helper

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by masegi on 2018/02/14.
 */

class OAuthIntercepter constructor(
        var token: String? = null,
        var otp: String? = null
): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val builder = request.newBuilder()
        if (!token.isNullOrEmpty()) {

            var authToken =
                    if (token!!.startsWith("Basic")) token
                    else "token " + token
            builder.addHeader("Authorization", authToken)
        }
        if (!otp.isNullOrEmpty()) {

            builder.addHeader("X-GitHub-OTP", otp!!.trim())
        }
        request = builder.build()
        return chain.proceed(request)
    }
}

