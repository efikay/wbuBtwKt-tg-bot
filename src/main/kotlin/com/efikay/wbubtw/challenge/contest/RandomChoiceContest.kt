package com.efikay.wbubtw.challenge.contest

import com.efikay.wbubtw.challenge.ChallengeResult

class RandomChoiceContest(
    private val displayName: String,
    private val displayDescription: String,
    private val choices: List<String>
) : ChallengeContest {
    override fun execute(): ChallengeResult {
        val choice = choices.random()

        return ChallengeResult(
            displayName = displayName,
            displayDescription = displayDescription,
            getDisplayResult = { choice }
        )
    }
}