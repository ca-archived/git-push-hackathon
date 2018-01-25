/*
 *
 *  GitHub-Client
 *
 *  UserTest.kt
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

package io.moatwel.github.domain.entity

import io.moatwel.github.TestUtil
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(User::class)
class UserTest {

  @Test
  fun testSerializable() {
    val user = geneUser()

    val restore = TestUtil.readAndWriteSerializable(user)

    assertNotNull(restore)
    assertThat(restore.id, `is`(1234L))
  }

  private fun geneUser(): User {
    return User(
      1234L,
      "halu5071",
      "Yasunori Horii",
      "https://sample.com/avatar",
    "https://sample.com/gravatar",
      "https://api.github.com/users/halu5071",
      "https://github.com/halu5071",
      "https://api.github.com/followers",
      "Bio of halu5071",
      123L,
      456L,
      false,
      "horiiortho5@gmail.com",
      "moatwel.io"
    )
  }
}