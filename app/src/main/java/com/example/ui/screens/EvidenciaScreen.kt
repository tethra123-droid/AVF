package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.EvidenceBankCategory
import com.example.model.EvidenceEntry
import com.example.ui.components.CaseLinkChip
import com.example.ui.components.DateJumpChip
import com.example.ui.components.SectionEyebrow
import com.example.ui.components.TacticPill
import com.example.ui.theme.LocalForensicColors
import com.example.viewmodel.DossierUiState

@Composable
fun EvidenciaScreen(
    uiState: DossierUiState,
    onSearchChange: (String) -> Unit,
    onSelectCategory: (EvidenceBankCategory?) -> Unit,
    onToggleBookmarkedFilter: () -> Unit,
    onClearFilters: () -> Unit,
    onToggleBookmark: (String) -> Unit,
    onOpenCase: (String) -> Unit,
    onOpenChatAtDate: (String, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier
                    .widthIn(max = 760.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SectionEyebrow("Banco de evidencia literal")
                Text(
                    text = "Citas y tácticas indexadas (${uiState.filteredEvidence.size})",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                OutlinedTextField(
                    value = uiState.evidenceSearchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text(stringResource(R.string.search_evidence_placeholder)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Buscar evidencia"
                        )
                    },
                    trailingIcon = {
                        if (uiState.evidenceSearchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { onSearchChange("") },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("clear_evidence_search")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Limpiar búsqueda de evidencia"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("evidence_search_input")
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = uiState.selectedEvidenceCategory == null && !uiState.showOnlyBookmarked,
                        onClick = { onSelectCategory(null) },
                        label = { Text("Todas") },
                        modifier = Modifier.testTag("evidence_filter_all")
                    )
                    FilterChip(
                        selected = uiState.showOnlyBookmarked,
                        onClick = onToggleBookmarkedFilter,
                        label = { Text("Marcadas (${uiState.bookmarkedIds.size})") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Bookmark,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier.testTag("evidence_filter_bookmarked")
                    )
                    EvidenceBankCategory.entries.forEach { category ->
                        FilterChip(
                            selected = uiState.selectedEvidenceCategory == category,
                            onClick = { onSelectCategory(category) },
                            label = { Text(category.label) },
                            modifier = Modifier.testTag("evidence_filter_${category.name.lowercase()}")
                        )
                    }
                }
            }
        }

        if (uiState.filteredEvidence.isEmpty()) {
            item {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.large,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier
                        .widthIn(max = 760.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.SearchOff,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(36.dp)
                        )
                        Text(
                            text = stringResource(R.string.empty_results_title),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = stringResource(R.string.empty_results_hint),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Button(
                            onClick = onClearFilters,
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("clear_evidence_filters_button")
                        ) {
                            Text(stringResource(R.string.clear_filters))
                        }
                    }
                }
            }
        } else {
            items(
                items = uiState.filteredEvidence,
                key = { it.id }
            ) { entry ->
                EvidenceEntryCard(
                    entry = entry,
                    isBookmarked = entry.id in uiState.bookmarkedIds,
                    onToggleBookmark = { onToggleBookmark(entry.id) },
                    onOpenCase = { onOpenCase(entry.caseId) },
                    onDateClick = { onOpenChatAtDate(entry.timestamp, entry.quote) },
                    modifier = Modifier.widthIn(max = 760.dp)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }
    }
}

@Composable
private fun EvidenceEntryCard(
    entry: EvidenceEntry,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onOpenCase: () -> Unit,
    onDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val forensicColors = LocalForensicColors.current
    val categoryColor = when (entry.category) {
        EvidenceBankCategory.EXCUSAS -> MaterialTheme.colorScheme.tertiary
        EvidenceBankCategory.ETIQUETAS -> MaterialTheme.colorScheme.primary
        EvidenceBankCategory.TECNICISMOS -> MaterialTheme.colorScheme.secondary
        EvidenceBankCategory.AUTO_IMAGEN -> MaterialTheme.colorScheme.tertiary
        EvidenceBankCategory.LUCIDEZ -> forensicColors.lucidityAccent
    }

    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    TacticPill(
                        label = entry.category.label,
                        containerColor = categoryColor.copy(alpha = 0.16f),
                        contentColor = categoryColor
                    )
                    DateJumpChip(
                        dateLabel = entry.timestamp,
                        accentColor = categoryColor,
                        onClick = onDateClick
                    )
                }
                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("bookmark_evidence_${entry.id}")
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = if (isBookmarked) "Quitar marca" else "Marcar evidencia",
                        tint = if (isBookmarked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Text(
                text = "«${entry.quote}»",
                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Ángel (con ${entry.interlocutor}) · ${entry.note}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                CaseLinkChip(
                    code = entry.caseCode,
                    onClick = onOpenCase
                )
            }
        }
    }
}
