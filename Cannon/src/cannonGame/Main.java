package cannonGame;

import cannonGame.GUI.StartScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Starts the application by showing StartScene window.
 */
public class Main extends Application {

    private static Stage primaryStage;
    private static Main instance;

    @Override
    public void start(Stage primaryStage) throws IOException {
        instance = this;
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Space ship battle");
        Parent startWindowRoot = FXMLLoader.load(getClass().getResource("GUI/startWindow.fxml"));
        StartScene startScene = new StartScene();
        startScene.showStartWindow(primaryStage, startWindowRoot);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static Main getInstance(){
        return instance;
    }
}
