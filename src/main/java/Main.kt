import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}

class Main : Application() {

    override fun start(primaryStage: Stage) {

        try {
            val fxmlLoader = FXMLLoader(javaClass.getResource("mainLayout.fxml"))
            val parent = fxmlLoader.load<Parent>()

            val scene = Scene(parent)
            primaryStage.scene = scene

            primaryStage.title = "Twitter"
            primaryStage.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}