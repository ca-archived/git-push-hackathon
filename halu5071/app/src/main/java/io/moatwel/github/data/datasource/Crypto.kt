/*
 *  GitHub-Client
 *
 *  Crypto.kt
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

package io.moatwel.github.data.datasource

import android.content.Context
import com.github.gfx.util.encrypt.Encryption
import timber.log.Timber
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException

open class Crypto(
  private val context: Context
) {

  private val cipher: Cipher? by lazy {
    var cipher: Cipher?
    try {
      cipher = Cipher.getInstance(Encryption.DEFAULT_ALGORITHM_MODE, "SC")
    } catch (e: NoSuchAlgorithmException) {
      cipher = null
      Timber.e(e)
    } catch (e: NoSuchProviderException) {
      cipher = null
      Timber.e(e)
    } catch (e: NoSuchPaddingException) {
      cipher = null
      Timber.e(e)
    }
    cipher
  }

  private fun createEncryption(): Encryption? {
    return cipher?.let {
      Encryption(it, Encryption.getDefaultPrivateKey(context))
    }
  }

  fun encrypt(plainText: String): String? {
    Timber.d("encrypt() PlainText: $plainText")
    val encryptedText = createEncryption()?.encrypt(plainText) ?: plainText
    Timber.d("encrypt() Encrypted: $encryptedText")
    return encryptedText
  }

  fun decrypt(encryptedString: String): String? {
    Timber.d("decrypt() Encrypted: $encryptedString")
    val plainText = createEncryption()?.decrypt(encryptedString) ?: encryptedString
    Timber.d("decrypt() PlainText: $plainText")
    return plainText
  }
}