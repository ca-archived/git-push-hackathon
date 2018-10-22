package io.github.hunachi.gist.util

import io.github.hunachi.gistnetwork.model.FileJson
import io.github.hunachi.model.File

internal fun Map.Entry<String, FileJson>.toFile(gistId: String) = File(
        filename = key,
        gistId = gistId,
        language = value.language,
        content = value.content ?: ""
)