package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.WhatsAppChatParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Dossier Ángel", appName)
    }

    @Test
    fun `bundled intact chat asset parses cleanly and contains all dossier dates`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val messages = context.assets.open("chat_registro.txt").use { stream ->
            WhatsAppChatParser.parse(stream)
        }

        assertTrue("Expected full chat history, got ${messages.size}", messages.size > 10_000)

        val dates = messages.map { it.dateKey }.toSet()
        val expectedDates = listOf(
            "5/1/26",
            "6/1/26",
            "18/1/26",
            "19/1/26",
            "20/1/26",
            "19/2/26",
            "29/3/26",
            "5/4/26",
            "9/4/26",
            "25/4/26",
            "5/6/26",
            "7/9/26",
            "10/9/26",
            "11/11/26",
            "14/11/26",
            "16/11/26",
            "17/11/26",
            "22/11/26"
        )
        expectedDates.forEach { expected ->
            assertTrue("Missing date $expected in bundled chat_registro.txt", expected in dates)
        }

        val speakers = messages.map { it.sender }.toSet()
        assertTrue(speakers.any { it.contains("Angel", ignoreCase = true) })
        assertTrue(speakers.any { it.equals("Sun", ignoreCase = true) })
    }
}
