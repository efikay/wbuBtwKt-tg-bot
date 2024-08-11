package com.efikay.wbubtw.shared.api_clients.meme_api

data class GetMemeApiResponse(
    val postLink: String,
    val subreddit: String,
    val title: String,
    val url: String,
    val nsfw: Boolean,
    val spoiler: Boolean,
    val author: String,
    val ups: Int,
    val preview: List<String>
)

data class GetMultipleMemesApiResponse(
    val count: Int,
    val memes: List<GetMemeApiResponse>
)
