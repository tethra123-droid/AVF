package com.example.data

import com.example.model.ChatMessage
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object WhatsAppChatParser {

    // Matches standard WhatsApp exports:
    // 1) "22/11/26, 3:51 - Angel Variable Filosófica: texto" or "22/11/2026, 3:51 a. m. - Ángel: texto"
    // 2) "[22/11/26, 03:51:12] Ángel: texto"
    private val bracketRegex = Regex(
        """^\[(\d{1,2}/\d{1,2}/\d{2,4}),\s*([^\]]+)\]\s*(.+)$"""
    )
    private val dashRegex = Regex(
        """^(\d{1,2}/\d{1,2}/\d{2,4}),\s*([^-]+?)\s*-\s*(.+)$"""
    )
    private val dateTokenRegex = Regex("""\b(\d{1,2})/(\d{1,2})/(\d{2,4})\b""")
    private val timeTokenRegex = Regex("""\b(\d{1,2}):(\d{2})\b""")

    private val systemPrefixes = listOf(
        "Los mensajes y las llamadas están cifrados",
        "Cambió tu código de seguridad",
        "Se eliminó este mensaje",
        "Se activaron los mensajes temporales",
        "Se desactivaron los mensajes temporales"
    )

    private val systemContainsMarkers = listOf(
        "creó el grupo",
        "te añadió",
        "añadió a",
        "salió del grupo",
        "eliminó a",
        "cambió el ícono",
        "cambió el asunto",
        "cambió la descripción",
        "fijó un mensaje"
    )

    fun normalizeDateKey(raw: String): String {
        val match = dateTokenRegex.find(raw.trim()) ?: return raw.trim()
        val day = match.groupValues[1].toIntOrNull()?.toString() ?: match.groupValues[1]
        val month = match.groupValues[2].toIntOrNull()?.toString() ?: match.groupValues[2]
        val yearRaw = match.groupValues[3]
        val year = if (yearRaw.length == 4) yearRaw.takeLast(2) else yearRaw
        return "$day/$month/$year"
    }

    fun normalizeTime(raw: String?): String? {
        if (raw.isNullOrBlank()) return null
        val match = timeTokenRegex.find(raw.trim()) ?: return null
        val hour = match.groupValues[1].toIntOrNull()?.toString() ?: match.groupValues[1]
        val minute = match.groupValues[2]
        return "$hour:$minute"
    }

    fun parse(rawText: String): List<ChatMessage> {
        return parse(rawText.byteInputStream(StandardCharsets.UTF_8))
    }

    fun parse(inputStream: InputStream): List<ChatMessage> {
        val result = mutableListOf<ChatMessage>()
        var currentDate = ""
        var currentTime = ""
        var currentSender = ""
        val currentContent = StringBuilder()
        var currentIsSystem = false
        var index = 0

        fun flushCurrent() {
            if (currentDate.isNotEmpty() && currentContent.isNotEmpty()) {
                val text = currentContent.toString().trimEnd()
                val isMedia = text.contains("<Multimedia omitido>", ignoreCase = true) ||
                    text.contains("<Media omitted>", ignoreCase = true)
                result.add(
                    ChatMessage(
                        id = "wa_${index++}",
                        dateKey = currentDate,
                        time = currentTime,
                        sender = currentSender,
                        content = text,
                        isMediaOmitted = isMedia,
                        isSystemMessage = currentIsSystem
                    )
                )
            }
            currentContent.clear()
        }

        BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8)).use { reader ->
            var rawLine = reader.readLine()
            while (rawLine != null) {
                val line = rawLine.trimStart('\uFEFF', '\u200E', '\u200F')
                val bracketMatch = bracketRegex.matchEntire(line)
                val dashMatch = if (bracketMatch == null) dashRegex.matchEntire(line) else null
                val match = bracketMatch ?: dashMatch

                if (match != null) {
                    flushCurrent()
                    currentDate = normalizeDateKey(match.groupValues[1])
                    currentTime = match.groupValues[2].trim()
                    val body = match.groupValues[3].trimStart('\uFEFF', '\u200E', '\u200F')
                    val colonIdx = body.indexOf(':')
                    val looksLikeSystemWithoutSender = colonIdx <= 0 ||
                        systemPrefixes.any { body.startsWith(it, ignoreCase = true) } ||
                        (colonIdx > 48 && systemContainsMarkers.any { body.contains(it, ignoreCase = true) })

                    if (!looksLikeSystemWithoutSender) {
                        currentSender = body.substring(0, colonIdx).trim()
                        currentContent.append(body.substring(colonIdx + 1).trimStart())
                        currentIsSystem = false
                    } else {
                        currentSender = "Sistema"
                        currentContent.append(body.trim())
                        currentIsSystem = true
                    }
                } else if (currentDate.isNotEmpty()) {
                    if (currentContent.isNotEmpty()) currentContent.append('\n')
                    currentContent.append(rawLine)
                }
                rawLine = reader.readLine()
            }
        }
        flushCurrent()
        return result
    }
}
