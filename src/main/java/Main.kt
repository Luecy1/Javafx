import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}

class Main : Application() {

    override fun start(primaryStage: Stage) {

        log.info("アプリケーションを開始します。")
        try {
            val fxmlLoader = FXMLLoader(javaClass.getResource("main_layout.fxml"))
            val parent = fxmlLoader.load<Parent>()

            val scene = Scene(parent)
            primaryStage.scene = scene

            primaryStage.title = "Twitter"
            primaryStage.show()

        } catch (e: Exception) {
            log.error("Exceptionが発生しました。", e)
        }
    }

    override fun stop() {
        super.stop()
        log.info("アプリケーションを終了します。")
    }

    companion object {
        private val log = LogManager.getLogger(Main::class.java.simpleName)
    }
}