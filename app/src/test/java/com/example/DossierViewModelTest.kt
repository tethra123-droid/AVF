package com.example

import com.example.data.WhatsAppChatParser
import com.example.model.EvidenceBankCategory
import com.example.model.RhetoricalTactic
import com.example.viewmodel.DossierViewModel
import com.example.viewmodel.ThemeModePreference
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DossierViewModelTest {

    @Test
    fun `initial state contains all 6 cases, 5 phases, 6 mechanisms, and bilateral Sun dynamics`() {
        val vm = DossierViewModel()
        val state = vm.uiState.value
        assertEquals(6, state.allCases.size)
        assertEquals(5, state.phases.size)
        assertEquals(6, state.mechanisms.size)
        assertEquals(5, state.sunDynamics.size)
        assertEquals(23, state.allEvidence.size)
        assertTrue(state.allCases.take(5).all { it.bilateralAnalysis != null })
        assertTrue(state.allChatMessages.isEmpty())

        vm.toggleSunDynamicExpanded("sd_1")
        assertFalse("sd_1" in vm.uiState.value.expandedSunDynamicIds)
        vm.toggleSunDynamicExpanded("sd_1")
        assertTrue("sd_1" in vm.uiState.value.expandedSunDynamicIds)
    }

    @Test
    fun `filtering cases by interlocutor and tactic narrows results`() {
        val vm = DossierViewModel()
        vm.selectInterlocutorFilter("Pablo Coronel")
        assertEquals(1, vm.uiState.value.filteredCases.size)
        assertEquals("caso_6", vm.uiState.value.filteredCases.first().id)

        vm.clearCaseFilters()
        assertEquals(6, vm.uiState.value.filteredCases.size)

        vm.selectTacticFilter(RhetoricalTactic.SCHRODINGERS_DOUCHEBAG)
        assertEquals(1, vm.uiState.value.filteredCases.size)
        assertEquals("caso_2", vm.uiState.value.filteredCases.first().id)
    }

    @Test
    fun `bookmarking quote and filtering evidence by bookmarked works`() {
        val vm = DossierViewModel()
        vm.toggleBookmark("ev_1")
        vm.toggleShowOnlyBookmarked()
        assertEquals(1, vm.uiState.value.filteredEvidence.size)
        assertEquals("ev_1", vm.uiState.value.filteredEvidence.first().id)

        vm.clearEvidenceFilters()
        vm.selectEvidenceCategory(EvidenceBankCategory.LUCIDEZ)
        assertTrue(vm.uiState.value.filteredEvidence.all { it.category == EvidenceBankCategory.LUCIDEZ })
    }

    @Test
    fun `theme preference cycles cleanly`() {
        val vm = DossierViewModel()
        assertEquals(ThemeModePreference.DARK, vm.uiState.value.themePreference)
        vm.cycleThemePreference()
        assertEquals(ThemeModePreference.LIGHT, vm.uiState.value.themePreference)
        vm.cycleThemePreference()
        assertEquals(ThemeModePreference.SYSTEM, vm.uiState.value.themePreference)
        vm.cycleThemePreference()
        assertEquals(ThemeModePreference.DARK, vm.uiState.value.themePreference)
    }

    @Test
    fun `whatsapp parser handles system marks, multiline messages, and time normalization`() {
        val sample = """
            5/1/26, 17:26 - ‎N Castillo creó el grupo "Tertulias ²".
            09/04/2026, 2:24 a. m. - Angel Variable Filosófica: Y por eso me cago cuando dicen que plata es plata
            Línea siguiente del mismo mensaje
            [22/11/26, 03:51:00] Sun: Ni aunque se lo pidas?
        """.trimIndent()
        val parsed = WhatsAppChatParser.parse(sample)
        assertEquals(3, parsed.size)
        assertTrue(parsed[0].isSystemMessage)
        assertEquals("Sistema", parsed[0].sender)

        assertFalse(parsed[1].isSystemMessage)
        assertEquals("9/4/26", parsed[1].dateKey)
        assertEquals("Angel Variable Filosófica", parsed[1].sender)
        assertTrue(parsed[1].content.contains("Línea siguiente del mismo mensaje"))

        assertEquals("22/11/26", parsed[2].dateKey)
        assertEquals("Sun", parsed[2].sender)

        assertEquals("3:51", WhatsAppChatParser.normalizeTime("22/11/26 · 03:51"))
        assertEquals("0:19", WhatsAppChatParser.normalizeTime("19/1/26 · 00:19–00:20"))
        assertNull(WhatsAppChatParser.normalizeTime("14/11/26"))
    }

    @Test
    fun `pending jump before chat load resolves once messages apply`() {
        val vm = DossierViewModel()
        vm.jumpToChatDate("22/11/26 · 03:51", "La IA no debate")
        val sample = """
            9/4/26, 2:24 - Angel Variable Filosófica: Plata es plata
            22/11/26, 3:51 - Angel Variable Filosófica: La IA no debate
        """.trimIndent()
        vm.applyLoadedChat(WhatsAppChatParser.parse(sample))
        val state = vm.uiState.value
        assertEquals("22/11/26", state.targetChatDateKey)
        assertEquals("3:51", state.targetChatTime)
        assertEquals("La IA no debate", state.targetChatHighlightQuery)
        assertTrue(state.chatScrollNonce > 0L)
    }
}
