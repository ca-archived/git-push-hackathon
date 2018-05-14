package net.matsudamper.git_push_hackathon.util

fun <R> String.fromJson(type: Class<R>): R? {
    val moshi = com.squareup.moshi.Moshi.Builder().add(com.squareup.moshi.KotlinJsonAdapterFactory()).build()
    return moshi.adapter(type).fromJson(this)
}