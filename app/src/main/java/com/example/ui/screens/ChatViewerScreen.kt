package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.Attachment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.WhatsAppChatParser
import com.example.model.ChatMessage
import com.example.model.ChatTimelineItem
import com.example.ui.components.SectionEyebrow
import com.example.ui.theme.LocalForensicColors
import com.example.viewmodel.DossierUiState
import kotlin.math.abs

private val parenAnnotationRegex = Regex("""\([^)]*\)""")

private fun extractHighlightSnippets(raw: String?): List<String> {
    if (raw.isNullOrBlank()) return emptyList()
    val cleaned = raw
        .replace("«", "")
        .replace("»", "")
        .replace("\"", "")
        .replace(parenAnnotationRegex, " ")
    return cleaned
        .split("/", "...", "…", "·", "→")
        .map { it.trim().lowercase() }
        .filter { it.length >= 5 }
        .sortedByDescending { it.length }
}

private fun messageMatchesJumpHighlight(
    message: ChatMessage,
    targetDate: String?,
    targetTime: String?,
    snippets: List<String>
): Boolean {
    if (targetDate == null || message.dateKey != targetDate) return false
    val msgLower = message.content.lowercase()
    if (snippets.isNotEmpty()) {
        return snippets.any { msgLower.contains(it) }
    }
    if (targetTime != null) {
        return WhatsAppChatParser.normalizeTime(message.time) == targetTime
    }
    return false
}

