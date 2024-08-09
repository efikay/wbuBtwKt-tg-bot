package com.efikay.wbubtw.bot.challenge_results

import com.efikay.wbubtw.challenge.ChallengeId
import org.springframework.stereotype.Service

@Service
class ChallengeTotalResultsService(
    private val challengeResultsService: ChallengeResultsService
) {
    fun getUsersChallengeTotalStats(): List<UsersChallengeTotalStats> {
        return ChallengeId.entries.map { challengeId ->
            val challengeResults = challengeResultsService.getAllChallengeResults().values.flatten()
            val challengeValues = challengeResults
                .filter {
                    it.challengeId == challengeId
                }.map {
                    it.__FIXME__getValueResult()
                }

            @Suppress("UNCHECKED_CAST")
            val averageValue = when (challengeValues.firstOrNull()) {
                null -> "<No data>"
                is Int -> processIntValueResults(challengeValues as List<Int>)
                is String -> processStringValueResults(challengeValues as List<String>)

                else -> throw ClassCastException("Wrong getValueResult return type in ChallengeResult!")
            }

            UsersChallengeTotalStats(
                displayChallengeName = challengeId.name,
                amountUsersChallenged = challengeValues.size,
                displayAverageValue = averageValue
            )
        }
    }


    private fun processStringValueResults(results: List<String>): String {
        val res = results.groupingBy { it }.eachCount()

        val (mostOftenString, occurrences) = res.entries.maxByOrNull {
            it.value
        }?.toPair() ?: Pair("<No data>", 0)


        return "\"$mostOftenString\", встречается $occurrences раз"
    }

    private fun processIntValueResults(results: List<Int>): String = results.average().toString()
}