package io.moatwel.github.domain.entity

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import io.moatwel.github.TestUtil
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class IssueTest {

  private lateinit var moshi: Moshi
  private lateinit var adapter: JsonAdapter<Issue>

  @Before
  fun before() {
    moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .add(Date::class.java, Rfc3339DateJsonAdapter())
      .build()
    adapter = moshi.adapter(Issue::class.java)
  }

  @Test
  fun testSerializable() {
    val user = geneUser()
    val issue = Issue(
      1234L,
      "Fetch event data via Api",
      "overview",
      "https://api.github.com/issue",
      13,
      user,
      "open",
      14
    )

    val restore = TestUtil.readAndWriteSerializable(issue)

    assertNotNull(restore)
    assertThat(restore.id, `is`(1234L))
    assertThat(restore.title, `is`("Fetch event data via Api"))
    assertThat(restore.body, `is`("overview"))
    assertThat(restore.number, `is`(13))
    assertThat(restore.user.name, `is`("Yasunori Horii"))
    assertThat(restore.user.followers, `is`(123L))
    assertThat(restore.user.isHireable, `is`(false))
    assertThat(restore.user.email, `is`("horiiortho5@gmail.com"))
  }

  @Test
  fun testParseByMoshi() {
    val resource = TestUtil.readResource("issue.json")

    val issue = adapter.fromJson(resource)

    assertNotNull(issue)
    assertThat(issue?.id, `is`(292218194L))
    assertThat(issue?.title, `is`("[Assigned] we should use @CheckResults"))
    assertThat(issue?.number, `is`(519))
    assertThat(issue?.body, `is`("## Overview (Required)\r\n- It becomes more safely"))
    assertThat(issue?.user?.login, `is`("takahirom"))
    assertThat(issue?.user?.id, `is`(1386930L))
    assertThat(issue?.user?.email, `is`(""))
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