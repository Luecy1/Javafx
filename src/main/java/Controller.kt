import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Label

class Controller {

    @FXML private lateinit var textLabel: Label

    @FXML fun handleSubmitButtonAction() {

        val owner = textLabel.scene.window

        println("click!")

        textLabel.text = "click!"

        AlertHelper.showAlert(Alert.AlertType.INFORMATION,owner,"情報","Click!")
    }
}
