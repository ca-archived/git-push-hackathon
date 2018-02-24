package io.github.massongit.hackathon.push.git.main.user

import android.graphics.Bitmap
import android.net.Uri

/**
 * ユーザー情報
 * @param avatar Avatar
 * @param name ユーザー名
 * @param url ユーザーページのURL
 */
data class UserData(val avatar: Bitmap, val name: String?, val url: Uri)
