import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.util.Callback
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import java.awt.Desktop
import java.net.URI

class Controller {

    @FXML private lateinit var area: TextArea

    @FXML private lateinit var keyword: TextField
    @FXML private lateinit var searchToggle : ToggleButton

    @FXML private lateinit var listView: ListView<TweetRow>

    // 押されたキーを管理
    private val pressKeys = mutableSetOf<KeyCode>()

    private val twitterRepository = TwitterRepository()

    @FXML fun initialize() {

        area.setOnKeyPressed {
            pressKeys += it.code
            judgeKeys()
        }

        area.setOnKeyReleased {
            pressKeys -= it.code
        }

        listView.cellFactory = Callback<ListView<TweetRow>, ListCell<TweetRow>> {
            TweetRowController()
        }
        listView.items = FXCollections.observableArrayList<TweetRow>()

        listView.setOnMouseClicked {
            if (it.clickCount == 2) {
                val selectedItems = listView.selectionModel.selectedItems
                Desktop.getDesktop().browse(URI(selectedItems[0].detailUrl))
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
                    listView.items.addAll(0, result)

                    delay(10 * 1000L)
                    if (!searchToggle.isSelected) break
                }
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, getOwner(), "情報", "検索を中止しました。")
        }
    }

    private fun getOwner() = area.scene.window

    private fun judgeKeys() {
        if (pressKeys.contains(KeyCode.ENTER) && pressKeys.contains(KeyCode.COMMAND)) {

            if (area.text.isEmpty()) return

            confirmAlert(getOwner()," 確認","ツイートしますか?") {
                twitterRepository.tweet("${area.text} ${keyword.text}")
                area.text = ""
            }
        }
    }
}
