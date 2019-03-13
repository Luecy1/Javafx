import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.input.KeyCode
import javafx.util.Callback
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx

class Controller {

    @FXML private lateinit var area: TextArea

    @FXML private lateinit var listView: ListView<Row>

    // 押されたキーを管理
    private val pressKeys = mutableSetOf<KeyCode>()

    @FXML fun initialize() {

        area.setOnKeyPressed {
            pressKeys += it.code
            judgeKeys()
        }

        area.setOnKeyReleased {
            pressKeys -= it.code
        }

        val items = FXCollections.observableArrayList<Row>()
        (1..10).forEach {
            items += Row("Name $it"," Tweet $it あああああああああああああああああああああ" +
                    "ああああ")
        }

        listView.cellFactory = Callback<ListView<Row>, ListCell<Row>> {
            RowController()
        }
        listView.items = items

        GlobalScope.launch(Dispatchers.Default){

            val result = withContext(Dispatchers.JavaFx) {
                delay(3 * 1000L)
                TwitterApi().getSearch("")
            }


            area.text = "huga" + Thread.currentThread().id.toString()
        }
        area.text = Thread.currentThread().id.toString()
    }

    @FXML fun handleSubmitButtonAction() {
        showAlert(Alert.AlertType.INFORMATION,getOwner(),"情報","Click!")
    }

    private fun getOwner() = area.scene.window

    private fun judgeKeys() {
        if (pressKeys.contains(KeyCode.ENTER) && pressKeys.contains(KeyCode.COMMAND)) {
            showAlert(Alert.AlertType.INFORMATION,getOwner(),"情報","tweet")
            area.text = ""
        }
    }
}
