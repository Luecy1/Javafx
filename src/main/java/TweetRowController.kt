import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.text.Text
import javafx.fxml.FXMLLoader
import javafx.scene.layout.VBox

class TweetRowController : ListCell<TweetRow>() {

    @FXML lateinit var cellContainer: VBox

    @FXML lateinit var name : Label
    @FXML lateinit var tweet : Text

    init {
        FXMLLoader(javaClass.getResource("tweet_cell.fxml")).apply {
            setController(this@TweetRowController)
            load()
        }
    }

    override fun updateItem(item: TweetRow?, empty: Boolean) {
        super.updateItem(item, empty)

        if (empty) {
            text = ""

            return
        }

        name.text = item?.name?.get()
        tweet .text = item?.tweet?.get()
        graphic = cellContainer
    }
}