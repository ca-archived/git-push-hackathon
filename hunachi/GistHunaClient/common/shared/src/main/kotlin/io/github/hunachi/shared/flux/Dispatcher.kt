package io.github.hunachi.shared.flux

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.*

class Dispatcher {
    val bus: BroadcastChannel<Any> = ConflatedBroadcastChannel()

    fun send(o: Any) {
        CoroutineScope(Dispatchers.IO).launch {
            bus.send(o)
            yield()
        }
    }

    inline fun <reified T> asChannel(): ReceiveChannel<T> {
        return bus.openSubscription().filter { it is T }.map { it as T }
    }
}
