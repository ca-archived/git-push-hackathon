package io.moatwel.github.data.network

import com.squareup.moshi.*
import io.moatwel.github.TestUtil
import io.moatwel.github.domain.entity.event.*
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.util.Date

@RunWith(PowerMockRunner::class)
@PrepareForTest(EventJsonAdapter::class)
class EventJsonAdapterTest {

  private lateinit var moshi: Moshi
  private lateinit var adapter: JsonAdapter<List<Event>>

  @Before
  fun before() {
    moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .add(Date::class.java, Rfc3339DateJsonAdapter())
      .add(EventJsonAdapter.FACTORY)
      .build()

    val type = Types.newParameterizedType(List::class.java, Event::class.java)
    adapter = moshi.adapter(type)
  }

  @Test
  fun parseEvents() {
    val resource = TestUtil.readResource("event_array.json")

    val list = adapter.fromJson(resource)

    assertNotNull(list)
    assertNotNull(list?.get(0))
    assertNotNull(list?.get(1))
    assertNotNull(list?.get(2))
    assertThat(list?.get(0)?.id, `is`(7163073730L))
    assertThat(list?.get(0)?.type, `is`("WatchEvent"))
    assertThat(list?.get(0)?.actor?.id, `is`(32922369L))
    assertThat(list?.get(0)?.actor?.login, `is`("halubot"))
    assertThat(list?.get(0)?.repo?.id, `is`(7508411L))
    assertThat(list?.get(0)?.repo?.name, `is`("ReactiveX/RxJava"))
    assertThat((list?.get(0)?.payload as WatchPayload).action, `is`("started"))
    assertThat(list.get(1).id, `is`(7163070761L))
    assertThat(list.get(1).type, `is`("PullRequestReviewCommentEvent"))
    assertThat(list.get(1).actor?.id, `is`(1386930L))
    assertThat(list.get(1).actor?.login, `is`("takahirom"))
    assertThat(list.get(1).repo?.id, `is`(115203383L))
    assertThat(list.get(1).repo?.name, `is`("DroidKaigi/conference-app-2018"))
    assertThat((list.get(1).payload as PullRequestReviewCommentPayload).action,
      `is`("created"))
    assertThat(list.get(2).id, `is`(7163056449L))
    assertThat(list.get(2).type, `is`("PushEvent"))
    assertThat(list.get(2).actor?.id, `is`(944185L))
    assertThat(list.get(2).actor?.login, `is`("futabooo"))
    assertThat(list.get(2).repo?.id, `is`(115203383L))
    assertThat(list.get(2).repo?.name, `is`("DroidKaigi/conference-app-2018"))
    assertThat((list.get(2).payload as PushPayload).size, `is`(1))
    assertThat((list.get(2).payload as PushPayload).ref,
      `is`("refs/heads/add-session-feedback-screen"))
    assertThat((list.get(2).payload as PushPayload).size, `is`(1))
  }

  @Test
  fun parseEvents2() {
    val resource = TestUtil.readResource("event_array_2.json")

    val list = adapter.fromJson(resource)

    assertNotNull(list)
    assertNotNull(list?.get(0))
    assertNotNull(list?.get(1))
    assertNotNull(list?.get(2))
    assertThat(list?.get(0)?.id, `is`(7171333413L))
    assertThat(list?.get(0)?.type, `is`("PullRequestEvent"))
    assertThat(list?.get(0)?.actor?.id, `is`(1386930L))
    assertThat(list?.get(0)?.actor?.login, `is`("takahirom"))
    assertThat(list?.get(0)?.repo?.id, `is`(115203383L))
    assertThat(list?.get(0)?.repo?.name, `is`("DroidKaigi/conference-app-2018"))
    assertThat((list?.get(0)?.payload as PullRequestPayload).action, `is`("closed"))
    assertThat(list.get(1).id, `is`(7170819290L))
    assertThat(list.get(1).type, `is`("IssuesEvent"))
    assertThat(list.get(1).actor?.id, `is`(1386930L))
    assertThat(list.get(1).actor?.login, `is`("takahirom"))
    assertThat(list.get(1).repo?.id, `is`(115203383L))
    assertThat(list.get(1).repo?.name, `is`("DroidKaigi/conference-app-2018"))
    assertThat((list.get(1).payload as IssuesPayload).action, `is`("closed"))
    assertThat(list.get(2).id, `is`(7162984872L))
    assertThat(list.get(2).type, `is`("DeleteEvent"))
    assertThat(list.get(2).actor?.id, `is`(18444125L))
    assertThat(list.get(2).actor?.login, `is`("halu5071"))
    assertThat(list.get(2).repo?.id, `is`(118399797L))
    assertThat(list.get(2).repo?.name, `is`("halu5071/git-push-hackathon"))
    assertThat((list.get(2).payload as DeletePayload).ref, `is`("feature-32"))
    assertThat((list.get(2).payload as DeletePayload).refType, `is`("branch"))
  }
}