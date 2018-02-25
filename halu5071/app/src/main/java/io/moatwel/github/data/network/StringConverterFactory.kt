/*
 *  GitHub-Client
 *
 *  StringConverterFactory.kt
 *
 *  Copyright 2018 moatwel.io
 *  author : halu5071 (Yasunori Horii)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.moatwel.github.data.network

import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class StringConverterFactory : Converter.Factory() {

  override fun requestBodyConverter(type: Type?,
                                    parameterAnnotations: Array<out Annotation>?,
                                    methodAnnotations: Array<out Annotation>?,
                                    retrofit: Retrofit?): Converter<*, RequestBody>? {
    return if (type == String::class.java) {
      Converter<String, RequestBody> {
        RequestBody.create(PLAIN_TEXT, it)
      }
    } else {
      null
    }
  }

  override fun responseBodyConverter(type: Type?,
                                     annotations: Array<out Annotation>?,
                                     retrofit: Retrofit?): Converter<ResponseBody, *>? {
    return if (type == String::class.java) {
      Converter<ResponseBody, String> {
        it.string().toString()
      }
    } else {
      null
    }
  }

  companion object {
    private val PLAIN_TEXT = MediaType.parse("text/plain; charset=UTF-8")
  }
}