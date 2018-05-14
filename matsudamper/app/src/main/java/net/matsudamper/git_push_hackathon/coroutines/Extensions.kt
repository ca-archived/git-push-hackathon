package net.matsudamper.git_push_hackathon.coroutines

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Job
import kotlin.coroutines.experimental.CoroutineContext

fun <T> async(
        context: CoroutineContext = CommonPool,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        parent: Job? = null,
        block: suspend CoroutineScope.() -> T) = kotlinx.coroutines.experimental.async(context, start, parent, block)