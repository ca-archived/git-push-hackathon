package net.matsudamper.git_push_hackathon.util

fun <T : AutoCloseable, R> using(target: T, block: (T) -> R): R = target.use { block(it) }