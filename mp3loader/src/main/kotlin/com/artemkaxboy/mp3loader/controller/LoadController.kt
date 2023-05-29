package com.artemkaxboy.mp3loader.controller

import com.artemkaxboy.mp3loader.client.RemoteAppClient
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedOutputStream
import java.nio.file.Files
import java.util.*
import kotlin.io.path.pathString

@RestController
class LoadController(
    private val remoteAppClient: RemoteAppClient,
) {

    private val logger = mu.KotlinLogging.logger {}
    private val regex = Regex("https.*pl.txt")

    @GetMapping("/load")
    fun load(@RequestParam source: String): ResponseEntity<ByteArray> {
        val mainPage = remoteAppClient.load(source)
        val mainPageBody = mainPage.body()!!.toString(Charsets.UTF_8)
        val playlistUrl = regex.find(mainPageBody)!!.value
        val playListResponse = remoteAppClient.load(playlistUrl, mapOf("referer" to "https://audiobook-mp3.com/"))
        val tmpFile = Files.createTempFile("mp3loader", "tar.gz")
        logger.info { "Saving to ${tmpFile.pathString}" }

        try {
            Files.newOutputStream(tmpFile).use { out ->
                BufferedOutputStream(out).use { buf ->
                    GzipCompressorOutputStream(buf).use { gz ->
                        TarArchiveOutputStream(gz).use { tar ->
                            val playlist = TarArchiveEntry("playlist.txt")
                                .apply { size = playListResponse.body()!!.size.toLong() }
                            tar.putArchiveEntry(playlist)
                            tar.write(playListResponse.body()!!)
                            tar.closeArchiveEntry()

                            Json.parseToJsonElement(playListResponse.body()!!.toString(Charsets.UTF_8))
                                .jsonArray.forEach { element ->
                                    val url = element.jsonObject["file"].toString().replace("\"", "")
                                    val name = element.jsonObject["title"].toString().replace("\"", "")

                                    logger.info { "Loading $name: $url" }

                                    val mp3response =
                                        remoteAppClient.load(url, mapOf("referer" to "https://audiobook-mp3.com/"))
                                    val mp3entry =
                                        TarArchiveEntry("$name.mp3").apply { size = mp3response.body()!!.size.toLong() }

                                    tar.putArchiveEntry(mp3entry)
                                    tar.write(mp3response.body()!!)
                                    tar.closeArchiveEntry()
                                }
                        }
                    }
                }
            }

            return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"${tmpFile.fileName}\"")
                .body(Files.readAllBytes(tmpFile))
        } finally {
            Files.delete(tmpFile)
        }
    }
}
