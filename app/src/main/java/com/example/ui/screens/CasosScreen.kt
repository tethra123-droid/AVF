package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FolderOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.DossierRepository
import com.example.model.DocumentedCase
import com.example.model.RhetoricalTactic
import com.example.ui.components.SectionEyebrow
import com.example.ui.components.TacticPill
import com.example.viewmodel.DossierUiState

@Composable
fun CasosScreen(
    uiState: DossierUiState,
    onSearchChange: (String) -> Unit,
    onSelectInterlocutor: (String?) -> Unit,
    onSelectTactic: (RhetoricalTactic?) -> Unit,
    onClearFilters: () -> Unit,
    onOpenCase: (String) -> Unit,
    onSelectCaseInSplitPane: (String) -> Unit,
    onToggleBookmark: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isExpandedLayout = maxWidth >= 840.dp

        if (isExpandedLayout) {
            Row(modifier = Modifier.fillMaxSize()) {
                CaseListColumn(
                    uiState = uiState,
                    isSplitMode = true,
                    onSearchChange = onSearchChange,
                    onSelectInterlocutor = onSelectInterlocutor,
                    onSelectTactic = onSelectTactic,
                    onClearFilters = onClearFilters,
                    onCaseClick = onSelectCaseInSplitPane,
                    modifier = Modifier
                        .weight(0.44f)
                        .fillMaxHeight()
                )
                VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                val activeCase = uiState.filteredCases.find { it.id == uiState.selectedCaseId }
                    ?: uiState.allCases.find { it.id == uiState.selectedCaseId }
                    ?: uiState.allCases.first()
                CasoDetallePane(
                    documentedCase = activeCase,
                    bookmarkedIds = uiState.bookmarkedIds,
                    onToggleBookmark = onToggleBookmark,
                    showBackButton = false,
                    onBack = {},
                    modifier = Modifier
                        .weight(0.56f)
                        .fillMaxHeight()
                )
            }
        } else {
            CaseListColumn(
                uiState = uiState,
                isSplitMode = false,
                onSearchChange = onSearchChange,
                onSelectInterlocutor = onSelectInterlocutor,
                onSelectTactic = onSelectTactic,
                onClearFilters = onClearFilters,
                onCaseClick = onOpenCase,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CaseListColumn(
    uiState: DossierUiState,
    isSplitMode: Boolean,
    onSearchChange: (String) -> Unit,
    onSelectInterlocutor: (String?) -> Unit,
    onSelectTactic: (RhetoricalTactic?) -> Unit,
    onClearFilters: () -> Unit,
    onCaseClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SectionEyebrow("2. Pruebas documentadas por caso")
                Text(
                    text = "Archivo Cronológico (${uiState.filteredCases.size}/${uiState.allCases.size})",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                OutlinedTextField(
                    value = uiState.caseSearchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text(stringResource(R.string.search_cases_placeholder)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Buscar casos"
                        )
                    },
                    trailingIcon = {
                        if (uiState.caseSearchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { onSearchChange("") },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("clear_case_search")
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
                        .testTag("cases_search_input")
                )

                // Interlocutor filter row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = uiState.selectedInterlocutor == null,
                        onClick = { onSelectInterlocutor(null) },
                        label = { Text("Todos") },
                        modifier = Modifier.testTag("filter_interlocutor_all")
                    )
                    DossierRepository.allInterlocutors.forEach { person ->
                        FilterChip(
                            selected = uiState.selectedInterlocutor == person,
                            onClick = { onSelectInterlocutor(person) },
                            label = { Text(person) },
                            modifier = Modifier.testTag("filter_interlocutor_${person.lowercase().replace(" ", "_")}")
                        )
                    }
                }

                // Tactic filter row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RhetoricalTactic.entries.forEach { tactic ->
                        FilterChip(
                            selected = uiState.selectedTactic == tactic,
                            onClick = { onSelectTactic(tactic) },
                            label = { Text(tactic.label) },
                            modifier = Modifier.testTag("filter_tactic_${tactic.name.lowercase()}")
                        )
                    }
                }
            }
        }

        if (uiState.filteredCases.isEmpty()) {
            item {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.large,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FolderOff,
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
                                .testTag("clear_case_filters_button")
                        ) {
                            Text(stringResource(R.string.clear_filters))
                        }
                    }
                }
            }
        } else {
            items(
                items = uiState.filteredCases,
                key = { it.id }
            ) { item ->
                CaseSummaryCard(
                    documentedCase = item,
                    isSelected = isSplitMode && item.id == uiState.selectedCaseId,
                    onClick = { onCaseClick(item.id) }
                )
            }
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CaseSummaryCard(
    documentedCase: DocumentedCase,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) MaterialTheme.colorScheme.surfaceContainerHigh else MaterialTheme.colorScheme.surfaceContainer,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
        ),
        tonalElevation = if (isSelected) 4.dp else 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .clickable(onClick = onClick)
            .testTag("case_card_${documentedCase.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = documentedCase.code,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Text(
                        text = documentedCase.dateRange,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Abrir expediente ${documentedCase.code}",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = documentedCase.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = documentedCase.overview,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                documentedCase.interlocutors.forEach { person ->
                    TacticPill(
                        label = person,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                documentedCase.tactics.take(3).forEach { tactic ->
                    TacticPill(label = tactic.label)
                }
            }
        }
    }
}
