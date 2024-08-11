package com.efikay.wbubtw.domain.random_meme.reddit

import com.efikay.wbubtw.shared.api_clients.meme_api.GetMemeApiResponse

data class RedditMeme(
    val postLink: String,
    val subreddit: String,
    val title: String,
    val url: String,
    val nsfw: Boolean,
    val spoiler: Boolean,
    val photoUrl: String
) {
    companion object {
        fun from(apiResponse: GetMemeApiResponse): RedditMeme {
            val largestMemePhoto = apiResponse.preview.last()

            return RedditMeme(
                postLink = apiResponse.postLink,
                subreddit = apiResponse.subreddit,
                title = apiResponse.title,
                url = apiResponse.url,
                nsfw = apiResponse.nsfw,
                spoiler = apiResponse.spoiler,
                photoUrl = largestMemePhoto,
            )
        }
    }
}