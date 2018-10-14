package cannonGame.GUI;

import cannonGame.SpaceShip;
import cannonGame.Controller;
import cannonGame.StaticUtils;
import cannonGame.targets.ITarget;
import cannonGame.targets.TargetBigMonster;
import cannonGame.targets.TargetRegular;
import cannonGame.targets.TargetSmallMonster;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Defines a game scene UI
 */
public class GameScene {
    private SpaceShip spaceShip;
    private Pane root;
    private Controller controller = new Controller();

    /**
     * Creates all needed for the game objects and starts animation
     * @param primaryStage:Stage
     */
    public void startGame(Stage primaryStage){
        // set StaticUtils to defaults
        StaticUtils.refresh();
        spaceShip = new SpaceShip();
        StaticUtils.countLbl = new Label(String.valueOf(StaticUtils.getCount()));
        root = new Pane();
        Scene scene = new Scene(root, 1000, 900);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.show();

        //creating the targets
        addTargets(0, 12, 1); // TargetRegular
        addTargets(520, 3, 2); // TargetSmallMonster
        addTargets(640, 3, 3); // TargetBigMonster

        spaceShip.addCannon(root.getWidth() / 2 - 50, root.getHeight()-80, root);
        StaticUtils.countLbl.setLayoutX(20);
        StaticUtils.countLbl.setLayoutY(root.getHeight() - 70);

        root.getChildren().addAll(spaceShip.getStvol(), spaceShip.getBody(), StaticUtils.countLbl);
        root.setOnKeyPressed(event -> controller.keyListener(event, spaceShip, root));
        root.setOnMouseMoved(event -> controller.mouseListener(event, spaceShip));
        root.setOnMouseClicked(event -> controller.mouseClick(event, spaceShip, root));

        at.get().start();
        root.requestFocus();
    }

    /**
     * Checks if there no targets remain
     * @return boolean
     */
    private boolean isAllTargetsHit(){
        for (ITarget target: StaticUtils.getTargetsList()) {
            if ((target.getHitsNeeded() - target.getHits()) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Makes all targets visible and active again after the last target of the previous set is hit.
     * Generates random speed and initial movement direction for each target and starts animation.
     */
    private void refreshTargets(){
        for (ITarget target: StaticUtils.getTargetsList()){
            target.setHitsNeeded(target.getHITS_NEED());
            target.setHits(0);
            int speed = 0;
            while (speed == 0){
                speed = StaticUtils.getR1().nextInt(9);
            }
            target.setSpeed(speed);
            target.getTarget().setX(StaticUtils.getR().nextInt((int)root.getWidth() - 100));
            target.getTarget().setOpacity(1);
            target.animationTargetStart();
        }
    }

    //  Animation main
    private SimpleObjectProperty<AnimationTimer> at = new SimpleObjectProperty<>(this, "at", new AnimationTimer() {
        @Override
        public void handle(long now) {
            StaticUtils.countLbl.setLayoutY(root.getHeight() - 70);
            spaceShip.moveCannon(root);
            if (isAllTargetsHit()) {
                refreshTargets();
            }
        }
    });

    /**
     * Creates a list of targets objects that will be used then in the game.
     * @param row1:double
     * @param qty:int
     * @param type:int
     */
    public void addTargets(double row1, int qty, int type){ // top row, quantity of targets, type: 1-regular, 2-small monster, 3-big monster
        double row = row1;
        double col;
        ITarget newTarget = null;
        for (int i = 0; i < qty; i++){
            switch(type){
                case 1:
                    newTarget = new TargetRegular(root);
                    break;
                case 2:
                    newTarget = new TargetSmallMonster(root);
                    break;
                case 3:
                    newTarget = new TargetBigMonster(root);
                    break;
            }

            if (i % 2 == 0){
                newTarget.setTargDir("right");
            } else {
                newTarget.setTargDir("left");
            }
            col = StaticUtils.getR().nextInt((int)root.getWidth() - 100);
            newTarget.setSpeed(StaticUtils.getR1());
            newTarget.addTarget(col, row);
            StaticUtils.getTargetsList().add(newTarget);
            root.getChildren().add(StaticUtils.getTargetsList().get(StaticUtils.getTargetsList().size()-1).getTarget());
            StaticUtils.getTargetsList().get(StaticUtils.getTargetsList().size()-1).animationTargetStart();
            row += 40;
        }
    }


}
