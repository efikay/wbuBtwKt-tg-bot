package com.efikay.wbubtw.domain.challenge.contest

import com.efikay.wbubtw.domain.challenge.ChallengeId
import com.efikay.wbubtw.domain.challenge.ChallengeResult

class RandomQuantityContest(
    @Suppress("FORBIDDEN_VARARG_PARAMETER_TYPE", "UNUSED_PARAMETER")
    vararg nothings: Nothing,

    private val challengeId: ChallengeId,
    private val displayName: String,
    private val displayDescription: String,
    private val resultTemplates: Map<IntRange, List<String>>,
    private val getQuantity: () -> Int,
) : ChallengeContest {
    override fun execute(): ChallengeResult {
        val result = getQuantity()

        val (_, templates) = resultTemplates.entries.find { (range) -> range.contains(result) }!!

        return ChallengeResult(
            displayName = displayName,
            displayDescription = displayDescription,
            getDisplayResult = { templates.random().format(result) },
            challengeId = challengeId,

            __FIXME__getValueResult = { result }
        )
    }
}