package io.github.hunachi.shared

import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.channels.*
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.yield

class Dispatcher {
    val bus: BroadcastChannel<Any> = ConflatedBroadcastChannel()

    fun send(o: Any) {
        GlobalScope.launch {
            bus.send(o)
            yield()
        }
    }

    inline fun <reified T> asChannel(): ReceiveChannel<T> {
        return bus.openSubscription().filter { it is T }.map { it as T }
    }
}
