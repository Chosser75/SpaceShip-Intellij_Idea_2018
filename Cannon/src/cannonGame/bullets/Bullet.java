package cannonGame.bullets;

import cannonGame.bonus.Bonus;
import cannonGame.SpaceShip;
import cannonGame.StaticUtils;
import cannonGame.bonus.BonusManager;
import cannonGame.targets.ITarget;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Abstract class for the space ship's ammo.
 */
public abstract class Bullet implements IBullet{

    protected Image bulletImg;
    protected ImageView bullet;
    protected FadeTransition ft; // bullet's transition on hit
    protected double bulletWidth;
    protected double bulletHeight;
    protected Pane root;
    protected SpaceShip spaceShip;
    protected boolean isBulletReady;
    protected BulletsCartridge cartridge;
    protected String bulletType;
    protected BonusManager bonusManager;
    protected Bonus bonus;

    /**
     * Sets starting coordinates and starts bullet's animation
     * @param axisX:double
     * @param axisY:double
     * @param root:Pane
     * @param spaceShip:SpaceShip
     * @param cartridge:BulletsCartridge
     * @param bonusManager:BonusManager
     */
    @Override
    public void shootBullet (double axisX, double axisY, Pane root, SpaceShip spaceShip, BulletsCartridge cartridge, BonusManager bonusManager){
        this.root = root;
        this.spaceShip = spaceShip;
        this.cartridge = cartridge;
        this.bonusManager = bonusManager;
        bullet.setFitWidth(bulletWidth);
        bullet.setFitHeight(bulletHeight);
        bullet.setX(axisX + 27);
        bullet.setY(axisY);
        atBullet.get().start();
    }

    /**
     * Plays sound of broken target
     */
    public void playStrike() {
        String strike = "src/sounds/strike.mp3";
        File strikeSound = new File(strike);
        if (strikeSound.exists()) {
            Media hit = new Media(strikeSound.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        }
    }

    //  Animation bullet
    SimpleObjectProperty<AnimationTimer> atBullet = new SimpleObjectProperty<>(this, "atBullet", new AnimationTimer() {
        @Override
        public void handle(long now) {
            bullet.setY(bullet.getY() - 5);
            //is the bullet out of the top of the screen
            if (bullet.getY() < -60) {
                atBullet.get().stop();
                isBulletReady = true;
                addBulletToCartridge();
                if (StaticUtils.getCount() > 0){
                    StaticUtils.setCount(StaticUtils.getCount() - 1);
                }
                StaticUtils.countLbl.setText(String.valueOf(StaticUtils.getCount()));
            }
            for (ITarget target: StaticUtils.getTargetsList()){
                // Check for hit
                if (checkForHit(target)) break;
            }
        }
    });

    /**
     * Checks if the bullet touch any target
     * @param target:ITarget
     * @return boolean
     */
    @Override
    public boolean checkForHit(ITarget target) {
        // if the target is hit
        if (bullet.getX() > target.getTarget().getX() - bulletWidth && bullet.getX() < target.getTarget().getX() + target.getTargetWidth() &&
                bullet.getY() > target.getTarget().getY() - bulletHeight/2 && bullet.getY() < target.getTarget().getY() + target.getTargetHeight() &&
                target.getHitsNeeded() > target.getHits()){
            playStrike();
            ft.play();
            atBullet.get().stop();
            isBulletReady = true;
            addBulletToCartridge();
            target.setHits(target.getHits() + 1);
            // if the target is destroyed
            if (target.getHitsNeeded() <= target.getHits()) {
                // try chance to get bonus
                checkBonus(target.getTarget().getX(), target.getTarget().getY());
                target.hitTransition(0.0, 1.0);
                target.animationTargetStop();
                StaticUtils.setCount(StaticUtils.getCount() + 5);
            } else {
                target.hitTransition(1.0, 0.0);
            }
            StaticUtils.countLbl.setText(String.valueOf(StaticUtils.getCount()));
            return true;
        }
        return false;
    }

    /**
     * Checks bonus chance and if it is, then launches a bonus.
     * @param axisX:double
     * @param axisY:double
     */
    private void checkBonus(double axisX, double axisY) {
        // try chance to get bonus
        String bonusType = bonusManager.tryBonus();
        // if bonus
        if (bonusType != "noBonus"){
            bonus = bonusManager.getBonus();
            bonus.launchBonus(bonusType, axisX, axisY, spaceShip);
        }
    }

    /**
     * Adds utilized bullet back to the bulletsCartridge
     */
    private void addBulletToCartridge(){
        if (bulletType == "regular"){
            cartridge.addBulletRegular(this);
        } else if (bulletType == "missile") {
            cartridge.addBulletMissile(this);
        }
    }

    /**
     * Returns bullet's graphical node
     * @return ImageView
     */
    public ImageView getBullet() {
        return bullet;
    }

    /**
     * Checks if bullet is ready to be launched
     * @return boolean
     */
    public boolean isBulletReady() {
        return isBulletReady;
    }

    /**
     * Sets the bullet ready or not ready to be launched
     * @param bulletReady
     */
    public void setBulletReady(boolean bulletReady) {
        isBulletReady = bulletReady;
    }
}
