import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty


class Row(name : String, tweet : String) {

    val name : StringProperty = SimpleStringProperty()

    val tweet : StringProperty = SimpleStringProperty()

    init {
        this.name.set(name)
        this.tweet.set(tweet)
    }
}

