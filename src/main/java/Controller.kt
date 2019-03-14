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
    @FXML private lateinit var button : Button

    @FXML private lateinit var listView: ListView<Row>

    // 押されたキーを管理
    private val pressKeys = mutableSetOf<KeyCode>()

    private var searchingFlg = false

    private val twitterRepository = TwitterRepository()

    @FXML fun initialize() {

        area.setOnKeyPressed {
            pressKeys += it.code
            judgeKeys()
        }

        area.setOnKeyReleased {
            pressKeys -= it.code
        }

        val items = FXCollections.observableArrayList<Row>()

        listView.cellFactory = Callback<ListView<Row>, ListCell<Row>> {
            RowController()
        }
        listView.items = items

        listView.setOnMouseClicked {
            if (it.clickCount == 2) {
                val selectedItems = listView.selectionModel.selectedItems
                println(selectedItems[0].detailUrl)
                Desktop.getDesktop().browse(URI(selectedItems[0].detailUrl))
            }
        }
    }

    @FXML fun handleSubmitButtonAction() {
        if (!searchingFlg) {

            if (keyword.text.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, getOwner(), "警告", "検索キーワードを入力してください。")
                return
            }

            showAlert(Alert.AlertType.INFORMATION, getOwner(), "情報", "検索を開始します。")
            button.text = "検索中"
            searchingFlg = true

            GlobalScope.launch(Dispatchers.JavaFx) {

                while (true) {
                    val result = withContext(Dispatchers.Default) {
                        twitterRepository.getSearch(keyword.text)
                    }
                    listView.items.addAll(0, result)

                    delay(10 * 1000L)
                    if (!searchingFlg) break
                }
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, getOwner(), "情報", "検索を中止します。")
            button.text = "検索"
            searchingFlg = false
        }

    }

    private fun getOwner() = area.scene.window

    private fun judgeKeys() {
        if (pressKeys.contains(KeyCode.ENTER) && pressKeys.contains(KeyCode.COMMAND)) {

            if (area.text.isEmpty()) return

            confirmAlert(getOwner()," 確認","ツイートしますか") {
                twitterRepository.tweet("${area.text} ${keyword.text}")
                area.text = ""
            }
        }
    }
}
