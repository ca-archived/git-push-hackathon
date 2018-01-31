package io.moatwel.github.data.network

import com.squareup.moshi.*
import io.moatwel.github.TestUtil
import io.moatwel.github.domain.entity.event.Event
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.util.*

@RunWith(PowerMockRunner::class)
@PrepareForTest(EventJsonAdapter::class)
class EventJsonAdapterTest {

  private lateinit var moshi: Moshi

  @Before
  fun before() {
    moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .add(Date::class.java, Rfc3339DateJsonAdapter())
      .add(EventJsonAdapter.FACTORY)
      .build()
  }

  @Test
  fun parseEvents() {
    val resource = TestUtil.readResource("event_array.json")

    val type = Types.newParameterizedType(List::class.java, Event::class.java)
    val adapter: JsonAdapter<List<Event>> = moshi.adapter<List<Event>>(type)
    val list = adapter.fromJson(resource)

    assertNotNull(list)
  }

}