import org.apache.logging.log4j.LogManager
import twitter4j.Query
import twitter4j.Status
import twitter4j.TwitterException
import twitter4j.TwitterFactory

class TwitterRepository {

    private var cacheTweet = mutableListOf<Status>()

    private val twitter = TwitterFactory().instance

    fun getSearch(queryString: String): List<TweetRow> {

        if (queryString.isEmpty()) return emptyList()

        val result = mutableListOf<TweetRow>()

        try {
            val query = Query(queryString)
            query.lang = "ja"
            val queryResult = twitter.search(query)
            val newStatus = queryResult.tweets.minus(cacheTweet)

            cacheTweet.addAll(0, newStatus)

            newStatus.forEach {
                val url = "https://twitter.com/${it.user.screenName}/status/${it.id}"
                result.add(TweetRow(it.user.name, it.text, url))

                log.info("new tweet :$it")
            }
        } catch (e: TwitterException) {
            log.error("TwitterExceptionが発生しました。", e)
        }

        return result
    }

    fun tweet(tweet: String) {
        try {
            twitter.updateStatus(tweet)
        } catch (e: TwitterException) {
            log.error("TwitterExceptionが発生しました。", e)
        }
    }

    companion object {
        private val log = LogManager.getLogger(TwitterRepository::class.java.simpleName)
    }
}