package cannonGame.bonus;

import cannonGame.SpaceShip;
import cannonGame.StaticUtils;
import cannonGame.targets.ITarget;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;

/**
 * Describes bonus object.
 */
public class Bonus {

    public Bonus(Pane root, BonusManager bonusManager){
        this.root = root;
        bonusBody.setOpacity(0.0);
        root.getChildren().add(bonusBody);
        this.bonusManager = bonusManager;
        ft.setNode(bonusBody);
        ft.setDuration(new Duration(1000));
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
    }

    private String bonusType;
    // The image bonus.png was downloaded from
    // https://vignette.wikia.nocookie.net/fantendo/images/a/ab/%C2%BF_Block.png/revision/latest/scale-to-width-down/240?cb=20100506164812
    private Image bonusImg = new Image("img/bonus.png");
    private ImageView bonusBody = new ImageView(bonusImg);
    private double axisX;
    private double axisY;
    private double width = 100;
    private Pane root;
    private FadeTransition ft = new FadeTransition();
    private SpaceShip spaceShip;
    private BonusManager bonusManager;
    private int num; // bonus's index number in BonusManager's set of bonuses

    /**
     * shows the bonus box on the screen and starts its animation
     * @param bonusType:String
     * @param axisX:double
     * @param axisY:double
     * @param spaceShip:SpaceShip
     */
    public void launchBonus(String bonusType, double axisX, double axisY, SpaceShip spaceShip) {
        this.bonusType = bonusType;
        this.axisX = axisX;
        this.axisY = axisY;
        this.spaceShip = spaceShip;
        bonusBody.setFitHeight(width);
        bonusBody.setFitWidth(width);
        bonusBody.setX(axisX);
        bonusBody.setY(axisY);
        atBonus.get().start();
    }

    //  Animation bonus
    SimpleObjectProperty<AnimationTimer> atBonus = new SimpleObjectProperty<>(this, "atBullet", new AnimationTimer() {
        @Override
        public void handle(long now) {
            bonusBody.setY(bonusBody.getY() + 1);
             if ((bonusBody.getY() > (root.getHeight() - 170)) && ((bonusBody.getX() + width) > spaceShip.getX()) && (bonusBody.getX() < (spaceShip.getX() + 100))){
                // bonus is picked up
                bonusPickUp();
             }else if (bonusBody.getY() > root.getHeight()) {
                atBonus.get().stop();
             }
        }
    });

    /**
     * applies related bonus effect if the bonus is picked up by the space ship
     */
    private void bonusPickUp() {
        atBonus.get().stop();
        ft.play();
        playSound();
        if (bonusType == "missile") {
            spaceShip.setBonusMissileActivator(spaceShip.getBonusMissileActivator() + 10);
        } else if (bonusType == "monster_target") {
            for (ITarget target: StaticUtils.getTargetsList()){
                if (target.getHitsNeeded() < 3 && target.getHitsNeeded() > target.getHits()) {
                    target.setHitsNeeded(target.getHitsNeeded() + 1);
                    RotateTransition rt = new RotateTransition(Duration.millis(1000), target.getTarget());
                    rt.setByAngle(360);
                    rt.play();
                }
            }
        } else if (bonusType == "multi_bullet"){
            spaceShip.setBonusMultiShotActive(true);
        } else if (bonusType == "speed_up"){
            spaceShip.speedUp();
        } else if (bonusType == "speed_down"){
            spaceShip.speedDown();
        }
    }

    /**
     * plays sound if the bonus box is picked up by the space ship
     */
    private void playSound(){
        // the sound in zvuk_bonus.mp3 was downloaded from http://poiskm.co/show/звук-из-марио
        File bonusSound = new File("src/sounds/zvuk_bonus.mp3");
        Media hit = new Media(bonusSound.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        if (bonusSound.exists()) {
            mediaPlayer.play();
        }
    }

    /**
     * Returns ImageView of the bonus
     * @return ImageView
     */
    public ImageView getBonusBody() {
        return bonusBody;
    }

    /**
     * Sets the bonus's index number in BonusManager's set of bonuses
     * @param num:int
     */
    public void setNum(int num) {
        this.num = num;
    }

//    public double getAxisX() {
//        return axisX;
//    }
//
//    public void setAxisX(double axisX) {
//        this.axisX = axisX;
//    }
//
//    public double getAxisY() {
//        return axisY;
//    }
//
//    public void setAxisY(double axisY) {
//        this.axisY = axisY;
//    }
//
//    public double getWidth() {
//        return width;
//    }
//
//    public void setWidth(double width) {
//        this.width = width;
//    }

//    public int getNum() {
//    return num;
//}

}

