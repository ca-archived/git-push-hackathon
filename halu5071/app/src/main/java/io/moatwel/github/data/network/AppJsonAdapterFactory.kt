package io.moatwel.github.data.network

import com.squareup.moshi.JsonAdapter
import se.ansman.kotshi.KotshiJsonAdapterFactory

@KotshiJsonAdapterFactory
abstract class AppJsonAdapterFactory : JsonAdapter.Factory {
  companion object {
    val INSTANCE : AppJsonAdapterFactory = KotshiAppJsonAdapterFactory()
  }
}