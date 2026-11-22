package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.BilateralAnalysis
import com.example.model.BriefExchange
import com.example.model.DocumentedCase
import com.example.model.GroupCase
import com.example.ui.components.DateJumpChip
import com.example.ui.components.SectionEyebrow
import com.example.ui.components.TacticPill
import com.example.ui.components.VerbatimQuoteCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CasoDetallePane(
    documentedCase: DocumentedCase,
    bookmarkedIds: Set<String>,
    onToggleBookmark: (String) -> Unit,
    onOpenChatAtDate: (String, String?) -> Unit,
    showBackButton: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier
                    .widthIn(max = 760.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (showBackButton) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("back_to_cases_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_to_cases)
                            )
                        }
                        Text(
                            text = stringResource(R.string.back_to_cases),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TacticPill(
                        label = documentedCase.code,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    DateJumpChip(
                        dateLabel = documentedCase.dateRange,
                        onClick = { onOpenChatAtDate(documentedCase.dateRange, null) }
                    )
                }

                Text(
                    text = documentedCase.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = documentedCase.subtitle,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    documentedCase.interlocutors.forEach { person ->
                        TacticPill(
                            label = "Interlocutor: $person",
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                    documentedCase.tactics.forEach { tactic ->
                        TacticPill(label = tactic.label)
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = documentedCase.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }
        }

        // Standard chronological sections (Cases 1–4)
        documentedCase.sections.forEach { section ->
            item(key = "${section.id}_header") {
                Column(
                    modifier = Modifier
                        .widthIn(max = 760.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(4.dp))
                    SectionEyebrow(section.heading)
                    if (!section.summary.isNullOrBlank()) {
                        Text(
                            text = section.summary,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            items(
                items = section.quotes,
                key = { it.id }
            ) { quote ->
                VerbatimQuoteCard(
                    quote = quote,
                    isBookmarked = quote.id in bookmarkedIds,
                    onToggleBookmark = { onToggleBookmark(quote.id) },
                    onDateClick = { date, text -> onOpenChatAtDate(date, text) },
                    modifier = Modifier.widthIn(max = 760.dp)
                )
            }
        }

        // Case 5 brief exchanges
        if (documentedCase.briefExchanges.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .widthIn(max = 760.dp)
                        .fillMaxWidth()
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    SectionEyebrow("Cronología de cruces breves con Sun")
                }
            }
            items(
                items = documentedCase.briefExchanges,
                key = { it.id }
            ) { brief ->
                BriefExchangeCard(
                    exchange = brief,
                    onDateClick = { onOpenChatAtDate("${brief.date} · ${brief.timeRange}", brief.initialClaim) },
                    modifier = Modifier.widthIn(max = 760.dp)
                )
            }
        }

        // Case 6 group exchanges
        if (documentedCase.groupCases.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .widthIn(max = 760.dp)
                        .fillMaxWidth()
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    SectionEyebrow("Evidencia transversal con otros integrantes")
                }
            }
            items(
                items = documentedCase.groupCases,
                key = { it.id }
            ) { groupCase ->
                GroupCaseCard(
                    groupCase = groupCase,
                    bookmarkedIds = bookmarkedIds,
                    onToggleBookmark = onToggleBookmark,
                    onOpenChatAtDate = onOpenChatAtDate,
                    modifier = Modifier.widthIn(max = 760.dp)
                )
            }
        }

        // Bilateral objective analysis (Sun's reaction, flaws, and infantilization traps)
        documentedCase.bilateralAnalysis?.let { bilateral ->
            item(key = "${documentedCase.id}_bilateral_header") {
                BilateralAnalysisCard(
                    analysis = bilateral,
                    modifier = Modifier.widthIn(max = 760.dp)
                )
            }
            items(
                items = bilateral.sunQuotes,
                key = { it.id }
            ) { quote ->
                VerbatimQuoteCard(
                    quote = quote,
                    isBookmarked = quote.id in bookmarkedIds,
                    onToggleBookmark = { onToggleBookmark(quote.id) },
                    onDateClick = { date, text -> onOpenChatAtDate(date, text) },
                    modifier = Modifier.widthIn(max = 760.dp)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
private fun BriefExchangeCard(
    exchange: BriefExchange,
    onDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = modifier.fillMaxWidth()
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
                DateJumpChip(
                    dateLabel = "${exchange.date} · ${exchange.timeRange}",
                    onClick = onDateClick
                )
            }
            Text(
                text = exchange.trigger,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            LabeledBlock(label = "AFIRMACIÓN INICIAL", text = exchange.initialClaim, isQuote = true)
            LabeledBlock(label = "GIRO / EXCUSA / DESVÍO", text = exchange.pivotOrExcuse, isQuote = false)
            LabeledBlock(label = "CIERRE", text = exchange.closure, isQuote = false)
        }
    }
}

@Composable
private fun GroupCaseCard(
    groupCase: GroupCase,
    bookmarkedIds: Set<String>,
    onToggleBookmark: (String) -> Unit,
    onOpenChatAtDate: (String, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = modifier.fillMaxWidth()
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
                TacticPill(
                    label = "EPISODIO ${groupCase.number} · ${groupCase.interlocutor}",
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
                DateJumpChip(
                    dateLabel = groupCase.date,
                    onClick = { onOpenChatAtDate(groupCase.date, null) }
                )
            }
            Text(
                text = groupCase.topic,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            LabeledBlock(label = "POSTURA INICIAL", text = groupCase.initialClaim, isQuote = true)
            LabeledBlock(label = "GIRO, EXCUSAS Y DESVÍO", text = groupCase.pivotOrExcuse, isQuote = false)
            groupCase.quotes.forEach { quote ->
                VerbatimQuoteCard(
                    quote = quote,
                    isBookmarked = quote.id in bookmarkedIds,
                    onToggleBookmark = { onToggleBookmark(quote.id) },
                    onDateClick = { date, text -> onOpenChatAtDate(date, text) }
                )
            }
        }
    }
}

@Composable
private fun LabeledBlock(
    label: String,
    text: String,
    isQuote: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            style = if (isQuote) {
                MaterialTheme.typography.bodyMedium.copy(
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium
                )
            } else {
                MaterialTheme.typography.bodySmall
            },
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun BilateralAnalysisCard(
    analysis: BilateralAnalysis,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(modifier = Modifier.height(2.dp))
        SectionEyebrow(
            text = "Autocrítica y análisis bilateral",
            color = MaterialTheme.colorScheme.tertiary
        )

        Surface(
            color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.34f),
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.45f)),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("bilateral_analysis_card")
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = analysis.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                LabeledBlock(
                    label = "POR QUÉ REACCIONÓ SUN ASÍ",
                    text = analysis.reactionContext,
                    isQuote = false
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "FALLAS PROPIAS Y TRAMPAS DE INFANTILIZACIÓN",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    analysis.flawsAndInfantilizationTraps.forEach { flaw ->
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "•",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Text(
                                text = flaw,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "BALANCE OBJETIVO",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = analysis.objectiveBalance,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
