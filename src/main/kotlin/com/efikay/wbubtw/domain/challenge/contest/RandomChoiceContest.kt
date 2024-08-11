package com.efikay.wbubtw.domain.challenge.contest

import com.efikay.wbubtw.domain.challenge.ChallengeId
import com.efikay.wbubtw.domain.challenge.ChallengeResult

class RandomChoiceContest(
    @Suppress("FORBIDDEN_VARARG_PARAMETER_TYPE", "UNUSED_PARAMETER")
    vararg nothings: Nothing,

    private val challengeId: ChallengeId,
    private val displayName: String,
    private val displayDescription: String,
    private val choices: List<String>
) : ChallengeContest {
    override fun execute(): ChallengeResult {
        val choice = choices.random()

        return ChallengeResult(
            displayName = displayName,
            displayDescription = displayDescription,
            getDisplayResult = { choice },
            challengeId = challengeId,

            __FIXME__getValueResult = { choice }
        )
    }
}