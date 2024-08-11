package com.efikay.wbubtw.domain.random_meme.reddit

import com.efikay.wbubtw.shared.api_clients.meme_api.GetMultipleMemesApiResponse
import com.efikay.wbubtw.shared.api_clients.meme_api.MemeApiService
import org.springframework.stereotype.Service

@Service
class RandomRedditMemeService(
    private val apiService: MemeApiService
) {
    private val storedMemes = mutableListOf<RedditMeme>()
    private val usedMemeIds = mutableListOf<String>()

    fun getNextMeme(alsoMarkAsRead: Boolean = false): RedditMeme? {
        if (storedMemes.isEmpty()) {
            val newMemesAmount = grabSomeMoreMemes()

            if (newMemesAmount == 0) {
                throw RuntimeException("Cannot get memes :(")
            }
        }

        val nextMeme = storedMemes.first()

        if (alsoMarkAsRead) {
            markMemeAsRead(nextMeme.postLink)
        }

        return nextMeme
    }

    fun markMemeAsRead(memeLink: String) {
        val isMemeRemoved = storedMemes.removeIf {
            it.postLink == memeLink
        }

        if (isMemeRemoved) {
            usedMemeIds.add(memeLink)
        }
    }

    private fun grabSomeMoreMemes(retries: Int = 3): Int {
        var someApiMemes: GetMultipleMemesApiResponse? = null
        (1..retries).forEach { _ ->
            someApiMemes = apiService.getMany(MEMES_EXTRA)

            if (someApiMemes != null) {
                return@forEach
            }

            Thread.sleep(3_000L)
        }

        val someMemes = someApiMemes?.memes?.map {
            RedditMeme.from(it)
        } ?: return 0

        val freshMemes = someMemes.filter { meme ->
            usedMemeIds.all {
                it != meme.postLink
            }
        }

        storedMemes.addAll(freshMemes)

        return freshMemes.size
    }

    companion object {
        private const val MEMES_EXTRA = 20
    }
}