@Composable
fun ChatViewerScreen(
    uiState: DossierUiState,
    onSearchChange: (String) -> Unit,
    onSelectSpeaker: (String?) -> Unit,
    onJumpToDate: (String) -> Unit,
    onClearJumpTarget: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val dateRowState = rememberLazyListState()
    val forensicColors = LocalForensicColors.current

    val snippets = remember(uiState.targetChatHighlightQuery) {
        extractHighlightSnippets(uiState.targetChatHighlightQuery)
    }

    LaunchedEffect(uiState.chatScrollNonce, uiState.chatTimelineItems) {
        val targetDate = uiState.targetChatDateKey ?: return@LaunchedEffect
        val items = uiState.chatTimelineItems
        if (items.isEmpty()) return@LaunchedEffect

        val targetTime = uiState.targetChatTime
        var targetIndex = -1

        // 1. Exact date + time + snippet match
        if (snippets.isNotEmpty() && targetTime != null) {
            targetIndex = items.indexOfFirst { item ->
                item is ChatTimelineItem.MessageItem &&
                    item.message.dateKey == targetDate &&
                    WhatsAppChatParser.normalizeTime(item.message.time) == targetTime &&
                    snippets.any { item.message.content.lowercase().contains(it) }
            }
        }
        // 2. Date + snippet match
        if (targetIndex < 0 && snippets.isNotEmpty()) {
            targetIndex = items.indexOfFirst { item ->
                item is ChatTimelineItem.MessageItem &&
                    item.message.dateKey == targetDate &&
                    snippets.any { item.message.content.lowercase().contains(it) }
            }
        }
        // 3. Date + exact normalized time match
        if (targetIndex < 0 && targetTime != null) {
            targetIndex = items.indexOfFirst { item ->
                item is ChatTimelineItem.MessageItem &&
                    item.message.dateKey == targetDate &&
                    WhatsAppChatParser.normalizeTime(item.message.time) == targetTime
            }
        }
        // 4. Date header fallback
        if (targetIndex < 0) {
            targetIndex = items.indexOfFirst { item ->
                item is ChatTimelineItem.DateHeader && item.dateKey == targetDate
            }
        }

        if (targetIndex >= 0) {
            val distance = abs(listState.firstVisibleItemIndex - targetIndex)
            if (distance > 35) {
                listState.scrollToItem(targetIndex)
            } else {
                listState.animateScrollToItem(targetIndex)
            }
        }

        val dateIdx = uiState.availableChatDates.indexOf(targetDate)
        if (dateIdx >= 0) {
            dateRowState.scrollToItem(dateIdx)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header & Intact Archive Controls
        Column(
            modifier = Modifier
                .widthIn(max = 760.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Verified,
                            contentDescription = "Archivo intacto verificado",
                            tint = forensicColors.lucidityAccent,
                            modifier = Modifier.size(15.dp)
                        )
                        SectionEyebrow("Archivo original intacto · Evidencia primaria")
                    }
                    Text(
                        text = uiState.chatSourceLabel,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (uiState.targetChatDateKey != null) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 10.dp, end = 2.dp)
                        ) {
                            Text(
                                text = buildString {
                                    append(uiState.targetChatDateKey)
                                    if (!uiState.targetChatTime.isNullOrBlank()) {
                                        append(" · ")
                                        append(uiState.targetChatTime)
                                    }
                                },
                                style = MaterialTheme.typography.labelMedium
                            )
                            IconButton(
                                onClick = onClearJumpTarget,
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("clear_chat_jump_target")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Quitar marca de salto",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            OutlinedTextField(
                value = uiState.chatSearchQuery,
                onValueChange = onSearchChange,
                placeholder = { Text(stringResource(R.string.search_chat_placeholder)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar en el chat"
                    )
                },
                trailingIcon = {
                    if (uiState.chatSearchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { onSearchChange("") },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("clear_chat_search")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Limpiar búsqueda"
                            )
                        }
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("chat_search_input")
            )

            // Quick Date Index Strip (LazyRow for 200+ dates)
            if (uiState.availableChatDates.isNotEmpty()) {
                LazyRow(
                    state = dateRowState,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item(key = "date_icon_marker") {
                        Icon(
                            imageVector = Icons.Filled.CalendarToday,
                            contentDescription = "Índice cronológico de fechas",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(16.dp)
                        )
                    }
                    items(
                        items = uiState.availableChatDates,
                        key = { it }
                    ) { dateKey ->
                        val isTarget = uiState.targetChatDateKey == dateKey
                        Surface(
                            color = if (isTarget) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = if (isTarget) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(
                                1.dp,
                                if (isTarget) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outlineVariant
                            ),
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onJumpToDate(dateKey) }
                                .testTag("chat_date_index_${dateKey.replace("/", "_")}")
                        ) {
                            Text(
                                text = dateKey,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            // Speaker Filter Strip (LazyRow sorted by participation)
            if (uiState.availableChatSpeakers.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item(key = "speaker_all") {
                        FilterChip(
                            selected = uiState.chatSelectedSpeaker == null,
                            onClick = { onSelectSpeaker(null) },
                            label = { Text("Todos") },
                            modifier = Modifier.testTag("chat_speaker_all")
                        )
                    }
                    items(
                        items = uiState.availableChatSpeakers,
                        key = { it }
                    ) { speaker ->
                        FilterChip(
                            selected = uiState.chatSelectedSpeaker == speaker,
                            onClick = { onSelectSpeaker(speaker) },
                            label = { Text(speaker) },
                            modifier = Modifier.testTag(
                                "chat_speaker_${speaker.lowercase().replace(" ", "_")}"
                            )
                        )
                    }
                }
            }
        }

        // Chat Timeline Body
        when {
            uiState.isChatLoading && uiState.chatTimelineItems.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.testTag("chat_loading_indicator")
                        )
                        Text(
                            text = "Cargando historial completo del chat…",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            uiState.chatLoadError != null && uiState.chatTimelineItems.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.chatLoadError,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            uiState.chatTimelineItems.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.empty_results_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            else -> {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .widthIn(max = 760.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = uiState.chatTimelineItems,
                        key = { item ->
                            when (item) {
                                is ChatTimelineItem.DateHeader -> item.id
                                is ChatTimelineItem.MessageItem -> item.message.id
                            }
                        }
                    ) { item ->
                        when (item) {
                            is ChatTimelineItem.DateHeader -> {
                                val isHighlightedDate = uiState.targetChatDateKey == item.dateKey
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Surface(
                                        color = if (isHighlightedDate) {
                                            MaterialTheme.colorScheme.secondaryContainer
                                        } else {
                                            MaterialTheme.colorScheme.surfaceContainerHigh
                                        },
                                        contentColor = if (isHighlightedDate) {
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        border = BorderStroke(
                                            1.dp,
                                            if (isHighlightedDate) {
                                                MaterialTheme.colorScheme.secondary
                                            } else {
                                                MaterialTheme.colorScheme.outlineVariant
                                            }
                                        )
                                    ) {
                                        Text(
                                            text = "${item.dateKey} · ${item.messageCount} mensajes",
                                            style = MaterialTheme.typography.labelMedium,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }

                            is ChatTimelineItem.MessageItem -> {
                                val isHighlightedMsg = messageMatchesJumpHighlight(
                                    message = item.message,
                                    targetDate = uiState.targetChatDateKey,
                                    targetTime = uiState.targetChatTime,
                                    snippets = snippets
                                )
                                ChatBubbleCard(
                                    message = item.message,
                                    isHighlighted = isHighlightedMsg
                                )
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }
    }
}

@Composable
private fun ChatBubbleCard(
    message: ChatMessage,
    isHighlighted: Boolean
) {
    val forensicColors = LocalForensicColors.current
    val isAngel = message.sender.contains("Ángel", ignoreCase = true) ||
        message.sender.contains("Angel", ignoreCase = true)

    val alignment = when {
        message.isSystemMessage -> Alignment.Center
        isAngel -> Alignment.CenterEnd
        else -> Alignment.CenterStart
    }

    val containerColor = when {
        message.isSystemMessage -> MaterialTheme.colorScheme.surfaceContainerLow
        isAngel -> MaterialTheme.colorScheme.surfaceContainerHigh
        else -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.45f)
    }

    val senderColor = when {
        message.isSystemMessage -> MaterialTheme.colorScheme.onSurfaceVariant
        isAngel -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.tertiary
    }

    val borderStroke = if (isHighlighted) {
        BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary)
    } else {
        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Surface(
            color = containerColor,
            shape = RoundedCornerShape(14.dp),
            border = borderStroke,
            modifier = Modifier.widthIn(max = 560.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.sender,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = senderColor
                    )
                    Text(
                        text = message.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (message.isMediaOmitted) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Attachment,
                            contentDescription = "Archivo multimedia omitido",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = message.content,
                            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Text(
                        text = message.content,
                        style = if (message.isSystemMessage) {
                            MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic)
                        } else {
                            MaterialTheme.typography.bodyMedium
                        },
                        color = if (isHighlighted) forensicColors.lucidityAccent else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
