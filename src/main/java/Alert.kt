// https://github.com/callicoder/javafx-examples/blob/master/javafx-registration-form-fxml/src/javafx/example/AlertHelper.java

import javafx.scene.control.Alert
import javafx.stage.Window

fun showAlert(alertType: Alert.AlertType, owner: Window, title: String, message: String) {
    val alert = Alert(alertType)
    alert.title = title
    alert.headerText = null
    alert.contentText = message
    alert.initOwner(owner)
    alert.show()
}
