package cannonGame.GUI;

import cannonGame.StaticUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Defines a start scene UI
 */
public class StartScene {
    /**
     * Shows start window.
     * @param stage
     * @param root
     */
    public void showStartWindow(Stage stage, Parent root) {
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

}
