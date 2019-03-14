import twitter4j.Query
import twitter4j.Status
import twitter4j.TwitterFactory

class TwitterRepository {

    private var cacheTweet = mutableListOf<Status>()

    private val twitter = TwitterFactory().instance

    fun getSearch(queryString: String): List<Row> {

        if (queryString.isEmpty()) return emptyList()

        val result = mutableListOf<Row>()

        val query = Query(queryString)
        query.lang = "ja"
        val queryResult = twitter.search(query)
        val newStatus = queryResult.tweets.minus(cacheTweet)

        cacheTweet.addAll(0, newStatus)

        newStatus.forEach {
            val url = "https://twitter.com/${it.user.screenName}/status/${it.id}"
            result.add(Row(it.user.name, it.text, url))
        }

        return result
    }

    fun tweet(tweet : String) {
        twitter.updateStatus(tweet)
    }
}