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
            val resource = javaClass.getResource("mainLayout.fxml")
            val parent = FXMLLoader.load<Parent>(resource)

            val scene = Scene(parent)
            primaryStage.scene = scene

            primaryStage.title = "TODO"
            primaryStage.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}