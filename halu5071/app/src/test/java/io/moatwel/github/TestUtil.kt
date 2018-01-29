/*
 *
 *  GitHub-Client
 *
 *  TestUtil.kt
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

package io.moatwel.github

import okio.Okio
import java.io.*

object TestUtil {
  @Suppress("UNCHECKED_CAST")
  @Throws(IOException::class, ClassNotFoundException::class)
  fun <T: Serializable>readAndWriteSerializable(src: T): T {
    val baos = ByteArrayOutputStream()
    ObjectOutputStream(baos).use { it.writeObject(src) }
    val bais = ByteArrayInputStream(baos.toByteArray())
    ObjectInputStream(bais).use { return it.readObject() as T }
  }

  fun readResource(fileName: String): String {
    val bufferedSource = Okio.buffer(Okio.source(javaClass.classLoader.getResourceAsStream(fileName)))
    val sourceText = bufferedSource.readUtf8()
    bufferedSource.close()
    return sourceText
  }
}