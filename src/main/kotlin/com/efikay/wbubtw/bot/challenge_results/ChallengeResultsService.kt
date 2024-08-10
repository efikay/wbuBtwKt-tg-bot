package com.efikay.wbubtw.bot.challenge_results

import com.efikay.wbubtw.challenge.ChallengeId
import com.efikay.wbubtw.challenge.ChallengeResult
import com.efikay.wbubtw.challenge.ChallengeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ChallengeResultsService(private val challengesService: ChallengeService) {
    private val challengeResults = HashMap<Long, List<ChallengeResult>>()

    fun getAllChallengeResults() = challengeResults

    fun getUserChallengeResult(userId: Long, challengeId: ChallengeId): ChallengeResult {
        return getUserChallengeResults(userId).find { it.challengeId == challengeId }!!
    }

    fun getUserChallengeResults(userId: Long): List<ChallengeResult> {
        if (challengeResults[userId] == null) {
            val userResults = challengesService.generateChallengeResults()

            challengeResults[userId] = userResults
        }

        return challengeResults[userId]!!
    }

    @Scheduled(cron = "0 0-23/6 * * * *")
    fun clearChallengeResults() {
        challengeResults.clear()
    }
}