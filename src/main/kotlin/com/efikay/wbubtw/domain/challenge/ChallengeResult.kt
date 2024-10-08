package com.efikay.wbubtw.domain.challenge

data class ChallengeResult(
    val challengeId: ChallengeId,
    val displayName: String,
    val displayDescription: String,
    val getDisplayResult: () -> String,

    val __FIXME__getValueResult: () -> Any,
)