package com.dev.touyou.gitme.Data

import java.net.URL
import java.util.*

/**
 * Created by touyou on 2018/02/25.
 */

data class Repository(
        val id: Int,
        val name: String,
        val url: URL,
        val description: String?,
        val updatedAt: Date?,
        val stargazersCount: Int?,
        val language: String?
)

data class Readme(
        val downloadUrl: URL
)