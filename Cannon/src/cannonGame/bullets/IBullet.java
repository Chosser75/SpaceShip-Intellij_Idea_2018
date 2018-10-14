package cannonGame.bullets;

import cannonGame.SpaceShip;
import cannonGame.bonus.BonusManager;
import cannonGame.targets.ITarget;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Interface for the space ship's ammo.
 */
public interface IBullet {

    void shootBullet (double axisX, double axisY, Pane root, // should set starting coordinates and start bullet's animation
                      SpaceShip spaceShip, BulletsCartridge cartridge, BonusManager bonusManager);
    boolean checkForHit(ITarget target); // should check if the bullet touch any target
    ImageView getBullet(); // should return bullet's graphical node
    boolean isBulletReady(); // should check if bullet is ready to be launched
    void setBulletReady(boolean bulletReady); // should set the bullet ready or not ready to be launched
}
