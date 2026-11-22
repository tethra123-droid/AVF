package com.example.data

import com.example.model.BaileyMotteComparison
import com.example.model.BilateralAnalysis
import com.example.model.BriefExchange
import com.example.model.CaseSection
import com.example.model.DocumentedCase
import com.example.model.EvidenceBankCategory
import com.example.model.EvidenceEntry
import com.example.model.GroupCase
import com.example.model.LiteralQuote
import com.example.model.PatternPhase
import com.example.model.PsychMechanism
import com.example.model.QuoteCategory
import com.example.model.RhetoricalTactic
import com.example.model.SunDynamicEntry

object DossierRepository {

    val patternPhases: List<PatternPhase> = listOf(
        PatternPhase(
            number = 1,
            code = "FASE 01",
            title = "Afirmación categórica o provocación inicial",
            subtitle = "Territorio Bailey",
            description = "Lanza una sentencia absoluta, tajante o burlesca sin matices ni condiciones previas.",
            triggerExamples = listOf(
                "\"La IA no debate... no coje postura aunque tú se la des\"",
                "\"me cago cuando dicen que plata es plata\"",
                "\"Es la ciencia que más está en pañales\"",
                "\"persona no debe de tener personajes con poderes conceptuales\""
            ),
            relatedCaseIds = listOf("caso_1", "caso_2", "caso_3", "caso_6")
        ),
        PatternPhase(
            number = 2,
            code = "FASE 02",
            title = "Fricción o refutación",
            subtitle = "Confrontación con datos o lógica",
            description = "El interlocutor (principalmente Sun, pero también Pablo Coronel, Armando, MariaJosé, N Castillo o Tarek) señala la falla lógica, pide un método concreto o aporta evidencia contraria.",
            triggerExamples = listOf(
                "Sun (3:55–3:56): \"Ni aunque se lo pidas? ... Prompteas mal / Es como culpar a la calculadora por sacarte mal una fórmula\"",
                "Sun (19/2 8:00–8:06): aclara que la prueba de Raven son matrices progresivas de razonamiento, no test de autismo ni DSM-5",
                "Pablo Coronel (28–29/3): distingue ordinarias vs. parciales y cita ecuaciones de onda, temperatura y Schrödinger"
            ),
            relatedCaseIds = listOf("caso_1", "caso_4", "caso_6")
        ),
        PatternPhase(
            number = 3,
            code = "FASE 03",
            title = "Reescritura retroactiva y escudo",
            subtitle = "Motte-and-Bailey o fallo externo",
            description = "En lugar de sostener la tesis original o conceder llanamente el error, ejecuta una maniobra Motte-and-Bailey (niega la versión fuerte y se repliega hacia una verdad trivial) y/o atribuye el fallo a factores externos (autocorrector, alcohol, sueño, déficit o lectura superficial).",
            triggerExamples = listOf(
                "\"nunca debati la cuestión\" / \"nunca mencioné un método\" / \"no dije eso\"",
                "\"obviamente acata si se lo pides\" / \"la inversión inicial es necesaria\" / \"la psicología sirve de mucho\"",
                "\"Marico fué el autocorrector\" / \"tengo 8 cervezas encima y ya son las 4am\""
            ),
            relatedCaseIds = listOf("caso_1", "caso_2", "caso_3", "caso_4")
        ),
        PatternPhase(
            number = 4,
            code = "FASE 04",
            title = "Inversión de carga y tecnicismo",
            subtitle = "Psicologización y fuga hacia arriba",
            description = "Desplaza el debate desde el contenido hacia el supuesto estado mental, emocional o cognitivo del interlocutor, o eleva repentinamente el tecnicismo para restablecer asimetría.",
            triggerExamples = listOf(
                "\"estás a la defensiva\" / \"te arde\" / \"autismo\" / \"ego descontrolado\"",
                "\"complejo de inferioridad perfecto\" / \"prejuicioso\" / \"estás confundido\"",
                "Fuga técnica: ontología, epistemología, teoría de grafos, fotónica, matemáticas discretas"
            ),
            relatedCaseIds = listOf("caso_1", "caso_2", "caso_3", "caso_6")
        ),
        PatternPhase(
            number = 5,
            code = "FASE 05",
            title = "Cierre unilateral o cambio de tema",
            subtitle = "Corte de interacción",
            description = "Corta la interacción apelando a la hora, al baño, a estudiar o declarando que el debate ya terminó sin haber resuelto la contradicción original.",
            triggerExamples = listOf(
                "\"Me tengo que bañar\" (1:08) / \"Voy a dormir\" (3:28)",
                "\"Es que ahí se acaba el debate lol\" (11:10)",
                "\"Entonces bye\" (3:55) / \"Estoy llegando a la universidad\" (10:26)"
            ),
            relatedCaseIds = listOf("caso_3", "caso_4", "caso_5", "caso_6")
        )
    )

    val baileyMotteComparisons: List<BaileyMotteComparison> = listOf(
        BaileyMotteComparison(
            id = "bm_1",
            caseId = "caso_1",
            topic = "Capacidad de debate de la IA",
            baileyClaim = "\"Fácil / La IA no debate / Y aunque se lo pidas / No coje postura aunque tú se la des\"",
            baileyTimestamp = "22/11/26 · 03:51",
            frictionSummary = "Sun confronta la imposibilidad de que no adopte postura si se le ordena en el prompt.",
            motteRetreat = "\"Querer decir 'a menos que se la des' / Obviamente\" · \"acata ipso facto\"",
            motteTimestamp = "22/11/26 · 03:57–03:59",
            excuseOrShield = "Autocorrector, 8 cervezas encima, 4am, enfermedad y no estar en plena consciencia."
        ),
        BaileyMotteComparison(
            id = "bm_2",
            caseId = "caso_2",
            topic = "Ingresos astutos sin capital",
            baileyClaim = "\"Ambas son posibles\" · \"Tampoco menor\" (ante probabilidad con vs. sin inversión) · \"Dije que se podía obtener ingresos astutos con o sin inversión inicial\"",
            baileyTimestamp = "9/4/26 · 02:27–03:33",
            frictionSummary = "Sun expone que sin capital inicial la probabilidad es mínima y pide el método.",
            motteRetreat = "\"La inversión inicial es necesaria\" · \"Nunca mencioné un método\" · \"nunca debati la cuestión\"",
            motteTimestamp = "9/4/26 · 02:28 y 17:45–18:16",
            excuseOrShield = "\"Me divierte volverlo un acertijo\", déficit de concentración/sueño y acusación de tergiversación."
        ),
        BaileyMotteComparison(
            id = "bm_3",
            caseId = "caso_3",
            topic = "Utilidad de la psicología y terapia",
            baileyClaim = "\"Que hay de sólido en un diagnóstico psicológico? 💀 / Es la ciencia que más está en pañales...\" · \"No cambia nada / Solo tendrás una excusa\"",
            baileyTimestamp = "19/1/26 · 00:06–00:20",
            frictionSummary = "Sun señala que lo desanima e intenta convencerlo de que no sirve.",
            motteRetreat = "\"No dije eso / La psicología sirve de mucho\" · \"Claro que tienes que intentarlo / Va a funcionar\"",
            motteTimestamp = "19/1/26 · 00:25 y 01:01–01:04",
            excuseOrShield = "Acota horas después a \"La psicoterapia\" y afirma a N Castillo que siempre apoyó la idea."
        ),
        BaileyMotteComparison(
            id = "bm_4",
            caseId = "caso_4",
            topic = "DSM-5 y Test de Raven",
            baileyClaim = "Compara la evaluación compartida por Sun con el DSM-5, el horóscopo y el MBTI.",
            baileyTimestamp = "19/2/26 · 03:53–03:58",
            frictionSummary = "Sun aclara que el Test de Raven mide razonamiento matricial, no autismo ni DSM-5.",
            motteRetreat = "\"Ni siquiera sé porque respondiste con eso sí no viene al caso\" · \"Misma verga que cualquiera test de IQ\"",
            motteTimestamp = "22/2/26 · 02:05–02:12",
            excuseOrShield = "\"Estaba pasándole por encima a los mensajes que no leí viejos jajsjajsjaj\"."
        ),
        BaileyMotteComparison(
            id = "bm_5",
            caseId = "caso_6",
            topic = "Cosmología y poderes en Persona 3",
            baileyClaim = "\"persona no debe de tener personajes con poderes conceptuales pq no es un universo de powersacling poderoso\"",
            baileyTimestamp = "7/9/26 · 17:39",
            frictionSummary = "N Castillo refuta desde el lore (17:39–17:48) y Armando (+58 414-0352950) le pasa el databook oficial de Nyx (17:52).",
            motteRetreat = "\"Ni siquiera es universal... Literalmente no hace nada... Lo inflaron demasiado conceptualmente\"",
            motteTimestamp = "7/9/26 · 17:54–18:11",
            excuseOrShield = "\"Pero me hicieron perder tiempo investigando su personaje, me tengo que desahogar\"."
        )
    )

