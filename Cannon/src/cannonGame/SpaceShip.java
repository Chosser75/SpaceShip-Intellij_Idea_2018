package cannonGame;

import cannonGame.bonus.BonusManager;
import cannonGame.bullets.BulletsCartridge;
import cannonGame.bullets.IBullet;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Describes space ship object.
 */
public class SpaceShip {

    private ImageView body; // space ship's body
    private ImageView stvol; // space ship's gun
    private String shotDir = "down"; // direction of the gun's movement
    private String canDir = "right"; // direction of the spaceShip's movement
    private boolean isReady = true; // is space ship's weapon ready to make next shot
    private double axisX; // space ship's axis X coordinate
    private double axisY; // space ship's axis Y coordinate
    private boolean isAdded = false; // is space ship's graphical node is created
    private int bonusMissileActivator = 0; // number of missiles gotten with bonus
    private boolean isBonusMultiShotActive = false;
    private IBullet bullet;
    private BulletsCartridge cartridge = new BulletsCartridge();
    private BonusManager bonusManager = new BonusManager();
    private double speed = 3;

    /**
     * Creates and adds to the Pane space ship's graphical node
     * @param axisX:double
     * @param axisY:double
     * @param root:Pane
     */
    public void addCannon (double axisX, double axisY, Pane root){
        cartridge.loadCartridge(root);
        bonusManager.loadBonuses(root);
        if (isAdded == true) return;
        this.axisX = axisX;
        this.axisY = axisY;
        // The image cannon_body.png was downloaded from http://www.doublebrick.ru/forums/viewtopic.php?f=111&t=39071
        Image bodyImg = new Image("img/cannon_body.png");
        body = new ImageView(bodyImg);
        body.setX(axisX);
        body.setY(axisY);
        body.setFitWidth(100);
        body.setFitHeight(80);
        // The image cannon_stvol.png was downloaded from http://www.doublebrick.ru/forums/viewtopic.php?f=111&t=39071
        Image stvolImg = new Image("img/cannon_stvol.png");
        stvol = new ImageView(stvolImg);
        stvol.setX(axisX + 12);
        stvol.setY(axisY);
        stvol.setFitWidth(75);
        stvol.setFitHeight(50);
        isAdded = true;
    }

    /**
     * Returns space ship's body graphical node
     * @return ImageView
     */
    public ImageView getBody() {
        return body;
    }

    /**
     * Returns space ship's gun graphical node
     * @return ImageView
     */
    public ImageView getStvol() {
        return stvol;
    }

    /**
     * Sets space ship's movement direction ("right", "left" or "stop")
     * @param canDir:String
     */
    public void setCanDir(String canDir) {
        this.canDir = canDir;
    }

    /**
     * Returns isReady parameter
     * @return boolean
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * Activates bullet's (launches a IBullet object) and gun's animations.
     * @param root:Pane
     */
    public void fire(Pane root) {
        if (isReady()){
            stvol.setY(body.getY());
            playShot();
            if (isBonusMultiShotActive){ // activates multi shot bonus effect
                for (double i = getStvol().getX() - 450; i<= getStvol().getX() + 450; i += 150){
                    addBullet();
                    bullet.shootBullet(i, getStvol().getY(), root, this, cartridge, bonusManager);
                }
                isBonusMultiShotActive = false;
            } else {
                addBullet();
                bullet.shootBullet(getStvol().getX(), getStvol().getY(), root, this, cartridge, bonusManager);
            }
            atCannonShot.get().start();
        }
    }

    /**
     * Gets IBullet object from BulletsCartridge object
     */
    private void addBullet() {
        if (bonusMissileActivator > 0){
            bullet = cartridge.getBulletMissile();
            if (!isBonusMultiShotActive) bonusMissileActivator -= 1;
        } else {
            bullet = cartridge.getBulletRegular();
        }
        bullet.setBulletReady(false);
    }

    /**
     * Animates gun's movement during a shot
     */
    public void canShot(){
        isReady = false;
        if (shotDir == "down") {
            if (stvol.getY() < body.getY() + 30) {
                stvol.setY(stvol.getY() + 5);
            } else {
              shotDir = "up";
            }
        } else if (shotDir == "up") {
            if (stvol.getY() > body.getY()) {
                stvol.setY(stvol.getY() - 1);
            } else {
                shotDir = "down";
                atCannonShot.get().stop();
                isReady = true;
            }
        }
    }

    /**
     * Animates space ship's movement
     */
    public void moveCannon(Pane root) {
        body.setY(root.getHeight() - 80);
        switch (canDir) {
            case "stop":
                break;
            case "right":
                if (body.getX() < root.getWidth() - 100) {
                    body.setX(body.getX() + speed);
                    stvol.setX(stvol.getX() + speed);
                } else canDir = "left";
                break;
            case "left":
                if (body.getX() > 0) {
                    body.setX(body.getX() - speed);
                    stvol.setX(stvol.getX() - speed);
                } else canDir = "right";
                break;
        }
    }

    /**
     * Plays shot sound
     */
    public void playShot() {
        String shot;
        if (bonusMissileActivator > 0) {
            shot = "src/sounds/shot.mp3";
        } else {
            shot = "src/sounds/shot1.mp3";
        }
        File shotSound = new File(shot);
        if (shotSound.exists()) {
            Media hit = new Media(shotSound.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        }
    }

    //  Animation spaceShip shot
    public SimpleObjectProperty<AnimationTimer> atCannonShot = new SimpleObjectProperty<>(this, "atCannonShot", new AnimationTimer() {
        @Override
        public void handle(long now) {
            canShot();
        }
    });

    /**
     * Increases space ship's speed in 2 times if it is less or equal to normal speed
     * (it is normal or decreased by "speed_down" bonus (through speedDown() method)).
     */
    public void speedUp(){
        if (speed <= 3) speed *= 2;
    }

    /**
     * Decreases space ship's speed in 2 times if it is more or equal to normal speed
     * (it is normal or increased by "speed_up" bonus (through speedUp() method)).
     */
    public void speedDown(){
        if (speed >= 3) speed /= 2;
    }

    public double getX(){
        return body.getX();
    }

    public double getY(){
        return body.getY();
    }

    public int getBonusMissileActivator() {
        return bonusMissileActivator;
    }

    public void setBonusMissileActivator(int bonusMissileActivator) {
        this.bonusMissileActivator = bonusMissileActivator;
    }

    /**
     * Activates/deactivates the multi_shot bonus.
     * @param isBonusMultiShotActive
     */
    public void setBonusMultiShotActive(boolean isBonusMultiShotActive) {
        this.isBonusMultiShotActive = isBonusMultiShotActive;
    }

}
