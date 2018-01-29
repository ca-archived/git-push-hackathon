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

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.moatwel.github.TestUtil
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class UserTest {

  private lateinit var moshi: Moshi
  private lateinit var adapter: JsonAdapter<User>

  @Before
  fun before() {
    moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    adapter = moshi.adapter(User::class.java)
  }

  @Test
  fun testSerializable() {
    val user = geneUser()

    val restore = TestUtil.readAndWriteSerializable(user)

    assertNotNull(restore)
    assertThat(restore.id, `is`(1234L))
  }

  @Test
  fun testParseByMoshi() {
    val resource = TestUtil.readResource("user.json")

    val user = adapter.fromJson(resource)

    assertNotNull(user)
    assertThat(user?.id, `is`(1L))
    assertThat(user?.login, `is`("octocat"))
    assertThat(user?.name, `is`("monalisa octocat"))
    assertThat(user?.avatarUrl, `is`("https://github.com/images/error/octocat_happy.gif"))
    assertThat(user?.email, `is`("octocat@github.com"))
    assertThat(user?.bio, `is`("There once was..."))
    assertThat(user?.followers, `is`(20L))
    assertThat(user?.isHireable, `is`(false))
  }

  @Test
  fun testParseByMoshiContainsNull() {
    val resource = TestUtil.readResource("user_nullable.json")

    val user = adapter.fromJson(resource)

    assertNotNull(user)
    assertNull(user?.email)
    assertNull(user?.isHireable)
    assertNull(user?.company)
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
      "https://api.github.com/following",
      "Bio of halu5071",
      123L,
      456L,
      false,
      "horiiortho5@gmail.com",
      "moatwel.io"
    )
  }
}