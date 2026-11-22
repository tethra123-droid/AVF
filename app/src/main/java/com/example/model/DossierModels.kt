package com.example.model

enum class RhetoricalTactic(val label: String) {
    MOTTE_AND_BAILEY("Motte-and-Bailey"),
    SCHRODINGERS_DOUCHEBAG("Schrödinger’s Douchebag"),
    EXCUSAS_EXTERNAS("Excusas externas"),
    PATOLOGIZACION("Patologización del otro"),
    FUGA_TECNICA("Fuga hacia arriba"),
    CIERRE_UNILATERAL("Cierre unilateral")
}

enum class QuoteCategory(val label: String) {
    AFIRMACION_INICIAL("Afirmación inicial"),
    GIRO_REESCRITURA("Giro / Reescritura"),
    EXCUSA_EXTERNA("Excusa externa"),
    ETIQUETA_DESVIO("Etiqueta / Desvío"),
    CONTRADICCION("Contradicción literal"),
    LUCIDEZ_PARCIAL("Lucidez parcial")
}

enum class EvidenceBankCategory(val label: String) {
    EXCUSAS("Excusas externas"),
    ETIQUETAS("Etiquetas al otro"),
    TECNICISMOS("Fuga técnica"),
    AUTO_IMAGEN("Auto-imagen epistémica"),
    LUCIDEZ("Lucidez parcial")
}

data class PatternPhase(
    val number: Int,
    val code: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val triggerExamples: List<String>,
    val relatedCaseIds: List<String>
)

data class BaileyMotteComparison(
    val id: String,
    val caseId: String,
    val topic: String,
    val baileyClaim: String,
    val baileyTimestamp: String,
    val frictionSummary: String,
    val motteRetreat: String,
    val motteTimestamp: String,
    val excuseOrShield: String
)

data class LiteralQuote(
    val id: String,
    val timestamp: String,
    val speaker: String,
    val text: String,
    val roleLabel: String,
    val category: QuoteCategory
)

data class CaseSection(
    val id: String,
    val heading: String,
    val summary: String? = null,
    val quotes: List<LiteralQuote> = emptyList()
)

data class BriefExchange(
    val id: String,
    val date: String,
    val timeRange: String,
    val trigger: String,
    val initialClaim: String,
    val pivotOrExcuse: String,
    val closure: String
)

data class GroupCase(
    val id: String,
    val number: Int,
    val interlocutor: String,
    val topic: String,
    val date: String,
    val initialClaim: String,
    val pivotOrExcuse: String,
    val quotes: List<LiteralQuote>
)

data class DocumentedCase(
    val id: String,
    val code: String,
    val number: Int,
    val title: String,
    val subtitle: String,
    val dateRange: String,
    val interlocutors: List<String>,
    val tactics: List<RhetoricalTactic>,
    val overview: String,
    val sections: List<CaseSection> = emptyList(),
    val briefExchanges: List<BriefExchange> = emptyList(),
    val groupCases: List<GroupCase> = emptyList()
)

data class PsychMechanism(
    val id: String,
    val letter: String,
    val title: String,
    val tagline: String,
    val summary: String,
    val keyQuote: String,
    val keyQuoteTimestamp: String,
    val points: List<String>,
    val relatedCaseIds: List<String>
)

data class EvidenceEntry(
    val id: String,
    val quote: String,
    val timestamp: String,
    val interlocutor: String,
    val caseId: String,
    val caseCode: String,
    val category: EvidenceBankCategory,
    val note: String
)
