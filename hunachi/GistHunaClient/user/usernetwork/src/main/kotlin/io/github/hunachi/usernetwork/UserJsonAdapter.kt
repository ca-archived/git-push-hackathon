package io.github.hunachi.usernetwork

import com.squareup.moshi.FromJson
import io.github.hunachi.model.User
import io.github.hunachi.shared.network.CustomNetworkAdapter
import io.github.hunachi.usernetwork.model.UserJson

class UserJsonAdapter : CustomNetworkAdapter {

    @FromJson
    fun toUser(userJson: UserJson): User = User(
            login = userJson.login,
            id = userJson.id,
            avatar_url = userJson.avatar_url,
            html_url = userJson.html_url,
            gists_url = userJson.gists_url,
            isOwner = true
    )
}