
class TwitterApi {

    fun getSearch(query: String): List<Row> {

        val result = listOf(
                Row("aaa", "hoge 1"),
                Row("aaa", "hoge 2"),
                Row("aaa", "hoge 3"),
                Row("aaa", "hoge 4"),
                Row("aaa", "hoge 5")
        )

        return result
   }
}