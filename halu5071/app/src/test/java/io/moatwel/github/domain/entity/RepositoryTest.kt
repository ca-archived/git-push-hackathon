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
class RepositoryTest {

  private lateinit var moshi: Moshi
  private lateinit var adapter: JsonAdapter<Repository>

  @Before
  fun before() {
    moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .add(Date::class.java, Rfc3339DateJsonAdapter())
      .build()
    adapter = moshi.adapter(Repository::class.java)
  }

  @Test
  fun testSerializable() {
    val user = geneUser()
    val repository = Repository(
      1234L,
      "git-push-hackathon",
      "halu5071/git-push-hackathon",
      user,
      false,
      "This is a sample for git-push-hackathon",
      439,
      3,
      2,
      "Kotlin",
      8,
      3,
      Date()
    )

    val restore = TestUtil.readAndWriteSerializable(repository)

    assertNotNull(restore)
    assertThat(restore.isPrivate, `is`(false))
    assertThat(restore.id, `is`(1234L))
    assertThat(restore.name, `is`("git-push-hackathon"))
    assertThat(restore.size, `is`(439))
    assertThat(restore.language, `is`("Kotlin"))
    assertThat(restore.owner.id, `is`(1234L))
    assertThat(restore.owner.name, `is`("Yasunori Horii"))
    assertThat(restore.owner.login, `is`("halu5071"))
    assertThat(restore.owner.isHireable, `is`(false))
  }

  @Test
  fun testParseByMoshi() {
    val resource = TestUtil.readResource("repository.json")

    val repository = adapter.fromJson(resource)

    assertNotNull(repository)
    assertThat(repository?.id, `is`(115203383L))
    assertThat(repository?.name, `is`("conference-app-2018"))
    assertThat(repository?.description, `is`("This is a git-push-hackathon application"))
    assertThat(repository?.size, `is`(4646))
    assertThat(repository?.isPrivate, `is`(false))
    assertThat(repository?.owner?.id, `is`(10727543L))
    assertThat(repository?.owner?.login, `is`("DroidKaigi"))
    assertThat(repository?.owner?.name, `is`(""))
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