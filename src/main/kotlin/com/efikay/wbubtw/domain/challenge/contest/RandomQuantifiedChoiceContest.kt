package com.efikay.wbubtw.domain.challenge.contest

import com.efikay.wbubtw.domain.challenge.ChallengeId
import com.efikay.wbubtw.domain.challenge.ChallengeResult

class RandomQuantifiedChoiceContest(
    @Suppress("FORBIDDEN_VARARG_PARAMETER_TYPE", "UNUSED_PARAMETER")
    vararg nothings: Nothing,

    private val challengeId: ChallengeId,
    private val displayName: String,
    private val displayDescription: String,
    private val choicesWithTemplates: Map<String, Map<IntRange, List<String>>>,
    private val getQuantity: () -> Int,
    private val persistentDisplayResult: Boolean = false,
) : ChallengeContest {
    override fun execute(): ChallengeResult {
        val quantity = getQuantity()

        val (chosenName, templateRanges) = choicesWithTemplates.entries.random()

        val (chosenRange, templates) = templateRanges.entries.find { (range) -> range.contains(quantity) }!!

        val initiallyGeneratedDisplayResult = "$chosenName: ${templates.random().format(quantity)}"

        return ChallengeResult(
            displayName = displayName,
            displayDescription = displayDescription,
            getDisplayResult = {
                if (persistentDisplayResult) {
                    initiallyGeneratedDisplayResult
                } else {
                    "$chosenName: ${templates.random().format(quantity)}"
                }
            },
            challengeId = challengeId,

            __FIXME__getValueResult = { "$chosenName $chosenRange" }
        )
    }
}