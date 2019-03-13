import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.input.KeyCode

class Controller {

    @FXML private lateinit var textLabel: Label

    @FXML private lateinit var area: TextArea

    // 押されたキーを管理
    private val pressKeys = mutableSetOf<KeyCode>()

    @FXML fun initialize() {
        println("init")

        area.setOnKeyPressed {
            pressKeys += it.code
            judgeKeys()
        }

        area.setOnKeyReleased {
            pressKeys -= it.code
        }
    }

    @FXML fun handleSubmitButtonAction() {
        
        AlertHelper.showAlert(Alert.AlertType.INFORMATION,getOwner(),"情報","Click!")
    }

    private fun getOwner() = textLabel.scene.window

    private fun judgeKeys() {
        if (pressKeys.contains(KeyCode.ENTER) && pressKeys.contains(KeyCode.COMMAND)) {
            AlertHelper.showAlert(Alert.AlertType.INFORMATION,getOwner(),"情報","tweet")
        }
    }
}