    val documentedCases: List<DocumentedCase> = listOf(
        DocumentedCase(
            id = "caso_1",
            code = "CASO 01",
            number = 1,
            title = "La contradicción de la IA, el autocorrector y \"las 8 cervezas\"",
            subtitle = "De \"no coje postura aunque tú se la des\" a \"acata ipso facto\"",
            dateRange = "22/11/26 · 03:51 – 05:09",
            interlocutors = listOf("Sun", "Armando"),
            tactics = listOf(
                RhetoricalTactic.MOTTE_AND_BAILEY,
                RhetoricalTactic.EXCUSAS_EXTERNAS,
                RhetoricalTactic.PATOLOGIZACION,
                RhetoricalTactic.FUGA_TECNICA
            ),
            overview = "Ante la sugerencia de Armando (+58 414-0352950 en el chat) de practicar debates con IA, Ángel afirma categóricamente que la IA no adopta postura aunque se le pida. Tras ser confrontado por Sun, invierte por completo la frase, culpa al autocorrector, suma excusas fisiológicas y desvía hacia etiquetas psicológicas y jerga técnica.",
            sections = listOf(
                CaseSection(
                    id = "c1_s1",
                    heading = "Afirmación inicial y quiebre",
                    summary = "Sentencia absoluta inicial seguida de confrontación directa de Sun (3:55–3:56): \"Ni aunque se lo pidas? ... Prompteas mal / Es como culpar a la calculadora por sacarte mal una fórmula\".",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c1_1",
                            timestamp = "22/11/26 · 03:51",
                            speaker = "Ángel",
                            text = "Fácil / La IA no debate / Y aunque se lo pidas / No coje postura aunque tú se la des / Irónicamente / Entonces no sirve",
                            roleLabel = "Afirmación inicial",
                            category = QuoteCategory.AFIRMACION_INICIAL
                        )
                    )
                ),
                CaseSection(
                    id = "c1_s2",
                    heading = "Giro y reescritura retroactiva",
                    summary = "En dos minutos pasa de negar que adopte postura a presentar lo opuesto como evidente y culpar a Sun por no haberlo inferido.",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c1_2",
                            timestamp = "22/11/26 · 03:57",
                            speaker = "Ángel",
                            text = "Querer decir 'a menos que se la des' / Obviamente",
                            roleLabel = "Inversión de frase",
                            category = QuoteCategory.GIRO_REESCRITURA
                        ),
                        LiteralQuote(
                            id = "q_c1_3",
                            timestamp = "22/11/26 · 03:59",
                            speaker = "Ángel",
                            text = "La IA con que le digas simplemente... te entiende el prompt a la perfección, lo que sucedería es que acata ipso facto.",
                            roleLabel = "Tesis opuesta",
                            category = QuoteCategory.GIRO_REESCRITURA
                        ),
                        LiteralQuote(
                            id = "q_c1_4",
                            timestamp = "22/11/26 · 04:03",
                            speaker = "Ángel",
                            text = "Y te tuve que destacar eso para que lo infirieras en lo que quise decir? Joder / Y todavía lo saca en cara como si fuera yo el que no lo vió desde el principio",
                            roleLabel = "Inversión de culpa",
                            category = QuoteCategory.GIRO_REESCRITURA
                        ),
                        LiteralQuote(
                            id = "q_c1_5",
                            timestamp = "22/11/26 · 04:28",
                            speaker = "Ángel",
                            text = "Que fué literalmente dándote la razón y admitiendo que tuve un error lingüístico",
                            roleLabel = "Reencuadre retrospectivo",
                            category = QuoteCategory.GIRO_REESCRITURA
                        )
                    )
                ),
                CaseSection(
                    id = "c1_s3",
                    heading = "Cadena de excusas e inconsistencia sintáctica",
                    summary = "Como señaló Sun (4:17), la frase original tenía dos conectores coherentes entre sí (\"Y aunque se lo pidas\" + \"No coje postura aunque tú se la des\"), haciendo inverosímil que ambos fueran un error aislado de autocorrector.",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c1_6",
                            timestamp = "22/11/26 · 04:01–04:02",
                            speaker = "Ángel",
                            text = "Quise decir*? / Yes / Autocorrector",
                            roleLabel = "Excusa 1: Autocorrector",
                            category = QuoteCategory.EXCUSA_EXTERNA
                        ),
                        LiteralQuote(
                            id = "q_c1_7",
                            timestamp = "22/11/26 · 04:14",
                            speaker = "Ángel",
                            text = "Marico fué el autocorrector",
                            roleLabel = "Reiteración",
                            category = QuoteCategory.EXCUSA_EXTERNA
                        ),
                        LiteralQuote(
                            id = "q_c1_8",
                            timestamp = "22/11/26 · 04:20",
                            speaker = "Ángel",
                            text = "ya no te sabría decir si fué que se me enredo el lenguaje mentalmente o el autocorrector, pero si pensé en 'a menos' no en 'aunque'",
                            roleLabel = "Duda sobre propia excusa",
                            category = QuoteCategory.CONTRADICCION
                        ),
                        LiteralQuote(
                            id = "q_c1_9",
                            timestamp = "22/11/26 · 04:15–04:16",
                            speaker = "Ángel",
                            text = "La peor parte es que se me olvidó mencionar que tengo 8 cervezas encima y ya son las 4am / Y aún así tengo está agilidad mental / Y enfermo, más bien me deberías de tener paciencia en lugar de atacar tan pateticamente",
                            roleLabel = "Alcohol, hora y enfermedad",
                            category = QuoteCategory.EXCUSA_EXTERNA
                        ),
                        LiteralQuote(
                            id = "q_c1_10",
                            timestamp = "22/11/26 · 04:21",
                            speaker = "Ángel",
                            text = "No estoy en plena consciencia",
                            roleLabel = "Inconsciencia parcial",
                            category = QuoteCategory.EXCUSA_EXTERNA
                        )
                    )
                ),
                CaseSection(
                    id = "c1_s4",
                    heading = "Etiquetas al interlocutor y desvío técnico",
                    summary = "Desplaza el foco hacia supuestos rasgos psicológicos de Sun y luego eleva la conversación a áreas técnicas complejas.",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c1_11",
                            timestamp = "22/11/26 · 04:00–04:27",
                            speaker = "Ángel",
                            text = "prejuicio tan forzado (4:00) · sigues peleando solo (4:04) · Te estás inventando patrones (4:27)",
                            roleLabel = "Descalificación inicial",
                            category = QuoteCategory.ETIQUETA_DESVIO
                        ),
                        LiteralQuote(
                            id = "q_c1_12",
                            timestamp = "22/11/26 · 04:34–04:43",
                            speaker = "Ángel",
                            text = "El autismo te hace predecible (4:34) · Patrón autista, muletillas en otro idioma, la necedad brutal... literalmente te arde... te dió rencor o al menos una disonancia cognitiva (4:43)",
                            roleLabel = "Patologización",
                            category = QuoteCategory.ETIQUETA_DESVIO
                        ),
                        LiteralQuote(
                            id = "q_c1_13",
                            timestamp = "22/11/26 · 04:50–05:06",
                            speaker = "Ángel",
                            text = "No te molestes Papu (4:50) · Te afectó (4:52) · No te molestes pendejo, que lo estás (4:52) · eres bien inmaduro en tu forma de debatir (4:54) · Muy inmadura tú firma de debatir (5:06)",
                            roleLabel = "Atribución emocional",
                            category = QuoteCategory.ETIQUETA_DESVIO
                        ),
                        LiteralQuote(
                            id = "q_c1_14",
                            timestamp = "22/11/26 · 04:45–04:50",
                            speaker = "Ángel",
                            text = "implicaciones ontologicas de la IA · La teoría de grafos y la lógica digital · Fotónica · La información cuántica · Los límites computacionales y su relación termodinámica",
                            roleLabel = "Fuga hacia arriba",
                            category = QuoteCategory.ETIQUETA_DESVIO
                        )
                    )
                )
            ),
            bilateralAnalysis = BilateralAnalysis(
                title = "Contraparte objetiva: reacción y fallas de Sun",
                reactionContext = "Sun ya había respondido desde las 3:52 y refutado el punto central entre 3:55 y 3:57 obligando a Ángel a reescribir su frase. Sin embargo, cuando Ángel no concede el error abiertamente y se burla en 4:02–4:03, Sun entra en una pulseada de desgaste que se extiende hasta las 5:09 AM.",
                flawsAndInfantilizationTraps = listOf(
                    "Arranca la réplica en tono burlón («You're wrong you're just a bad prompter ugu» a las 3:52 y «Escribes dlv» a las 4:02), facilitando que Ángel encuadre el intercambio como ataque personal.",
                    "A las 3:59 escribe «Brotato cree que la IA funciona como lenguaje humano», una formulación imprecisa (los LLM sí operan sobre lenguaje natural) que dio flanco real para que Ángel respondiera «Non sequitur».",
                    "Cae en falsos cierres repetidos: anuncia «Fin del debate / A dormir» (4:20), «el punto está cerrado. A dormir» (4:31, retractado en el mismo minuto con «Noup, deja exporto todos los chats...») y «por hoy ya está, a dormir / Gud naight» (4:41–4:42), pero sigue respondiendo hasta las 5:09 AM, reforzando la narrativa de Ángel de que «te arde».",
                    "Responde a la patologización con psicologización inversa («toqué tu herida» a las 4:38), lo que desvía el foco del fallo lógico verificable hacia intenciones internas difíciles de probar."
                ),
                sunQuotes = listOf(
                    LiteralQuote(
                        id = "q_c1_sun_1",
                        timestamp = "22/11/26 · 03:52–03:59",
                        speaker = "Sun",
                        text = "You're wrong you're just a bad prompter ugu (3:52) · Brotato cree que la IA funciona como lenguaje humano (3:59)",
                        roleLabel = "Tono burlón e imprecisión técnica",
                        category = QuoteCategory.AUTOCRITICA_SUN
                    ),
                    LiteralQuote(
                        id = "q_c1_sun_2",
                        timestamp = "22/11/26 · 04:31–04:38",
                        speaker = "Sun",
                        text = "entonces el punto está cerrado. A dormir / Noup, deja exporto todos los chats y los desgloso (4:31) · toqué tu herida (4:38)",
                        roleLabel = "Falso cierre y psicologización de vuelta",
                        category = QuoteCategory.AUTOCRITICA_SUN
                    )
                ),
                objectiveBalance = "Sun tenía la razón técnica desde las 3:56 (analogía de la calculadora y los dos «aunque» consecutivos). Su falla no fue lógica sino estratégica: seguir buscando una admisión explícita a las 4:30 AM tras haber ganado ya el argumento."
            )
        ),
        DocumentedCase(
            id = "caso_2",
            code = "CASO 02",
            number = 2,
            title = "\"Plata es plata\", \"ingresos astutos\" y \"nunca debatí la cuestión\"",
            subtitle = "Contradicción sobre inversión inicial, ambigüedad y confesión de patrón previo",
            dateRange = "9/4/26 · 02:13 – 21:36",
            interlocutors = listOf("Sun"),
            tactics = listOf(
                RhetoricalTactic.MOTTE_AND_BAILEY,
                RhetoricalTactic.SCHRODINGERS_DOUCHEBAG,
                RhetoricalTactic.EXCUSAS_EXTERNAS,
                RhetoricalTactic.PATOLOGIZACION
            ),
            overview = "Tras burlarse de la frase \"plata es plata\" y defender que obtener ingresos astutos sin capital inicial no tiene menor probabilidad, Ángel se contradice en el mismo minuto y termina negando por la tarde haber debatido la cuestión o mencionado método alguno.",
            sections = listOf(
                CaseSection(
                    id = "c2_s1",
                    heading = "Afirmación inicial y primeras réplicas",
                    summary = "Cuando Sun (2:27) señala que obtener ingresos pasivos/astutos sin inversión inicial tiene probabilidad muy baja, Ángel replica: \"Ambas son posibles\" (2:27) y \"Tampoco menor\" (2:28).",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c2_1",
                            timestamp = "9/4/26 · 02:24",
                            speaker = "Ángel",
                            text = "Y por eso me cago cuando dicen que plata es plata JAJAJAJAJAJAJ / Cuando la idea es buscar plenitud en la independencia maxima del capital que te mantiene esclavo al sistema por pendejo / Hasta lograr superarlo / Que es cuando tienes ingresos astutos y no eres un empleado",
                            roleLabel = "Provocación inicial",
                            category = QuoteCategory.AFIRMACION_INICIAL
                        ),
                        LiteralQuote(
                            id = "q_c2_2",
                            timestamp = "9/4/26 · 02:27–02:28",
                            speaker = "Ángel",
                            text = "Ambas son posibles (2:27) / Tampoco menor (2:28)",
                            roleLabel = "Sostiene igual probabilidad",
                            category = QuoteCategory.AFIRMACION_INICIAL
                        )
                    )
                ),
                CaseSection(
                    id = "c2_s2",
                    heading = "El quiebre y las contradicciones literales",
                    summary = "Oscila entre afirmar que la inversión inicial es necesaria, decir que sí se podía sin inversión y finalmente desconocer por completo haber debatido el tema.",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c2_3",
                            timestamp = "9/4/26 · 02:28",
                            speaker = "Ángel",
                            text = "La inversión inicial es necesaria",
                            roleLabel = "Contradicción inmediata",
                            category = QuoteCategory.CONTRADICCION
                        ),
                        LiteralQuote(
                            id = "q_c2_4",
                            timestamp = "9/4/26 · 03:33",
                            speaker = "Ángel",
                            text = "Lo de la inversión inicial nunca negué que sin ingresos fuera menos probable... Dije que se podía obtener ingresos astutos con o sin inversión inicial",
                            roleLabel = "Reescritura nocturna",
                            category = QuoteCategory.CONTRADICCION
                        ),
                        LiteralQuote(
                            id = "q_c2_5",
                            timestamp = "9/4/26 · 17:50",
                            speaker = "Ángel",
                            text = "Y sigues infiriendo que yo en algún momento me refería a un método ficticio para generar ingresos astutos sin capital inicial 💀💀💀💀",
                            roleLabel = "Negación vespertina",
                            category = QuoteCategory.GIRO_REESCRITURA
                        ),
                        LiteralQuote(
                            id = "q_c2_6",
                            timestamp = "9/4/26 · 17:45–18:35",
                            speaker = "Ángel",
                            text = "Nunca mencioné un método 🙈🙉 (17:45) · nunca hablé de problemas económicos los cuales introdujiste a la fuerza (17:57) · no me refutaste a ningún punto porque nunca debati la cuestión (18:16) · Lo que estamos haciendo técnicamente no es debatir / Porque no estábamos hablando de lo mismo (18:34–18:35)",
                            roleLabel = "Desconocimiento del debate",
                            category = QuoteCategory.GIRO_REESCRITURA
                        )
                    )
                ),
                CaseSection(
                    id = "c2_s3",
                    heading = "Factor Schrödinger’s Douchebag y excusa física",
                    summary = "Entre 18:22 y 18:31 hay mensajes <Multimedia omitido> de Ángel tras los cuales Sun reacciona diciendo \"Ahh, ahora es una frase de chiste, recoge la cuerda\"; en texto visible Ángel responde \"No dije que fuera un chiste / Chiste? 😳\" entre 18:32 y 18:33.",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c2_7",
                            timestamp = "9/4/26 · 02:43–19:29",
                            speaker = "Ángel",
                            text = "Me divierte volverlo un acertijo (2:43) · A este punto ya lo hago para que los demás vean y por mantener activas las neuronas (18:36) · El loco no se da cuenta de que me estoy burlando de su tergiversación, el autismo está sólido (19:29)",
                            roleLabel = "En serio vs. burla",
                            category = QuoteCategory.ETIQUETA_DESVIO
                        ),
                        LiteralQuote(
                            id = "q_c2_8",
                            timestamp = "9/4/26 · 21:31 vs 21:36",
                            speaker = "Ángel",
                            text = "No te ardas, bro, la idea no es pelear (21:31) → Las ofensas no son en broma / Cuando las digo es por razones genuinas (21:36)",
                            roleLabel = "Oscilación en 5 min",
                            category = QuoteCategory.CONTRADICCION
                        ),
                        LiteralQuote(
                            id = "q_c2_9",
                            timestamp = "9/4/26 · 03:39",
                            speaker = "Ángel",
                            text = "Y con poca precisión, pero el aburrimiento me dió sueño y me saco de concentración el déficit / Perdí el prime",
                            roleLabel = "Excusa física/cognitiva",
                            category = QuoteCategory.EXCUSA_EXTERNA
                        )
                    )
                ),
                CaseSection(
                    id = "c2_s4",
                    heading = "Etiquetas usadas y confesión de patrón previo",
                    summary = "Catálogo de descalificaciones durante el intercambio y admisión sobre un debate previo en Instagram.",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c2_10",
                            timestamp = "9/4/26 · 02:18–19:29",
                            speaker = "Ángel",
                            text = "Se nota que no saben (2:18) · necesitas el ejercicio / Busca la falacia (2:32) · prejuicioso (2:41) · eres imbécil (2:45) · Iluso (2:46) · Pendejo men / Te faltó hermenéutica o sintaxis? (2:47) · Solo deja el orgullo (2:48) · sofista (3:33) · mentiroso sin vergüenza (3:34) · el autismo está sólido (19:29)",
                            roleLabel = "Etiquetas acumuladas",
                            category = QuoteCategory.ETIQUETA_DESVIO
                        ),
                        LiteralQuote(
                            id = "q_c2_11",
                            timestamp = "9/4/26 · 02:53",
                            speaker = "Ángel",
                            text = "Eso sí fué porque no le ví caso más allá de ganar credibilidad contigo / Y parece que te importó más de lo que pensé / Así que tuve que seguir",
                            roleLabel = "Confesión patrón previo (IG)",
                            category = QuoteCategory.LUCIDEZ_PARCIAL
                        )
                    )
                )
            ),
            bilateralAnalysis = BilateralAnalysis(
                title = "Contraparte objetiva: reacción y fallas de Sun",
                reactionContext = "Ángel niega una contradicción escrita minutos antes y adopta un tono paternalista de profesor («necesitas el ejercicio», «Eres inteligente, tu puedes / Solo deja el orgullo»). Sun identifica la maniobra, pero acepta competir dentro de ese mismo marco.",
                flawsAndInfantilizationTraps = listOf(
                    "Antes de escalar la discusión, a las 2:19 lanza una broma autodespectiva («El autismo **le echaba culpa al autismo**»); horas después (19:29), Ángel recicla esa misma etiqueta («el autismo está sólido») para invalidarlo.",
                    "Entra en la competencia de estatus y ego en 2:49 («en eso me ganas con big diff») y admite en 2:51 estar «nivelandome a tu manera de ser», bajando al terreno provocador que favorece a Ángel.",
                    "A las 2:55 reconoce lúcidamente «Sí, ciertamente no debería rebajarme en ese sentido», pero al minuto siguiente (2:56–2:57) cae de lleno en la trampa de pedirle validación: «Necesito tu respuesta / Y si no la das, la tomo como una rendición indirecta».",
                    "Por la tarde (18:12–18:16) mezcla un argumento económico correcto con apelación a credenciales («soy estudiante de economía... es obvio que no lo es para alguien que carece de experiencia laboral»), desplazando el debate al estatus personal.",
                    "En 18:31–18:35 atribuye en texto que Ángel admitió «un jodido chiste» tras mensajes multimedia, abriendo la puerta a que Ángel lo niegue literalmente en 18:32."
                ),
                sunQuotes = listOf(
                    LiteralQuote(
                        id = "q_c2_sun_1",
                        timestamp = "9/4/26 · 02:51–02:57",
                        speaker = "Sun",
                        text = "Solo que nivelandome a tu manera de ser (2:51) · Sí, ciertamente no debería rebajarme en ese sentido (2:55) · Necesito tu respuesta / Y si no la das, la tomo como una rendición indirecta (2:56–2:57)",
                        roleLabel = "Lucidez momentánea vs. búsqueda de rendición",
                        category = QuoteCategory.AUTOCRITICA_SUN
                    ),
                    LiteralQuote(
                        id = "q_c2_sun_2",
                        timestamp = "9/4/26 · 18:12–18:16",
                        speaker = "Sun",
                        text = "porque en efecto, soy estudiante de economía, y tus atrocidades de planes son, atrocidades lol (18:12) · es obvio que no lo es para alguien que carece de experiencia laboral (18:16)",
                        roleLabel = "Apelación a credenciales y ataque personal",
                        category = QuoteCategory.AUTOCRITICA_SUN
                    )
                ),
                objectiveBalance = "El planteamiento económico de Sun (requerir ingreso/capital previo antes de ingresos pasivos) era consistente y el de Ángel era contradictorio. Sin embargo, exigir «Necesito tu respuesta» a alguien que declaró estar jugando «para que los demás vean» convirtió la infantilización en un bucle."
            )
        ),
        DocumentedCase(
            id = "caso_3",
            code = "CASO 03",
            number = 3,
            title = "Psicología \"en pañales\", giro de apoyo y discurso paralelo",
            subtitle = "De desestimar el diagnóstico a afirmar a espaldas de Sun que siempre apoyó la terapia",
            dateRange = "18/1/26 · 23:36 – 19/1/26 · 11:52",
            interlocutors = listOf("Sun", "N Castillo"),
            tactics = listOf(
                RhetoricalTactic.MOTTE_AND_BAILEY,
                RhetoricalTactic.PATOLOGIZACION,
                RhetoricalTactic.CIERRE_UNILATERAL
            ),
            overview = "Ángel descarta la solidez del diagnóstico psicológico calificándolo de ciencia en pañales y mera excusa. Cuando Sun señala la hostilidad, gira hacia \"la psicología sirve de mucho\", corta por la hora/baño (1:08) y media hora después le asegura a N Castillo que siempre apoyó que Sun fuera al psicólogo.",
            sections = listOf(
                CaseSection(
                    id = "c3_s1",
                    heading = "Afirmaciones iniciales",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c3_1",
                            timestamp = "18/1/26 · 23:37–23:40",
                            speaker = "Ángel",
                            text = "Y la fé no te da voluntad / Ya que tú libertad no es tuya / Que asco... Si envidias eso / Tienes dos problemas",
                            roleLabel = "Ataque inicial",
                            category = QuoteCategory.AFIRMACION_INICIAL
                        ),
                        LiteralQuote(
                            id = "q_c3_2",
                            timestamp = "19/1/26 · 00:06",
                            speaker = "Ángel",
                            text = "No cambia nada / Solo tendrás una excusa",
                            roleLabel = "Desestimación",
                            category = QuoteCategory.AFIRMACION_INICIAL
                        ),
                        LiteralQuote(
                            id = "q_c3_3",
                            timestamp = "19/1/26 · 00:19–00:20",
                            speaker = "Ángel",
                            text = "Que hay de sólido en un diagnóstico psicológico? 💀 / Es la ciencia que más está en pañales... Está más cerca de la filosofía que de una ciencia",
                            roleLabel = "Descalificación disciplinar",
                            category = QuoteCategory.AFIRMACION_INICIAL
                        ),
                        LiteralQuote(
                            id = "q_c3_4",
                            timestamp = "19/1/26 · 00:31–00:33",
                            speaker = "Ángel",
                            text = "No persuade / Pero men como que convencer / Increíble / Ni ningún psicólogo en el mundo",
                            roleLabel = "Escepticismo absoluto",
                            category = QuoteCategory.AFIRMACION_INICIAL
                        )
                    )
                ),
                CaseSection(
                    id = "c3_s2",
                    heading = "Giros en tiempo real y a la mañana siguiente",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c3_5",
                            timestamp = "19/1/26 · 00:25",
                            speaker = "Ángel",
                            text = "No dije eso / La psicología sirve de mucho / Pero no es la magia que buscas",
                            roleLabel = "Repliegue Motte",
                            category = QuoteCategory.GIRO_REESCRITURA
                        ),
                        LiteralQuote(
                            id = "q_c3_6",
                            timestamp = "19/1/26 · 00:43",
                            speaker = "Ángel",
                            text = "Adivina para que vas al psicólogo? / Para salir de esa ignorancia",
                            roleLabel = "Inversión de motivo",
                            category = QuoteCategory.ETIQUETA_DESVIO
                        ),
                        LiteralQuote(
                            id = "q_c3_7",
                            timestamp = "19/1/26 · 01:01–01:04",
                            speaker = "Ángel",
                            text = "Claro que tienes que intentarlo / Y no es malo salir con ayuda / Va a funcionar",
                            roleLabel = "Giro hacia apoyo",
                            category = QuoteCategory.GIRO_REESCRITURA
                        ),
                        LiteralQuote(
                            id = "q_c3_8",
                            timestamp = "19/1/26 · 01:36–01:38",
                            speaker = "Ángel (a N Castillo)",
                            text = "@N Castillo yo le dije que no lo hiciera? 💀 / Es más, no apoyé la idea de que lo haga? ... Le dije muchas veces / Antes de ese mensaje que lo hiciera",
                            roleLabel = "Discurso paralelo",
                            category = QuoteCategory.CONTRADICCION
                        ),
                        LiteralQuote(
                            id = "q_c3_9",
                            timestamp = "19/1/26 · 10:29 y 11:38–11:49",
                            speaker = "Ángel",
                            text = "Psicología no tanto / La psicoterapia / La terapia pues (10:29) · No quería que te afectara / Sino que amplíes tú panorama... También quería que empezarás a escuchar a solitario (11:38–11:49)",
                            roleLabel = "Acotación y reescritura de intención",
                            category = QuoteCategory.GIRO_REESCRITURA
                        )
                    )
                ),
                CaseSection(
                    id = "c3_s3",
                    heading = "Etiquetas directas, comentarios a terceros y excusas de cierre",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c3_10",
                            timestamp = "19/1/26 · 00:22–10:27",
                            speaker = "Ángel (a Sun)",
                            text = "apoyar tú sesgo... desde el cientificismo, que es otra religión más (0:22) · Prefieres permanecer en ignorancia (0:23) · no ves autoridad en mí... tú sesgo no lo recibe (0:48) · No estoy debatiendo / Te quiero ayudar / Estás digridiendo... Estás a la defensiva (0:59–1:01) · Solo estás confundido (10:27)",
                            roleLabel = "Etiquetas directas",
                            category = QuoteCategory.ETIQUETA_DESVIO
                        ),
                        LiteralQuote(
                            id = "q_c3_11",
                            timestamp = "19/1/26 · 01:39–01:52",
                            speaker = "Ángel (con N Castillo)",
                            text = "estaba predispuesto y por eso la defensiva · A sunny es difícil hacerlo entrar en razón... Demasiados sesgos y presupuestos · pone a la gente con ego descontrolado a la defensiva · siempre ha sido así de pedante... Eso es culpa del ego y la ignorancia",
                            roleLabel = "Psicologización a espaldas",
                            category = QuoteCategory.ETIQUETA_DESVIO
                        ),
                        LiteralQuote(
                            id = "q_c3_12",
                            timestamp = "19/1/26 · 01:03–11:37",
                            speaker = "Ángel",
                            text = "La hora (1:03) · Me tengo que bañar (1:08) · Estoy llegando a la universidad (10:26) · Cuando llegue a la casa seguimos (11:37)",
                            roleLabel = "Excusas de cierre",
                            category = QuoteCategory.EXCUSA_EXTERNA
                        )
                    )
                )
            ),
            bilateralAnalysis = BilateralAnalysis(
                title = "Contraparte objetiva: reacción y fallas de Sun",
                reactionContext = "Sun atravesaba un momento de agotamiento real (insatisfacción académica, conflicto familiar, 3 horas para despertar) y buscaba respaldo práctico para ir a psicoterapia, no un debate filosófico sobre si la psicología es ciencia.",
                flawsAndInfantilizationTraps = listOf(
                    "Al desahogarse, formula su propio proceso con términos vulnerables y ambivalentes: «Una \"excusa\" / Una \"justificación\"» (0:19), «Quizá quiero permanecer en ignorancia en ese aspecto» (0:22) y «Es literalmente un placebo autogenerado» (0:25). Eso le entregó a Ángel literalmente el léxico con el que luego lo infantilizó («Solo tendrás una excusa», «Prefieres permanecer en ignorancia»).",
                    "Permanece defendiendo su derecho a probar terapia hasta la 1:07 AM cuando tenía que despertar en 3 horas (1:03–1:07), buscando que Ángel —quien le decía «no ves autoridad en mí»— validara o diera permiso a una decisión que ya estaba tomada.",
                    "Acepta debatir su salud mental bajo el marco intelectualizador del otro (citando «qualia» en 0:41 y «lógica paraconsistente» en 1:07) en lugar de poner un límite sencillo como «no estoy pidiendo aprobación ni debatiendo esto ahora»."
                ),
                sunQuotes = listOf(
                    LiteralQuote(
                        id = "q_c3_sun_1",
                        timestamp = "19/1/26 · 00:19–00:25",
                        speaker = "Sun",
                        text = "Necesito eso / Para actuar en cualquier cosa / Una \"excusa\" / Una \"justificación\" (0:19) · Quizá quiero permanecer en ignorancia en ese aspecto (0:22) · Es literalmente un placebo autogenerado (0:25)",
                        roleLabel = "Marco léxico entregado al oponente",
                        category = QuoteCategory.AUTOCRITICA_SUN
                    ),
                    LiteralQuote(
                        id = "q_c3_sun_2",
                        timestamp = "19/1/26 · 01:05–01:07",
                        speaker = "Sun",
                        text = "te digo que me dejes intentar esta opción antes de arrastrarme a la opción menos esperanzadora y pues, continúas (1:05) · Tengo que despertar en / 3 horas (1:07)",
                        roleLabel = "Desgaste nocturno buscando tregua",
                        category = QuoteCategory.AUTOCRITICA_SUN
                    )
                ),
                objectiveBalance = "La reacción de Sun es humanamente comprensible: pedir espacio para una opción terapéutica cuando te la descalifican en crisis. Su punto vulnerable fue pedir permiso implícito y autocalificarse como «excusa/ignorancia» frente a un interlocutor que convierte cualquier fisura verbal en jerarquía intelectual."
            )
        ),
        DocumentedCase(
            id = "caso_4",
            code = "CASO 04",
            number = 4,
            title = "El DSM-5, el test de Raven y \"pasarle por encima a los mensajes\"",
            subtitle = "Error técnico no concedido, desplazamiento del blanco y cierre abrupto",
            dateRange = "18/2/26 – 23/2/26",
            interlocutors = listOf("Sun"),
            tactics = listOf(
                RhetoricalTactic.EXCUSAS_EXTERNAS,
                RhetoricalTactic.MOTTE_AND_BAILEY,
                RhetoricalTactic.CIERRE_UNILATERAL
            ),
            overview = "Ante las capturas de Sun sobre su evaluación, Ángel ridiculiza el DSM-5 comparándolo con el horóscopo. Cuando Sun aclara que la prueba de Raven son matrices de razonamiento y no autismo ni DSM-5, Ángel desestima la corrección, cambia el blanco hacia el currículum y la madrastra, y finalmente admite haber leído por encima.",
            sections = listOf(
                CaseSection(
                    id = "c4_s1",
                    heading = "Afirmación inicial y corrección técnica",
                    summary = "Sun (19/2 8:00) aclara que la prueba de Raven no es de autismo ni está vinculada al DSM-5, sino que son matrices progresivas de razonamiento.",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c4_1",
                            timestamp = "19/2/26 · 03:53–03:58",
                            speaker = "Ángel",
                            text = "El dsm 5 me causa mucha gracia definitivamente / La peor parte es que ahora tienes con que justificar tú terquedad, dandote una razón *\"más grande\"* para que trabajes en tú autocrítica... Hace lo mismo con eso que lo que hacen los astrólogos con el horóscopo / O la gente del mbti",
                            roleLabel = "Afirmación inicial",
                            category = QuoteCategory.AFIRMACION_INICIAL
                        )
                    )
                ),
                CaseSection(
                    id = "c4_s2",
                    heading = "Evasión, cambio de blanco y admisión de descuido",
                    summary = "Cuando Sun (23/2 7:29) aclara que jamás lo pondría en un currículum, Ángel cambia el blanco a la madrastra y luego admite que no leyó bien.",
                    quotes = listOf(
                        LiteralQuote(
                            id = "q_c4_2",
                            timestamp = "22/2/26 · 02:00–02:12",
                            speaker = "Ángel",
                            text = "no el hecho de tener un papel para el currículum que te ayude a justificar 'dificultad social', eso sí es una mierda bro (2:00) · Ni siquiera sé porque respondiste con eso sí no viene al caso y tampoco lo mencioné (2:05) · Misma verga que cualquiera test de IQ (2:12)",
                            roleLabel = "Evasión y reemplazo",
                            category = QuoteCategory.GIRO_REESCRITURA
                        ),
                        LiteralQuote(
                            id = "q_c4_3",
                            timestamp = "23/2/26 · 10:21",
                            speaker = "Ángel",
                            text = "Dijiste que era para tu madrastra también",
                            roleLabel = "Cambio de blanco",
                            category = QuoteCategory.ETIQUETA_DESVIO
                        ),
                        LiteralQuote(
                            id = "q_c4_4",
                            timestamp = "23/2/26 · 11:09",
                            speaker = "Ángel",
                            text = "Estaba pasándole por encima a los mensajes que no leí viejos jajsjajsjaj / Pero ya / Mucho / Bueno ya los había dicho, voy a hacer como que lo tenías en mente desde el principio",
                            roleLabel = "Admisión de lectura superficial",
                            category = QuoteCategory.EXCUSA_EXTERNA
                        ),
                        LiteralQuote(
                            id = "q_c4_5",
                            timestamp = "23/2/26 · 11:10",
                            speaker = "Ángel",
                            text = "Es que ahí se acaba el debate lol",
                            roleLabel = "Cierre unilateral (cambia a geometría)",
                            category = QuoteCategory.ETIQUETA_DESVIO
                        )
                    )
                )
            ),
            bilateralAnalysis = BilateralAnalysis(
                title = "Contraparte objetiva: reacción y fallas de Sun",
                reactionContext = "Tras compartir capturas de resultados reales, Ángel compara la evaluación con el horóscopo/MBTI y acusa a Sun de usar el diagnóstico para justificar «terquedad». Sun responde de forma técnica y sobria al inicio (19/2 7:53–8:00), diferenciando DSM-5 de Matrices Progresivas de Raven.",
                flawsAndInfantilizationTraps = listOf(
                    "Cuando Ángel cambia el blanco hacia el currículum y la madrastra el 22–23/2, Sun vuelve a dar explicaciones detalladas a pesar de haber dicho que estaba en detox de redes y sin humor para responder (22/2 · 10:03).",
                    "El 23/2 a las 7:37 describe su propia evaluación médica entre comillas como «cumplir mi \"capricho\" de obtener un diagnóstico formal», adoptando parcialmente el encuadre minimizador de Ángel en vez de mantener el límite neutro."
                ),
                sunQuotes = listOf(
                    LiteralQuote(
                        id = "q_c4_sun_1",
                        timestamp = "23/2/26 · 07:37",
                        speaker = "Sun",
                        text = "cumplir mi \"capricho\" de obtener un diagnóstico formal no implica necesariamente quedarme estancado y no progresar",
                        roleLabel = "Adopción defensiva del término ajeno",
                        category = QuoteCategory.AUTOCRITICA_SUN
                    )
                ),
                objectiveBalance = "Este es uno de los casos donde Sun mantuvo mejor templanza y precisión técnica (19/2 8:00). Aun así, seguir justificándose días después ante cambios de blanco muestra cómo la expectativa de ser comprendido por Ángel reabre debates ya resueltos."
            )
        ),
        DocumentedCase(
            id = "caso_5",
            code = "CASO 05",
            number = 5,
            title = "Otros cruces breves con Sun (mismo patrón en menor escala)",
            subtitle = "5 intercambios entre enero y noviembre de 2026 con idéntica estructura de giro y cierre",
            dateRange = "6/1/26 – 14/11/26",
            interlocutors = listOf("Sun"),
            tactics = listOf(
                RhetoricalTactic.MOTTE_AND_BAILEY,
                RhetoricalTactic.FUGA_TECNICA,
                RhetoricalTactic.CIERRE_UNILATERAL
            ),
            overview = "Además de los cuatro debates extensos, al menos cinco cruces breves con Sun a lo largo de 2026 reproducen en escala compacta la secuencia de sentencia tajante, repliegue/desvío técnico o semántico y cierre unilateral.",
            briefExchanges = listOf(
                BriefExchange(
                    id = "b_1",
                    date = "6/1/26",
                    timeRange = "02:51–03:28",
                    trigger = "Poesía, IA y concepto de arte.",
                    initialClaim = "\"no es el artista de la obra... la pista separada de la letra no contaría como arte\"",
                    pivotOrExcuse = "Desvía (\"estás guiando el tema a otro ámbito\"), redefine ontología/epistemología, dice \"Voy a dormir\" y diagnostica a Sun (\"estás en ese abismo\").",
                    closure = "Envía tema de Solitario (\"analiza bien\")."
                ),
                BriefExchange(
                    id = "b_2",
                    date = "20/1/26",
                    timeRange = "09:36–10:27",
                    trigger = "Rinitis obstructiva de Sun.",
                    initialClaim = "\"Entonces tú roncas / Ajuro\" (9:36); \"Deberías de roncar\" (10:10).",
                    pivotOrExcuse = "Ante la negativa, cambia a \"Capaz no hay tanta obstrucción\" y \"Hay otra opción / Que dormido respires por la boca\" (10:14–10:15).",
                    closure = "Responde \"Ajuro 💀\" (10:23) y cambia de tema a la universidad (10:27)."
                ),
                BriefExchange(
                    id = "b_3",
                    date = "25/4/26",
                    timeRange = "16:21–17:48",
                    trigger = "Libro de epistemología en inglés.",
                    initialClaim = "\"Cómo todo libro de filosofía\" (16:31).",
                    pivotOrExcuse = "Cuando Sun señala que un libro en español no mejora el inglés, apela a \"Obviamente\", lo acusa de \"razonas a medias a propósito\" y \"hombre de paja\" (17:30–17:31), y remata: \"Que es más absurdo tu comentario\" (17:48).",
                    closure = "Cambia a preguntar qué leyó del libro."
                ),
                BriefExchange(
                    id = "b_4",
                    date = "5/6/26",
                    timeRange = "12:14–12:24",
                    trigger = "Firmas y anonimato en el canal.",
                    initialClaim = "\"Eso sigue siendo anónimo\" / \"Mismo significado, diferente semántica\" (12:19–12:20).",
                    pivotOrExcuse = "Tras Sun distinguir ausencia absoluta vs. gradualidad, exige redefinición formal (\"El anonimato es binario, no gradual... a menos que te refieras a otro concepto que tú tendrías que definir con parámetros específicos\" 12:21–12:23).",
                    closure = "Sun concede la distinción práctica y se va."
                ),
                BriefExchange(
                    id = "b_5",
                    date = "14/11/26",
                    timeRange = "22:06–22:26",
                    trigger = "Perfil «intento de emo» de ~Lønux (comentado por Armando, +58 414-0352950).",
                    initialClaim = "\"Nah\" / \"No hay que ser emo para ponerse esos perfiles\" (22:06–22:10).",
                    pivotOrExcuse = "Descalifica la réplica de Sun como \"Fútil\" (22:25).",
                    closure = "Sun responde irónicamente \"okjuez tienestoda la razón y te cabe derecho\" (22:26)."
                )
            ),
            bilateralAnalysis = BilateralAnalysis(
                title = "Contraste positivo: cuándo NO cayó en el bucle",
                reactionContext = "Estos cruces breves permiten comparar el resultado cuando Sun corta temprano frente a cuando busca convencer.",
                flawsAndInfantilizationTraps = listOf(
                    "En el episodio de 5/6/26 (12:24), Sun distingue lo binario de lo gradual en la práctica («En la práctica real claro, existe lo binario... pero aún más prácticos, hay niveles de anonimato») y se retira sin exigir concesión.",
                    "En 14/11/26 (22:26), tras recibir un seco «Fútil», cierra con un único «okjuez tienestoda la razón y te cabe derecho» sin gastar madrugada en probar el punto; allí el patrón de Ángel no logra escalar."
                ),
                sunQuotes = listOf(
                    LiteralQuote(
                        id = "q_c5_sun_1",
                        timestamp = "5/6/26 · 12:24",
                        speaker = "Sun",
                        text = "En la práctica real claro, existe lo binario, anónimo o no anónimo, pero aún más prácticos, hay niveles de anonimato y a eso me refiero",
                        roleLabel = "Cierre práctico sin persecución",
                        category = QuoteCategory.AUTOCRITICA_SUN
                    )
                ),
                objectiveBalance = "La comparación directa con los Casos 1 y 2 demuestra que la escalada depende de dos partes: cuando Sun expone su criterio práctico y corta con humor seco («okjuez tienestoda la razón...»), la infantilización pierde tracción."
            )
        ),
        DocumentedCase(
            id = "caso_6",
            code = "CASO 06",
            number = 6,
            title = "Evidencia del mismo patrón con otros integrantes del grupo",
            subtitle = "7 episodios con Pablo Coronel, MariaJosé, Armando, N Castillo, Tarek y Daniel",
            dateRange = "27/3/26 – 17/11/26",
            interlocutors = listOf("Pablo Coronel", "MariaJosé", "Armando", "N Castillo", "Tarek", "Daniel"),
            tactics = listOf(
                RhetoricalTactic.MOTTE_AND_BAILEY,
                RhetoricalTactic.PATOLOGIZACION,
                RhetoricalTactic.FUGA_TECNICA,
                RhetoricalTactic.CIERRE_UNILATERAL
            ),
            overview = "La conducta no es exclusiva hacia Sun: el registro documental muestra exactamente las mismas maniobras de acotación sobre la marcha, invención estadística ad hoc, patologización, apelación a \"es obvio\" y cierre unilateral frente a otros seis participantes (en el chat original, Armando figura como +58 414-0352950 y Daniel como +58 412-5921835).",
            groupCases = listOf(
                GroupCase(
                    id = "g_1",
                    number = 1,
                    interlocutor = "Pablo Coronel",
                    topic = "Ecuaciones diferenciales",
                    date = "27/3 – 29/3/26",
                    initialClaim = "\"por algo nadie se saca ecuaciones diferenciales explicando el mundo\" (27/3 15:25) y \"Con eso solo puedes modelar-describir movimiento\" (29/3 3:02).",
                    pivotOrExcuse = "Cuando Pablo distingue ordinarias vs. parciales (28/3 16:18) y cita ecuaciones de onda, temperatura y Schrödinger (29/3 3:31–3:32), Ángel se corrige sobre la marcha, acota a \"Cambio / Continuo\", minimiza los ejemplos, introduce matemáticas discretas/lógica booleana/geometría diferencial y al no poder refutar el punto de la singularidad responde \"No te hagas que sabes a lo que me refiero\" antes de cerrar.",
                    quotes = listOf(
                        LiteralQuote("qg_1", "29/3 · 03:06–03:35", "Ángel", "O el cambio, siendo más estrictos (3:06) · Cambio / Continuo (3:35)", "Acotación sobre la marcha", QuoteCategory.GIRO_REESCRITURA),
                        LiteralQuote("qg_2", "29/3 · 03:33–03:55", "Ángel", "Mano solo estás proporcionando datos que se saben (3:33) · No te hagas que sabes a lo que me refiero (3:50) · Entonces bye (3:55)", "Minimización y cierre", QuoteCategory.ETIQUETA_DESVIO)
                    )
                ),
                GroupCase(
                    id = "g_2",
                    number = 2,
                    interlocutor = "MariaJosé (MJ)",
                    topic = "Escuchar una canción completa",
                    date = "5/4/26",
                    initialClaim = "\"Te faltó escucharla completa\" (22:03).",
                    pivotOrExcuse = "Cuando MJ responde que sí la escuchó completa (22:06), Ángel recalcula el ataque con una estadística inventada al vuelo.",
                    quotes = listOf(
                        LiteralQuote("qg_3", "5/4 · 23:03–23:05", "Ángel", "De hecho, fue reacción de obvia timada / Estoy 90% seguro de que no la escucho completa... El 10% Es de la baja probabilidad de que la haya escuchado antes de que lo recordaras / Así de easyzzz", "Estadística ad hoc", QuoteCategory.ETIQUETA_DESVIO)
                    )
                ),
                GroupCase(
                    id = "g_3",
                    number = 3,
                    interlocutor = "N Castillo, Armando y Daniel",
                    topic = "Nyx en Persona 3",
                    date = "7/9/26",
                    initialClaim = "\"persona no debe de tener personajes con poderes conceptuales pq no es un universo de powersacling poderoso / Tiene una cosmología simple\" (17:39).",
                    pivotOrExcuse = "Primero N Castillo lo refuta desde el lore (17:39–17:48) y Armando (+58 414-0352950) le pasa la traducción del databook oficial (17:52). Tras leerla, Ángel minimiza la escala de Nyx (17:54–17:58); cuando Armando y Daniel (+58 412-5921835) cruzan a Makoto/Dante (18:09–18:10), pega un análisis comparativo con Vergil (18:10) y se queja de haber perdido tiempo (18:11).",
                    quotes = listOf(
                        LiteralQuote("qg_4", "7/9 · 17:54–18:11", "Ángel", "Ni siquiera es universal... Literalmente no hace nada... Lo inflaron demasiado conceptualmente, me decepcionó incluso · Pero me hicieron perder tiempo investigando su personaje, me tengo que desahogar", "Minimización y queja de tiempo", QuoteCategory.GIRO_REESCRITURA)
                    )
                ),
                GroupCase(
                    id = "g_4",
                    number = 4,
                    interlocutor = "Armando (+58 414-0352950)",
                    topic = "Humor objetivo, pobreza y \"complejo de inferioridad\"",
                    date = "10/9/26",
                    initialClaim = "\"el humor es mejor mientras más inteligente / Totalmente objetivo\" (16:58).",
                    pivotOrExcuse = "En el mismo bloque (16:59–17:00) matiza (\"depende de la narrativa mental que te armes\"). Sobre la pobreza llama a Armando \"muy pollo en el tema como para entenderlo\" (17:05), y cuando Armando se repliega diciendo que mejor se calla, Ángel lo patologiza.",
                    quotes = listOf(
                        LiteralQuote("qg_5", "10/9 · 17:10–17:11", "Ángel", "Eres buenísimo teniendo complejo de inferioridad / Es un mecanismo psicológico común... Complejo de inferioridad perfecto", "Patologización directa", QuoteCategory.ETIQUETA_DESVIO)
                    )
                ),
                GroupCase(
                    id = "g_5",
                    number = 5,
                    interlocutor = "Pablo Coronel",
                    topic = "Libro de física cuántica",
                    date = "11/11/26",
                    initialClaim = "Enumera \"álgebra, calculo, geometría y todas las físicas\" como requisitos (19:13).",
                    pivotOrExcuse = "Cuando Pablo menciona que también lleva probabilidad (\"Marico WTF\"), Ángel —que no la había mencionado— responde como si fuera evidente (19:16), a lo que Pablo contesta \"Pa ti todo es obvio mamaguevo\" (19:17), mientras Ángel desvía pidiendo las citas famosas de la física cuántica.",
                    quotes = listOf(
                        LiteralQuote("qg_6", "11/11 · 19:16", "Ángel", "Bro 🤡 / Cómo que wtf / Es obvio 😭", "Escudo de obviedad", QuoteCategory.GIRO_REESCRITURA)
                    )
                ),
                GroupCase(
                    id = "g_6",
                    number = 6,
                    interlocutor = "N Castillo y Tarek",
                    topic = "Bandwagonning vs. ad populum / Amar el cálculo",
                    date = "16/11/26",
                    initialClaim = "En la tarde (16:06–16:07) afirma a N Castillo que en Latinoamérica nadie usa Bandwagonning; en la noche (22:33) sostiene ante Tarek que hubo matemáticos que \"vivieron y murieron por el cálculo\".",
                    pivotOrExcuse = "A N Castillo le declara \"Infundado\" y \"Non sequitur\" el argumento de que «bandwagonning» es más fácil/cómodo de decir (16:08–16:09). Con Tarek se repliega en el mismo bloque (22:33) e invalida su definición (22:40).",
                    quotes = listOf(
                        LiteralQuote("qg_7", "16/11 · 22:33", "Ángel (a Tarek)", "Y de todas formas es otro debate porque yo siempre hablé de gustar", "Cambio retrospectivo de tesis", QuoteCategory.GIRO_REESCRITURA)
                    )
                ),
                GroupCase(
                    id = "g_7",
                    number = 7,
                    interlocutor = "Daniel (+58 412-5921835)",
                    topic = "Si Hitler fue \"pintor\"",
                    date = "17/11/26",
                    initialClaim = "Ante el desacuerdo reprende la escritura (17:57): \"Entonces escribe bien mmgv\".",
                    pivotOrExcuse = "Cuando Daniel escribe \"Muy tarde adios\" y abandona el grupo (18:31), Ángel lo etiqueta un minuto después (18:32).",
                    quotes = listOf(
                        LiteralQuote("qg_8", "17/11 · 18:32", "Ángel", "Cobarde jajajaja", "Etiqueta al salir", QuoteCategory.ETIQUETA_DESVIO)
                    )
                )
            )
        )
    )

    val psychMechanisms: List<PsychMechanism> = listOf(
        PsychMechanism(
            id = "mec_a",
            letter = "A",
            title = "Identidad construida sobre la infalibilidad intelectual",
            tagline = "\"El árbitro epistémico\"",
            summary = "Ángel se presenta explícitamente como alguien por encima de los sesgos comunes. Cuando alguien construye su autoestima y estatus grupal sobre ser quien siempre entiende el panorama completo, cometer un error en público se vive como una amenaza identitaria y no como una simple equivocación.",
            keyQuote = "\"leo mi propio ego y lo manejo como quiero, difícilmente caigo en sesgos y falacias también por mi metacognición constante\"",
            keyQuoteTimestamp = "22/11/26 · 04:35",
            points = listOf(
                "\"Tengo honestidad intelectual y pensamientos critico\" (19/1 · 09:59).",
                "\"Cuando me equivoco lo admito enseguida\" (9/4 · 02:50).",
                "Pablo Coronel resume esta percepción grupal el 11/11 (19:17): \"Pa ti todo es obvio mamaguevo\".",
                "De esa amenaza identitaria nace la urgencia de reescribir lo dicho para preservar una imagen de coherencia."
            ),
            relatedCaseIds = listOf("caso_1", "caso_2", "caso_6")
        ),
        PsychMechanism(
            id = "mec_b",
            letter = "B",
            title = "Dinámica Motte-and-Bailey + Schrödinger’s Douchebag",
            tagline = "Repliegue a lo obvio y comodín de ambigüedad",
            summary = "Combina dos estrategias: lanza afirmaciones extremas (Bailey), retrocede a verdades indiscutibles cuando hay fricción (Motte), y si el repliegue es cuestionado vuelve difuso el marco entre hablar en serio, hacer un acertijo o burlarse.",
            keyQuote = "\"nunca debati la cuestión\" / \"me divierte volverlo un acertijo\" / \"me estoy burlando\"",
            keyQuoteTimestamp = "9/4/26 · 02:43–19:29",
            points = listOf(
                "Fase Bailey (territorio arriesgado): \"la IA no debate... no coje postura aunque tú se la des\", \"me cago cuando dicen que plata es plata\", \"la ciencia que más está en pañales\".",
                "Fase Motte (fortaleza segura): \"claro que acata si se lo pides\", \"nunca negué que la inversión inicial sea necesaria\", \"la psicología sirve de mucho\".",
                "Comodín Schrödinger: si convence es una \"cátedra\"; si falla, \"no estaba debatiendo en serio\" o \"estaba borracho/dormido/leyendo por encima\"."
            ),
            relatedCaseIds = listOf("caso_1", "caso_2", "caso_3")
        ),
        PsychMechanism(
            id = "mec_c",
            letter = "C",
            title = "Mecanismo de inversión de carga y psicologización del oponente",
            tagline = "Blindaje del ego mediante inversión del foco",
            summary = "Una constante transversal es patologizar o psicologizar la réplica del otro para invalidarla sin responder al fondo del argumento.",
            keyQuote = "\"El autismo te hace predecible\" / \"complejo de inferioridad perfecto\"",
            keyQuoteTimestamp = "22/11/26 · 04:34 y 10/9/26 · 17:11",
            points = listOf(
                "A Sun le atribuye \"autismo que te hace predecible\", \"sesgos\", \"ignorancia\", \"estar a la defensiva\", \"rencor\", \"disonancia cognitiva\", \"ego descontrolado\" y que \"te arde\".",
                "A Armando le atribuye un \"complejo de inferioridad perfecto\".",
                "A Pablo le dice \"No te hagas que sabes a lo que me refiero\" y a Daniel \"Cobarde\".",
                "Trampa comunicativa: si el otro responde con calma, \"se te nota que estás molesto\"; si responde con fuerza, queda \"demostrado\" que está ardido."
            ),
            relatedCaseIds = listOf("caso_1", "caso_3", "caso_6")
        ),
        PsychMechanism(
            id = "mec_d",
            letter = "D",
            title = "Doble estándar en la atribución de errores",
            tagline = "Asimetría de rigor: forma propia vs. capacidad ajena",
            summary = "Exige precisión absoluta en sintaxis, semántica y lógica formal a sus interlocutores, mientras solicita indulgencia fisiológica o técnica ante sus propias contradicciones.",
            keyQuote = "\"Te faltó hermenéutica o sintaxis?\" vs. \"tengo 8 cervezas encima... autocorrector\"",
            keyQuoteTimestamp = "9/4/26 · 02:47 y 22/11/26 · 04:14–04:15",
            points = listOf(
                "Hacia los demás: \"Non sequitur\", \"Infundado\", \"Te faltó hermenéutica o sintaxis?\", \"Entonces escribe bien mmgv\".",
                "Hacia sí mismo: \"autocorrector\", \"8 cervezas encima\", \"4am\", \"enfermo\", \"déficit\", \"me tomé una pastilla para dormir\", \"estaba pasándole por encima a los mensajes\".",
                "Regla implícita: su error siempre es accidental (\"de forma\"); el error del otro siempre es estructural (\"de capacidad o ego\")."
            ),
            relatedCaseIds = listOf("caso_1", "caso_2", "caso_4", "caso_6")
        ),
        PsychMechanism(
            id = "mec_e",
            letter = "E",
            title = "Escalamiento de abstracción (\"Fuga hacia arriba\")",
            tagline = "Restablecer asimetría mediante jerga especializada",
            summary = "Cuando pierde terreno en una discusión práctica o concreta (cómo conseguir capital, o cómo funciona un prompt en una IA), cambia la conversación hacia conceptos más abstractos o disciplinares.",
            keyQuote = "\"implicaciones ontologicas de la IA\" · \"Fotónica\" · \"teoría de grafos\"",
            keyQuoteTimestamp = "22/11/26 · 04:45–04:50",
            points = listOf(
                "Introduce términos como epistemología, ontología, teoría de grafos, fotónica, matemáticas discretas o lógica modal/metalógica.",
                "Restablece una asimetría percibida de conocimiento sin haber resuelto la contradicción práctica inicial."
            ),
            relatedCaseIds = listOf("caso_1", "caso_5", "caso_6")
        ),
        PsychMechanism(
            id = "mec_f",
            letter = "F",
            title = "Lo que Ángel sí reconoce de sí mismo",
            tagline = "Puntos de lucidez parcial en el registro",
            summary = "Para que el análisis sea completo y honesto, el propio chat contiene momentos donde Ángel admite parte de su mecanismo, aunque sin llegar a frenarlo en el momento del roce.",
            keyQuote = "\"solo estaré esperando algo más que tendré que rebatir y explicar como si tuviera que demostrármelo a mi mismo\"",
            keyQuoteTimestamp = "22/11/26 · 05:06",
            points = listOf(
                "19/1 · 09:58 — \"Yo si soy pedante\"",
                "9/4 · 02:53 — \"Eso sí fué porque no le ví caso más allá de ganar credibilidad contigo\"",
                "22/11 · 05:03 — \"Claro si te he dado razones para pensar cosas malas, también por eso no me sorprende en lo absoluto\"",
                "22/11 · 05:06 — \"solo estaré esperando algo más que tendré que rebatir y explicar como si tuviera que demostrármelo a mi mismo\""
            ),
            relatedCaseIds = listOf("caso_1", "caso_2", "caso_3")
        )
    )

    val evidenceBank: List<EvidenceEntry> = listOf(
        EvidenceEntry("ev_1", "Marico fué el autocorrector", "22/11/26 · 04:14", "Sun", "caso_1", "CASO 01", EvidenceBankCategory.EXCUSAS, "Atribución técnica tras contradecir dos conectores seguidos."),
        EvidenceEntry("ev_2", "La peor parte es que se me olvidó mencionar que tengo 8 cervezas encima y ya son las 4am / Y enfermo", "22/11/26 · 04:15–04:16", "Sun", "caso_1", "CASO 01", EvidenceBankCategory.EXCUSAS, "Cadena de atenuantes físicos y horarios."),
        EvidenceEntry("ev_3", "No estoy en plena consciencia", "22/11/26 · 04:21", "Sun", "caso_1", "CASO 01", EvidenceBankCategory.EXCUSAS, "Escudo de estado alterado durante el debate."),
        EvidenceEntry("ev_4", "el aburrimiento me dió sueño y me saco de concentración el déficit / Perdí el prime", "9/4/26 · 03:39", "Sun", "caso_2", "CASO 02", EvidenceBankCategory.EXCUSAS, "Excusa física tras un bloque de 60+ renglones."),
        EvidenceEntry("ev_5", "Estaba pasándole por encima a los mensajes que no leí viejos jajsjajsjaj", "23/2/26 · 11:09", "Sun", "caso_4", "CASO 04", EvidenceBankCategory.EXCUSAS, "Admisión de lectura superficial tras confundir Raven con DSM-5."),
        EvidenceEntry("ev_6", "La hora / Me tengo que bañar / Estoy llegando a la universidad", "19/1/26 · 01:03–10:26", "Sun", "caso_3", "CASO 03", EvidenceBankCategory.EXCUSAS, "Cierre unilateral apelando a prisa o rutina diaria."),

        EvidenceEntry("ev_7", "El autismo te hace predecible", "22/11/26 · 04:34", "Sun", "caso_1", "CASO 01", EvidenceBankCategory.ETIQUETAS, "Patologización directa para invalidar la réplica."),
        EvidenceEntry("ev_8", "literalmente te arde... te dió rencor o al menos una disonancia cognitiva", "22/11/26 · 04:43", "Sun", "caso_1", "CASO 01", EvidenceBankCategory.ETIQUETAS, "Inversión emocional del foco hacia el interlocutor."),
        EvidenceEntry("ev_9", "El loco no se da cuenta de que me estoy burlando de su tergiversación, el autismo está sólido", "9/4/26 · 19:29", "Sun", "caso_2", "CASO 02", EvidenceBankCategory.ETIQUETAS, "Descalificación + comodín Schrödinger."),
        EvidenceEntry("ev_10", "pone a la gente con ego descontrolado a la defensiva / siempre ha sido así de pedante", "19/1/26 · 01:39–01:52", "N Castillo (sobre Sun)", "caso_3", "CASO 03", EvidenceBankCategory.ETIQUETAS, "Etiquetado a espaldas del interlocutor tras desconectarse."),
        EvidenceEntry("ev_11", "Eres buenísimo teniendo complejo de inferioridad... Complejo de inferioridad perfecto", "10/9/26 · 17:10–17:11", "Armando (+58 414-0352950)", "caso_6", "CASO 06", EvidenceBankCategory.ETIQUETAS, "Patologización cuando Armando decide replegarse."),
        EvidenceEntry("ev_12", "Cobarde jajajaja", "17/11/26 · 18:32", "Daniel (+58 412-5921835)", "caso_6", "CASO 06", EvidenceBankCategory.ETIQUETAS, "Etiqueta lanzada un minuto después de que Daniel abandone el grupo."),
        EvidenceEntry("ev_13", "Te faltó hermenéutica o sintaxis? / sofista / mentiroso sin vergüenza", "9/4/26 · 02:47–03:34", "Sun", "caso_2", "CASO 02", EvidenceBankCategory.ETIQUETAS, "Exigencia asimétrica de rigor formal."),

        EvidenceEntry("ev_14", "implicaciones ontologicas de la IA / La teoría de grafos y la lógica digital / Fotónica / La información cuántica", "22/11/26 · 04:45–04:50", "Sun", "caso_1", "CASO 01", EvidenceBankCategory.TECNICISMOS, "Fuga hacia arriba tras contradicción básica sobre prompts."),
        EvidenceEntry("ev_15", "El anonimato es binario, no gradual... definir con parámetros específicos", "5/6/26 · 12:21–12:23", "Sun", "caso_5", "CASO 05", EvidenceBankCategory.TECNICISMOS, "Exigencia de formalización ante una distinción práctica."),
        EvidenceEntry("ev_16", "Infundado / Non sequitur", "16/11/26 · 16:08–16:09", "N Castillo", "caso_6", "CASO 06", EvidenceBankCategory.TECNICISMOS, "Jerga lógica para desestimar que «bandwagonning» sea más fácil/cómodo de decir."),

        EvidenceEntry("ev_17", "Tengo honestidad intelectual y pensamientos critico", "19/1/26 · 09:59", "Grupo", "caso_3", "CASO 03", EvidenceBankCategory.AUTO_IMAGEN, "Autodefinición como árbitro racional."),
        EvidenceEntry("ev_18", "leo mi propio ego y lo manejo como quiero, difícilmente caigo en sesgos y falacias también por mi metacognición constante", "22/11/26 · 04:35", "Sun", "caso_1", "CASO 01", EvidenceBankCategory.AUTO_IMAGEN, "Declaración de inmunidad frente a sesgos."),
        EvidenceEntry("ev_19", "Cuando me equivoco lo admito enseguida", "9/4/26 · 02:50", "Sun", "caso_2", "CASO 02", EvidenceBankCategory.AUTO_IMAGEN, "Autoimagen en contraste directo con las reescrituras."),

        EvidenceEntry("ev_20", "Yo si soy pedante", "19/1/26 · 09:58", "Grupo", "caso_3", "CASO 03", EvidenceBankCategory.LUCIDEZ, "Admisión explícita de tono pedante."),
        EvidenceEntry("ev_21", "Eso sí fué porque no le ví caso más allá de ganar credibilidad contigo", "9/4/26 · 02:53", "Sun", "caso_2", "CASO 02", EvidenceBankCategory.LUCIDEZ, "Reconocimiento de motivación centrada en credibilidad en IG."),
        EvidenceEntry("ev_22", "Claro si te he dado razones para pensar cosas malas, también por eso no me sorprende en lo absoluto", "22/11/26 · 05:03", "Sun", "caso_1", "CASO 01", EvidenceBankCategory.LUCIDEZ, "Lucidez momentánea al cierre del intercambio."),
        EvidenceEntry("ev_23", "solo estaré esperando algo más que tendré que rebatir y explicar como si tuviera que demostrármelo a mi mismo", "22/11/26 · 05:06", "Sun", "caso_1", "CASO 01", EvidenceBankCategory.LUCIDEZ, "Autorreflexión directa sobre la dinámica compulsiva de debate.")
    )

    val sunBilateralDynamics: List<SunDynamicEntry> = listOf(
        SunDynamicEntry(
            id = "sd_1",
            code = "DINÁMICA 01",
            title = "Perseguir la concesión explícita ante la infantilización",
            triggerFromAngel = "Ángel adopta tono paternalista/superior («Eres inteligente, tu puedes / Solo deja el orgullo», «necesitas el ejercicio», «No te molestes Papu») mientras niega lo que acaba de escribir.",
            sunReactionReason = "Sun detecta la maniobra de inmediato (por eso cita irónicamente a Ángel en 9/4 2:49) y siente impotencia ante la injusticia lógica de que alguien tuerza el historial frente al grupo.",
            objectiveFlaw = "Exigir que quien está jugando a evadir declare su derrota («Necesito tu respuesta / Y si no la das, la tomo como una rendición indirecta») valida sin querer la jerarquía de alumno buscando aprobación del «maestro».",
            timestamp = "9/4/26 · 02:56–02:57",
            quote = "Necesito tu respuesta / Y si no la das, la tomo como una rendición indirecta, a lo sofista, cosa que reconoces muy claro que eres",
            relatedCaseId = "caso_2"
        ),
        SunDynamicEntry(
            id = "sd_2",
            code = "DINÁMICA 02",
            title = "Falsos cierres y desgaste de madrugada",
            triggerFromAngel = "Ángel invierte la carga («todavía lo saca en cara», «sigues peleando solo») o lanza una etiqueta psicológica justo cuando Sun intenta cerrar.",
            sunReactionReason = "El intento de Ángel de quedar con la última palabra reactiva en Sun la necesidad de dejar constancia probatoria de que no está exagerando.",
            objectiveFlaw = "Anunciar «A dormir» o «el punto está cerrado» (22/11 4:20, 4:31 y 4:41–4:42) y volver minutos después a seguir debatiendo hasta las 5:09 AM —o hasta la 1:07 AM teniendo 3 horas para dormir (19/1)— refuerza visualmente la acusación de Ángel de que «te arde».",
            timestamp = "22/11/26 · 04:31",
            quote = "Ajá, si ya admitiste el error lingüístico y que me diste la razón en el fondo, entonces el punto está cerrado. A dormir / Noup, deja exporto todos los chats y los desgloso",
            relatedCaseId = "caso_1"
        ),
        SunDynamicEntry(
            id = "sd_3",
            code = "DINÁMICA 03",
            title = "Igualar el tono agresivo, credenciales y psicologización inversa",
            triggerFromAngel = "Ángel descalifica con etiquetas («prejuicioso», «imbécil», «el autismo te hace predecible») o elude responder métodos concretos.",
            sunReactionReason = "Sun intenta responder en la misma frecuencia para no parecer pasivo ni dejar pasar la burla («nivelandome a tu manera de ser»).",
            objectiveFlaw = "Devolver psicologización («toqué tu herida» 22/11 4:38), usar burla inicial («You're wrong you're just a bad prompter ugu» / «Escribes dlv») o apelar a estatus laboral/académico («soy estudiante de economía... carece de experiencia laboral» 9/4 18:12–18:16) desvía el debate del argumento sólido hacia una pulseada de egos.",
            timestamp = "9/4/26 · 02:51 y 18:12–18:16",
            quote = "Solo que nivelandome a tu manera de ser (2:51) · soy estudiante de economía, y tus atrocidades de planes son, atrocidades lol (18:12)",
            relatedCaseId = "caso_2"
        ),
        SunDynamicEntry(
            id = "sd_4",
            code = "DINÁMICA 04",
            title = "Entregar vocabulario vulnerable y humor autodespectivo",
            triggerFromAngel = "Ángel cuestiona la validez de la psicología o minimiza el diagnóstico como «excusa» y «terquedad».",
            sunReactionReason = "Sun habla con transparencia en un grupo que considera de amigos y usa humor autocrítico para rebajar tensión o explicar su experiencia interna.",
            objectiveFlaw = "Llamar a su propia búsqueda de ayuda «excusa», «ignorancia», «placebo autogenerado» (19/1) o «capricho» (23/2), y bromear con «el autismo» (9/4 2:19), entrega literalmente a Ángel las palabras y etiquetas que luego recicla para infantilizarlo.",
            timestamp = "19/1/26 · 00:19–00:25",
            quote = "Necesito eso / Para actuar en cualquier cosa / Una \"excusa\" / Una \"justificación\" · Quizá quiero permanecer en ignorancia en ese aspecto · Es literalmente un placebo autogenerado",
            relatedCaseId = "caso_3"
        ),
        SunDynamicEntry(
            id = "sd_5",
            code = "DINÁMICA 05",
            title = "Flancos técnicos o inferencias sobre mensajes multimedia",
            triggerFromAngel = "Ángel combina respuestas rápidas en texto con stickers/audios (<Multimedia omitido>) y luego exige rigor literal.",
            sunReactionReason = "En la rapidez del chat en vivo, Sun resume o interpreta la intención detrás del conjunto de respuestas de Ángel.",
            objectiveFlaw = "Atribuir en texto que Ángel admitió «un jodido chiste» tras mensajes multimedia (9/4 18:31–18:35) o redactar una frase imprecisa como «cree que la IA funciona como lenguaje humano» (22/11 3:59) le da a Ángel asideros reales para alegar tergiversación o «Non sequitur».",
            timestamp = "22/11/26 · 03:59 y 9/4/26 · 18:31",
            quote = "Brotato cree que la IA funciona como lenguaje humano (22/11 3:59) · Ahh, ahora es una frase de chiste, recoge la cuerda (9/4 18:31)",
            relatedCaseId = "caso_1"
        )
    )

    val allInterlocutors: List<String> = listOf(
        "Sun",
        "Pablo Coronel",
        "Armando",
        "N Castillo",
        "MariaJosé",
        "Tarek",
        "Daniel"
    )

    fun getCaseById(caseId: String): DocumentedCase? =
        documentedCases.find { it.id == caseId }
}
