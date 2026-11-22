package com.example.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.DossierRepository
import com.example.data.WhatsAppChatParser
import com.example.model.BaileyMotteComparison
import com.example.model.ChatMessage
import com.example.model.ChatTimelineItem
import com.example.model.DocumentedCase
import com.example.model.EvidenceBankCategory
import com.example.model.EvidenceEntry
import com.example.model.PatternPhase
import com.example.model.PsychMechanism
import com.example.model.RhetoricalTactic
import com.example.model.SunDynamicEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    val sunDynamics: List<SunDynamicEntry> = DossierRepository.sunBilateralDynamics,
    val expandedSunDynamicIds: Set<String> = setOf("sd_1", "sd_2"),
    val allEvidence: List<EvidenceEntry> = DossierRepository.evidenceBank,
    val filteredEvidence: List<EvidenceEntry> = DossierRepository.evidenceBank,
    val evidenceSearchQuery: String = "",
    val selectedEvidenceCategory: EvidenceBankCategory? = null,
    val showOnlyBookmarked: Boolean = false,
    val bookmarkedIds: Set<String> = emptySet(),
    val themePreference: ThemeModePreference = ThemeModePreference.DARK,
    // Chat viewer state (bound directly to intact assets/chat_registro.txt)
    val isChatLoading: Boolean = false,
    val chatLoadError: String? = null,
    val chatSourceLabel: String = "Tertulias/Agora episteme",
    val allChatMessages: List<ChatMessage> = emptyList(),
    val chatTimelineItems: List<ChatTimelineItem> = emptyList(),
    val availableChatDates: List<String> = emptyList(),
    val availableChatSpeakers: List<String> = emptyList(),
    val chatSearchQuery: String = "",
    val chatSelectedSpeaker: String? = null,
    val targetChatDateKey: String? = null,
    val targetChatTime: String? = null,
    val targetChatHighlightQuery: String? = null,
    val chatScrollNonce: Long = 0L
)

private fun buildTimelineItems(messages: List<ChatMessage>): List<ChatTimelineItem> {
    if (messages.isEmpty()) return emptyList()
    val items = ArrayList<ChatTimelineItem>(messages.size + 128)
    var currentDate: String? = null
    var headerIdx = 0
    val groupedCounts = messages.groupingBy { it.dateKey }.eachCount()
    for (msg in messages) {
        if (msg.dateKey != currentDate) {
            currentDate = msg.dateKey
            items.add(
                ChatTimelineItem.DateHeader(
                    id = "date_${headerIdx++}_${msg.dateKey}",
                    dateKey = msg.dateKey,
                    messageCount = groupedCounts[msg.dateKey] ?: 1
                )
            )
        }
        items.add(ChatTimelineItem.MessageItem(msg))
    }
    return items
}

class DossierViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DossierUiState())
    val uiState: StateFlow<DossierUiState> = _uiState.asStateFlow()

    private var chatLoadedFromContext = false
    private var pendingJumpRawDate: String? = null
    private var pendingJumpHighlight: String? = null

    fun ensureChatLoaded(context: Context) {
        if (chatLoadedFromContext) return
        chatLoadedFromContext = true
        val appContext = context.applicationContext

        _uiState.update { it.copy(isChatLoading = true, chatLoadError = null) }

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    appContext.assets.open(CHAT_ASSET_FILENAME).use { stream ->
                        WhatsAppChatParser.parse(stream)
                    }
                }
            }
            result.onSuccess { parsed ->
                applyLoadedChat(parsed)
            }.onFailure { err ->
                chatLoadedFromContext = false
                _uiState.update {
                    it.copy(
                        isChatLoading = false,
                        chatLoadError = err.localizedMessage ?: "No se pudo abrir el historial del chat."
                    )
                }
            }
        }
    }

    internal fun applyLoadedChat(messages: List<ChatMessage>) {
        val dates = messages.map { it.dateKey }.distinct()
        val speakerCounts = messages
            .filter { !it.isSystemMessage }
            .groupingBy { it.sender }
            .eachCount()
        val speakers = speakerCounts.entries
            .sortedByDescending { it.value }
            .map { it.key }
            .let { active ->
                if (messages.any { it.isSystemMessage }) active + "Sistema" else active
            }
        val timeline = buildTimelineItems(messages)

        _uiState.update { state ->
            val pendingDate = pendingJumpRawDate
            val pendingHighlight = pendingJumpHighlight
            pendingJumpRawDate = null
            pendingJumpHighlight = null

            val resolvedDate = if (pendingDate != null) {
                resolveAvailableDateKey(WhatsAppChatParser.normalizeDateKey(pendingDate), dates)
            } else {
                state.targetChatDateKey?.let { resolveAvailableDateKey(it, dates) }
            }
            val resolvedTime = if (pendingDate != null) {
                WhatsAppChatParser.normalizeTime(pendingDate)
            } else {
                state.targetChatTime
            }
            val resolvedHighlight = if (pendingDate != null) pendingHighlight else state.targetChatHighlightQuery
            val nextNonce = if (pendingDate != null) state.chatScrollNonce + 1L else state.chatScrollNonce

            state.copy(
                isChatLoading = false,
                chatLoadError = null,
                chatSourceLabel = "Tertulias/Agora episteme · ${messages.size} mensajes intactos",
                allChatMessages = messages,
                chatTimelineItems = timeline,
                availableChatDates = dates,
                availableChatSpeakers = speakers,
                chatSearchQuery = "",
                chatSelectedSpeaker = null,
                targetChatDateKey = resolvedDate,
                targetChatTime = resolvedTime,
                targetChatHighlightQuery = resolvedHighlight,
                chatScrollNonce = nextNonce
            )
        }
    }

    fun updateChatSearch(query: String) {
        _uiState.update { state ->
            val updated = state.copy(chatSearchQuery = query)
            updated.copy(chatTimelineItems = filterChatTimeline(updated))
        }
    }

    fun selectChatSpeaker(speaker: String?) {
        _uiState.update { state ->
            val next = if (state.chatSelectedSpeaker == speaker) null else speaker
            val updated = state.copy(chatSelectedSpeaker = next)
            updated.copy(chatTimelineItems = filterChatTimeline(updated))
        }
    }

    fun jumpToChatDate(rawDate: String, highlightQuery: String? = null) {
        val normalizedDate = WhatsAppChatParser.normalizeDateKey(rawDate)
        val normalizedTime = WhatsAppChatParser.normalizeTime(rawDate)
        _uiState.update { state ->
            if (state.allChatMessages.isEmpty()) {
                pendingJumpRawDate = rawDate
                pendingJumpHighlight = highlightQuery
                return@update state.copy(
                    targetChatDateKey = normalizedDate,
                    targetChatTime = normalizedTime,
                    targetChatHighlightQuery = highlightQuery
                )
            }
            val resolvedDate = resolveAvailableDateKey(normalizedDate, state.availableChatDates)
            // Reset search/speaker filters so the destination conversation is always visible
            val cleared = state.copy(
                chatSearchQuery = "",
                chatSelectedSpeaker = null,
                targetChatDateKey = resolvedDate,
                targetChatTime = normalizedTime,
                targetChatHighlightQuery = highlightQuery,
                chatScrollNonce = state.chatScrollNonce + 1L
            )
            cleared.copy(chatTimelineItems = buildTimelineItems(cleared.allChatMessages))
        }
    }

    fun clearChatJumpTarget() {
        pendingJumpRawDate = null
        pendingJumpHighlight = null
        _uiState.update {
            it.copy(
                targetChatDateKey = null,
                targetChatTime = null,
                targetChatHighlightQuery = null
            )
        }
    }

    private fun resolveAvailableDateKey(normalized: String, available: List<String>): String? {
        if (available.isEmpty()) return null
        if (normalized in available) return normalized
        // Handle partial date like "19/1" or "29/3" by matching day/month prefix
        val dayMonth = normalized.split("/").take(2).joinToString("/")
        val prefixMatch = available.firstOrNull { it.startsWith("$dayMonth/") }
        if (prefixMatch != null) return prefixMatch
        // Fallback: match same month/year if exact day has no entries
        val parts = normalized.split("/")
        if (parts.size >= 2) {
            val monthSuffix = "/${parts[1]}/"
            val sameMonth = available.firstOrNull { it.contains(monthSuffix) }
            if (sameMonth != null) return sameMonth
        }
        return available.firstOrNull()
    }

    private fun filterChatTimeline(state: DossierUiState): List<ChatTimelineItem> {
        val q = state.chatSearchQuery.trim().lowercase()
        val speaker = state.chatSelectedSpeaker
        if (q.isEmpty() && speaker == null) {
            return buildTimelineItems(state.allChatMessages)
        }
        val filtered = state.allChatMessages.filter { msg ->
            val matchesSpeaker = speaker == null ||
                msg.sender.equals(speaker, ignoreCase = true)
            val matchesQuery = q.isEmpty() ||
                msg.content.lowercase().contains(q) ||
                msg.sender.lowercase().contains(q) ||
                msg.dateKey.lowercase().contains(q) ||
                msg.time.lowercase().contains(q)
            matchesSpeaker && matchesQuery
        }
        return buildTimelineItems(filtered)
    }

    fun selectPhase(phaseNumber: Int) {
        _uiState.update { it.copy(selectedPhaseNumber = phaseNumber.coerceIn(1, 5)) }
    }

    fun selectComparison(index: Int) {
        _uiState.update { state ->
            val safeIndex = index.coerceIn(0, (state.comparisons.size - 1).coerceAtLeast(0))
            state.copy(selectedComparisonIndex = safeIndex)
        }
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

    fun toggleSunDynamicExpanded(id: String) {
        _uiState.update { state ->
            val next = if (id in state.expandedSunDynamicIds) {
                state.expandedSunDynamicIds - id
            } else {
                state.expandedSunDynamicIds + id
            }
            state.copy(expandedSunDynamicIds = next)
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
                } ||
                (c.bilateralAnalysis?.let { b ->
                    b.title.lowercase().contains(q) ||
                        b.reactionContext.lowercase().contains(q) ||
                        b.objectiveBalance.lowercase().contains(q) ||
                        b.flawsAndInfantilizationTraps.any { it.lowercase().contains(q) } ||
                        b.sunQuotes.any { it.text.lowercase().contains(q) }
                } == true)
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

    companion object {
        const val CHAT_ASSET_FILENAME = "chat_registro.txt"
    }
}
