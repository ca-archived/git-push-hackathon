package com.dev.touyou.gitme.Data

import java.net.URL

/**
 * Created by touyou on 2018/02/25.
 */

data class User(
        val login: String,
        val id: Int,
        val avatarUrl: URL
)