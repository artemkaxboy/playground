package com.artemkaxboy.mp3loader.client

import org.springframework.stereotype.Component
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class RemoteAppClient {

    private val client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build()

    fun load(source: String, headers: Map<String, String> = emptyMap()): HttpResponse<ByteArray> {
        val request = HttpRequest.newBuilder()
            .uri(java.net.URI.create(source))
            .apply {
                headers.forEach { (t, u) ->
                    setHeader(t, u)
                }
            }
            .build()
        return client.send(request, HttpResponse.BodyHandlers.ofByteArray())
    }
}
