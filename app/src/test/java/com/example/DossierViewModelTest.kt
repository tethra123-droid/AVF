package com.example

import com.example.model.EvidenceBankCategory
import com.example.model.RhetoricalTactic
import com.example.viewmodel.DossierViewModel
import com.example.viewmodel.ThemeModePreference
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DossierViewModelTest {

    @Test
    fun `initial state contains all 6 cases, 5 phases, and 6 mechanisms`() {
        val vm = DossierViewModel()
        val state = vm.uiState.value
        assertEquals(6, state.allCases.size)
        assertEquals(5, state.phases.size)
        assertEquals(6, state.mechanisms.size)
        assertEquals(23, state.allEvidence.size)
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
}
