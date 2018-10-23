package io.github.hunachi.shared.flux

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.*

class Dispatcher {
    val bus: BroadcastChannel<Any> = BroadcastChannel(10)

    fun send(o: Any) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            bus.send(o)
            yield()
        }
    }

    inline fun <reified T> asChannel(): ReceiveChannel<T> {
        return bus.openSubscription().filter { it is T }.map { it as T }
    }
}
