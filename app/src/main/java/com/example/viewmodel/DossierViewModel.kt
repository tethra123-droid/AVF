package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.DossierRepository
import com.example.model.BaileyMotteComparison
import com.example.model.DocumentedCase
import com.example.model.EvidenceBankCategory
import com.example.model.EvidenceEntry
import com.example.model.PatternPhase
import com.example.model.PsychMechanism
import com.example.model.RhetoricalTactic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class ThemeModePreference {
    SYSTEM,
    DARK,
    LIGHT
}

data class DossierUiState(
    val phases: List<PatternPhase> = DossierRepository.patternPhases,
    val selectedPhaseNumber: Int = 1,
    val comparisons: List<BaileyMotteComparison> = DossierRepository.baileyMotteComparisons,
    val selectedComparisonIndex: Int = 0,
    val allCases: List<DocumentedCase> = DossierRepository.documentedCases,
    val filteredCases: List<DocumentedCase> = DossierRepository.documentedCases,
    val caseSearchQuery: String = "",
    val selectedInterlocutor: String? = null,
    val selectedTactic: RhetoricalTactic? = null,
    val selectedCaseId: String = "caso_1",
    val mechanisms: List<PsychMechanism> = DossierRepository.psychMechanisms,
    val expandedMechanismIds: Set<String> = setOf("mec_a", "mec_b"),
    val allEvidence: List<EvidenceEntry> = DossierRepository.evidenceBank,
    val filteredEvidence: List<EvidenceEntry> = DossierRepository.evidenceBank,
    val evidenceSearchQuery: String = "",
    val selectedEvidenceCategory: EvidenceBankCategory? = null,
    val showOnlyBookmarked: Boolean = false,
    val bookmarkedIds: Set<String> = emptySet(),
    val methodologyExpanded: Boolean = false,
    val themePreference: ThemeModePreference = ThemeModePreference.DARK
)

class DossierViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DossierUiState())
    val uiState: StateFlow<DossierUiState> = _uiState.asStateFlow()

    fun selectPhase(phaseNumber: Int) {
        _uiState.update { it.copy(selectedPhaseNumber = phaseNumber.coerceIn(1, 5)) }
    }

    fun selectComparison(index: Int) {
        _uiState.update { state ->
            val safeIndex = index.coerceIn(0, (state.comparisons.size - 1).coerceAtLeast(0))
            state.copy(selectedComparisonIndex = safeIndex)
        }
    }

    fun toggleMethodology() {
        _uiState.update { it.copy(methodologyExpanded = !it.methodologyExpanded) }
    }

    fun cycleThemePreference() {
        _uiState.update { state ->
            val next = when (state.themePreference) {
                ThemeModePreference.DARK -> ThemeModePreference.LIGHT
                ThemeModePreference.LIGHT -> ThemeModePreference.SYSTEM
                ThemeModePreference.SYSTEM -> ThemeModePreference.DARK
            }
            state.copy(themePreference = next)
        }
    }

    fun updateCaseSearch(query: String) {
        _uiState.update { state ->
            val updated = state.copy(caseSearchQuery = query)
            updated.copy(filteredCases = filterCases(updated))
        }
    }

    fun selectInterlocutorFilter(interlocutor: String?) {
        _uiState.update { state ->
            val next = if (state.selectedInterlocutor == interlocutor) null else interlocutor
            val updated = state.copy(selectedInterlocutor = next)
            updated.copy(filteredCases = filterCases(updated))
        }
    }

    fun selectTacticFilter(tactic: RhetoricalTactic?) {
        _uiState.update { state ->
            val next = if (state.selectedTactic == tactic) null else tactic
            val updated = state.copy(selectedTactic = next)
            updated.copy(filteredCases = filterCases(updated))
        }
    }

    fun clearCaseFilters() {
        _uiState.update { state ->
            val updated = state.copy(
                caseSearchQuery = "",
                selectedInterlocutor = null,
                selectedTactic = null
            )
            updated.copy(filteredCases = filterCases(updated))
        }
    }

    fun selectCase(caseId: String) {
        _uiState.update { it.copy(selectedCaseId = caseId) }
    }

    fun toggleMechanismExpanded(id: String) {
        _uiState.update { state ->
            val next = if (id in state.expandedMechanismIds) {
                state.expandedMechanismIds - id
            } else {
                state.expandedMechanismIds + id
            }
            state.copy(expandedMechanismIds = next)
        }
    }

    fun updateEvidenceSearch(query: String) {
        _uiState.update { state ->
            val updated = state.copy(evidenceSearchQuery = query)
            updated.copy(filteredEvidence = filterEvidence(updated))
        }
    }

    fun selectEvidenceCategory(category: EvidenceBankCategory?) {
        _uiState.update { state ->
            val next = if (state.selectedEvidenceCategory == category) null else category
            val updated = state.copy(selectedEvidenceCategory = next)
            updated.copy(filteredEvidence = filterEvidence(updated))
        }
    }

    fun toggleShowOnlyBookmarked() {
        _uiState.update { state ->
            val updated = state.copy(showOnlyBookmarked = !state.showOnlyBookmarked)
            updated.copy(filteredEvidence = filterEvidence(updated))
        }
    }

    fun clearEvidenceFilters() {
        _uiState.update { state ->
            val updated = state.copy(
                evidenceSearchQuery = "",
                selectedEvidenceCategory = null,
                showOnlyBookmarked = false
            )
            updated.copy(filteredEvidence = filterEvidence(updated))
        }
    }

    fun toggleBookmark(itemId: String) {
        _uiState.update { state ->
            val next = if (itemId in state.bookmarkedIds) {
                state.bookmarkedIds - itemId
            } else {
                state.bookmarkedIds + itemId
            }
            val updated = state.copy(bookmarkedIds = next)
            updated.copy(filteredEvidence = filterEvidence(updated))
        }
    }

    private fun filterCases(state: DossierUiState): List<DocumentedCase> {
        val q = state.caseSearchQuery.trim().lowercase()
        return state.allCases.filter { c ->
            val matchesInterlocutor = state.selectedInterlocutor == null ||
                c.interlocutors.any { it.equals(state.selectedInterlocutor, ignoreCase = true) }
            val matchesTactic = state.selectedTactic == null || state.selectedTactic in c.tactics
            val matchesQuery = q.isEmpty() ||
                c.title.lowercase().contains(q) ||
                c.subtitle.lowercase().contains(q) ||
                c.overview.lowercase().contains(q) ||
                c.interlocutors.any { it.lowercase().contains(q) } ||
                c.sections.any { s ->
                    s.heading.lowercase().contains(q) ||
                        s.quotes.any { it.text.lowercase().contains(q) }
                } ||
                c.briefExchanges.any { b ->
                    b.trigger.lowercase().contains(q) ||
                        b.initialClaim.lowercase().contains(q) ||
                        b.pivotOrExcuse.lowercase().contains(q)
                } ||
                c.groupCases.any { g ->
                    g.interlocutor.lowercase().contains(q) ||
                        g.topic.lowercase().contains(q) ||
                        g.initialClaim.lowercase().contains(q) ||
                        g.pivotOrExcuse.lowercase().contains(q) ||
                        g.quotes.any { it.text.lowercase().contains(q) }
                }
            matchesInterlocutor && matchesTactic && matchesQuery
        }
    }

    private fun filterEvidence(state: DossierUiState): List<EvidenceEntry> {
        val q = state.evidenceSearchQuery.trim().lowercase()
        return state.allEvidence.filter { item ->
            val matchesCategory = state.selectedEvidenceCategory == null || item.category == state.selectedEvidenceCategory
            val matchesBookmark = !state.showOnlyBookmarked || item.id in state.bookmarkedIds
            val matchesQuery = q.isEmpty() ||
                item.quote.lowercase().contains(q) ||
                item.note.lowercase().contains(q) ||
                item.interlocutor.lowercase().contains(q) ||
                item.timestamp.lowercase().contains(q)
            matchesCategory && matchesBookmark && matchesQuery
        }
    }
}
