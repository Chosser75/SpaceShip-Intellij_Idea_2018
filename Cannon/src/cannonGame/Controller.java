package cannonGame;

import cannonGame.GUI.GameScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    @FXML
    private TextField nameTF;

    /**
     *  Handles mouse-button-click events.
     *  Left button - shoot, right button - stop moving space ship.
     */
    public void mouseClick(MouseEvent event, SpaceShip spaceShip, Pane root) {
        if (event.getButton() == MouseButton.PRIMARY) spaceShip.fire(root);
        if (event.getButton() == MouseButton.SECONDARY) spaceShip.setCanDir("stop");
    }

    /**
     * Handles mouse-move events.
     * If the cursor were at the left side from the space ship, it moves left,
     * if the cursor were at the right side from the space ship, it moves right.
     */
    public void mouseListener(MouseEvent event, SpaceShip spaceShip) {
        if (event.getX() > spaceShip.getBody().getX()) spaceShip.setCanDir("right");
        if (event.getX() < spaceShip.getBody().getX()) spaceShip.setCanDir("left");
    }

    /**
     * Handles keyboard events.
     * If RIGHT key is pressed, space ship moves right.
     * If LEFT key is pressed, space ship moves left.
     * If DOWN key is pressed, space ship stops.
     * If SPACE key is pressed, space ship shots.
     * If Q key is pressed, current game stops and checks for a new best result.
     */
    public void keyListener(KeyEvent event, SpaceShip spaceShip, Pane root) {
        if (event.getCode() == KeyCode.RIGHT) spaceShip.setCanDir("right");
        if (event.getCode() == KeyCode.LEFT) spaceShip.setCanDir("left");
        if (event.getCode() == KeyCode.DOWN) spaceShip.setCanDir("stop");
        if (event.getCode() == KeyCode.SPACE) spaceShip.fire(root);
        if (event.getCode() == KeyCode.Q) {
            try {
                quitGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops current game and checks for new best result.
     * If new record, shows NewRecordScene, which prompts user
     * to enter his name.
     */
    private void quitGame() throws IOException {
        if (checkRecord()){
            // ask name
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(Main.getPrimaryStage());
            dialog.setTitle("New best score!");
            Parent newRecWinRoot = FXMLLoader.load(getClass().getResource("GUI/newRecordScene.fxml"));
            Scene newRecordScene = new Scene(newRecWinRoot);
            dialog.setScene(newRecordScene);
            dialog.show();
        }
        // !!!!!! need to stop all threads !!!!!!
        try {
            Main.getInstance().start(Main.getPrimaryStage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a new game.
     */
    public void startGame(){
        GameScene gameScene = new GameScene();
        gameScene.startGame(Main.getPrimaryStage());
    }

    /**
     * Gets a list of top winners from the database,
     * and checks if the current score is a new best result.
     * @return boolean
     */
    private boolean checkRecord(){
        DataBaseHelper db = new DataBaseHelper();
        ArrayList<Winner> winners = db.getWinners();
        boolean isRecord = false;
        if (winners != null) {
            if (winners.size() < 10 && StaticUtils.getCount() > 0) isRecord = true;
            for (Winner w : winners) {
                if (StaticUtils.getCount() > w.getScore()) isRecord = true;
            }
        }
        return isRecord;
    }

    /**
     * Gets a list of top winners from the database,
     * and shows it in a HOFScene window.
     * @param mouseEvent
     */
    public void showRecords(MouseEvent mouseEvent) throws IOException {

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.getPrimaryStage());
        dialog.setTitle("Best results");
        Parent newRecWinRoot = FXMLLoader.load(getClass().getResource("GUI/HOFScene.fxml"));
        Scene recordsScene = new Scene(newRecWinRoot);
        dialog.setScene(recordsScene);
        dialog.show();
    }

    /**
     * Saves the new champion to the database
     * and closes the prompt window.
     * @param event
     */
    public void saveNewRecord(ActionEvent event) {
        DataBaseHelper db = new DataBaseHelper();
        db.saveRecord(nameTF.getText(), StaticUtils.getCount());
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
