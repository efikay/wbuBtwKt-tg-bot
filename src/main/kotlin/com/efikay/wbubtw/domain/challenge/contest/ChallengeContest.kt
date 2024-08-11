package com.efikay.wbubtw.domain.challenge.contest

import com.efikay.wbubtw.domain.challenge.ChallengeResult

interface ChallengeContest {
    fun execute(): ChallengeResult
}