package com.efikay.wbubtw.domain.challenge.bot.challenge_results

import com.efikay.wbubtw.domain.challenge.ChallengeId
import org.springframework.stereotype.Service

@Service
class ChallengeTotalResultsService(
    private val challengeResultsService: ChallengeResultsService
) {
    @Suppress("UNCHECKED_CAST")
    fun getUsersChallengeTotalStats(): List<UsersChallengeTotalStats> {
        return ChallengeId.entries.map { challengeId ->
            val challengeResults = challengeResultsService.getAllChallengeResults().values.flatten()
            val challengeValues = challengeResults
                .filter {
                    it.challengeId == challengeId
                }.map {
                    it.__FIXME__getValueResult()
                }

            val (averageValue, maxValue) = when (challengeValues.firstOrNull()) {
                is Int -> {
                    val intValues = challengeValues as List<Int>

                    Pair(
                        makeAvgIntResult(intValues),
                        makeMaxIntResult(intValues),
                    )
                }

                is String -> {
                    val stringValues = challengeValues as List<String>

                    Pair(
                        "N/A",
                        makeMaxStringResult(stringValues),
                    )
                }

                null -> Pair("<No data>", "<No data>")
                else -> throw ClassCastException("Wrong getValueResult return type in ChallengeResult!")
            }

            UsersChallengeTotalStats(
                displayChallengeName = challengeId.name,
                amountUsersChallenged = challengeValues.size,
                displayAverageValue = averageValue,
                displayMaxValue = maxValue,
            )
        }
    }


    private fun makeMaxStringResult(results: List<String>): String {
        val res = results.groupingBy { it }.eachCount()

        val (mostOftenString, occurrences) = res.entries.maxByOrNull {
            it.value
        }?.toPair() ?: Pair("<No data>", 0)


        return "\"$mostOftenString\", встречается $occurrences раз"
    }

    private fun makeAvgIntResult(results: List<Int>): String = results.average().toString()
    private fun makeMaxIntResult(results: List<Int>): String = results.max().toString()
}