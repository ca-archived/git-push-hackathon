package io.github.hunachi.gist_infra.adapter

import io.github.hunachi.gist_infra.model.FileJson
import io.github.hunachi.model.File

internal fun Map.Entry<String, FileJson>.toFile(gistId: String) = File(
        filename = key,
        gistId = gistId,
        language = value.language,
        content = value.content ?: ""
)