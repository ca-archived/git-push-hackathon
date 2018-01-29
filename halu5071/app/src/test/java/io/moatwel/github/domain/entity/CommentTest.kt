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
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CommentTest {

  private lateinit var moshi: Moshi
  private lateinit var adapter: JsonAdapter<Comment>

  @Before
  fun before() {
    moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .add(Date::class.java, Rfc3339DateJsonAdapter())
      .build()
    adapter = moshi.adapter(Comment::class.java)
  }

  @Test
  fun testSerializable() {
    val comment = Comment(
      1234L,
      geneUser(),
      "hogehoge",
      "https://github.com/comment",
      Date(),
      Date()
    )

    val restore = TestUtil.readAndWriteSerializable(comment)

    assertNotNull(restore)
    assertThat(restore.id, `is`(1234L))
    assertThat(restore.body, `is`("hogehoge"))
    assertThat(restore.url, `is`("https://github.com/comment"))
    assertThat(restore.user.name, `is`("Yasunori Horii"))
    assertThat(restore.user.id, `is`(1234L))
    assertThat(restore.user.isHireable, `is`(false))
  }

  @Test
  fun testParseByMoshi() {
    val resource = TestUtil.readResource("comment.json")

    val comment = adapter.fromJson(resource)

    assertNotNull(comment)
    assertThat(comment?.url, `is`("https://api.github.com"))
    assertThat(comment?.body, `is`("This is a really good change! :+1:"))
    assertThat(comment?.id, `is`(11056394L))
    assertThat(comment?.user?.avatarUrl, `is`("https://avatars.githubusercontent.com"))
    assertThat(comment?.user?.id, `is`(6752317L))
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
      12L,
      123L,
      false,
      "horiiortho5@gmail.com",
      "moatwel.io"
    )
  }
}