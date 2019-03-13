import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.input.KeyCode

class Controller {

    @FXML private lateinit var area: TextArea

    @FXML private lateinit var listView: ListView<String>

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

        val items = FXCollections.observableArrayList<String>(
                "one", "two", "three"
        )
        (1..10).forEach {
            items += "item $it"
        }

        listView.items = items
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
