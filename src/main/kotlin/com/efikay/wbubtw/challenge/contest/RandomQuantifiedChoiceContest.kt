package com.efikay.wbubtw.challenge.contest

import com.efikay.wbubtw.challenge.ChallengeId
import com.efikay.wbubtw.challenge.ChallengeResult

class RandomQuantifiedChoiceContest(
    @Suppress("FORBIDDEN_VARARG_PARAMETER_TYPE", "UNUSED_PARAMETER")
    vararg nothings: Nothing,

    private val challengeId: ChallengeId,
    private val displayName: String,
    private val displayDescription: String,
    private val choicesWithTemplates: Map<String, Map<IntRange, List<String>>>,
    private val getQuantity: () -> Int,
) : ChallengeContest {
    override fun execute(): ChallengeResult {
        val quantity = getQuantity()

        val (_, templateRanges) = choicesWithTemplates.entries.random()

        val (_, templates) = templateRanges.entries.find { (range) -> range.contains(quantity) }!!

        return ChallengeResult(
            displayName = displayName,
            displayDescription = displayDescription,
            getDisplayResult = { templates.random().format(quantity) },
            challengeId = challengeId,
        )
    }
}