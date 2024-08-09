package com.efikay.wbubtw.challenge

data class ChallengeResult(
    val challengeId: ChallengeId,
    val displayName: String,
    val displayDescription: String,
    val getDisplayResult: () -> String,
    
    val __FIXME__getValueResult: () -> Any,
)