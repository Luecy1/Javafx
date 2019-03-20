// https://github.com/callicoder/javafx-examples/blob/master/javafx-registration-form-fxml/src/javafx/example/AlertHelper.java

import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.Window

fun showAlert(alertType: Alert.AlertType, owner: Window, title: String, message: String) {
    Alert(alertType).apply {
        this.title = title
        this.headerText = null
        this.contentText = message
        this.initOwner(owner)
        this.showAndWait()
    }
}

fun confirmAlert(owner: Window, title: String, message: String, okAction: () -> Unit) {
    Alert(Alert.AlertType.CONFIRMATION).apply {
        this.title = title
        this.headerText = null
        this.contentText = message
        this.initOwner(owner)
        this.showAndWait().filter {
            it == ButtonType.OK
        }.ifPresent {
            okAction()
        }
    }
}