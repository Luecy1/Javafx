import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.util.Callback
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import org.apache.logging.log4j.LogManager
import java.awt.Desktop
import java.net.URI

class Controller {

    @FXML
    private lateinit var tweetTextArea: TextArea

    @FXML private lateinit var keyword: TextField
    @FXML private lateinit var searchToggle : ToggleButton

    @FXML
    private lateinit var tweetListView: ListView<TweetRow>

    // 押されたキーを管理
    private val pressKeys = mutableSetOf<KeyCode>()

    private val twitterRepository = TwitterRepository()

    @FXML fun initialize() {

        tweetTextArea.setOnKeyPressed {
            pressKeys += it.code
            judgeKeys()
        }

        tweetTextArea.setOnKeyReleased {
            pressKeys -= it.code
        }

        tweetListView.cellFactory = Callback<ListView<TweetRow>, ListCell<TweetRow>> {
            TweetRowController()
        }
        tweetListView.items = FXCollections.observableArrayList<TweetRow>()

        tweetListView.setOnMouseClicked {
            if (it.clickCount == 2) {
                openSelectedDetail()
            }
        }
        tweetListView.setOnKeyPressed {
            if (it.code == KeyCode.ENTER) {
                openSelectedDetail()
            }
        }

        keyword.setOnKeyPressed {
            if (it.code == KeyCode.ENTER) {
                searchToggle.isSelected = true
                toggleSearchButton()
            }
        }

        searchToggle.setOnAction {
            toggleSearchButton()
        }
    }

    private fun toggleSearchButton() {
        if (searchToggle.isSelected) {

            if (keyword.text.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, getOwner(), "警告", "検索キーワードを入力してください。")
                searchToggle.isSelected = false
                return
            }

            showAlert(Alert.AlertType.INFORMATION, getOwner(), "情報", "検索を開始します。")

            GlobalScope.launch(Dispatchers.JavaFx) {

                while (true) {
                    val result = withContext(Dispatchers.Default) {
                        twitterRepository.getSearch(keyword.text)
                    }
                    tweetListView.items.addAll(0, result)

                    delay(10 * 1000L)
                    if (!searchToggle.isSelected) break
                }
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, getOwner(), "情報", "検索を中止しました。")
        }
    }

    private fun getOwner() = tweetTextArea.scene.window

    private fun judgeKeys() {
        if (pressKeys.contains(KeyCode.ENTER) && pressKeys.contains(KeyCode.COMMAND)) {

            if (tweetTextArea.text.isEmpty()) return

            confirmAlert(getOwner(),"確認","ツイートしますか?") {

                val tweetText = "${tweetTextArea.text} ${keyword.text}"

                twitterRepository.tweet(tweetText)

                log.info("Tweet : $tweetText")
                tweetTextArea.text = ""

                // ダイアログ中にクリアされないため
                pressKeys.clear()
            }
        }
    }

    private fun openSelectedDetail() {
        val selectedItems = tweetListView.selectionModel.selectedItems
        Desktop.getDesktop().browse(URI(selectedItems[0].detailUrl))
    }

    companion object {
        private val log = LogManager.getLogger(Controller::class.java.simpleName)
    }
}
