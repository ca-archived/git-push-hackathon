package net.matsudamper.git_push_hackathon.github

import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

class HttpGitHubURLConnection(url: URL) : AutoCloseable {
    private val connection = url.openConnection() as HttpURLConnection

    override fun close() {
        connection.disconnect()
    }

    fun setHeader(params: Map<String, String>) {
        params.forEach { (key, value) ->
            connection.addRequestProperty(key, value)
        }
    }

    fun getNextUrl(): String? {
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val linkField = connection.getHeaderField("Link") ?: return null

            linkField.split(",").forEach {
                val matcher = Pattern.compile("<(.+?)>; rel=\"next\"").matcher(it)
                if (matcher.find()) {
                    return matcher.group(1)
                }
            }
        }

        return null
    }

    fun getResponseString() = try {
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            BufferedInputStream(connection.inputStream).use { it.reader().use { it.readLines().joinToString() } }
        } else {
            throw IOException(BufferedInputStream(connection.inputStream).use { it.reader().use { it.readLines().joinToString() } })
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}