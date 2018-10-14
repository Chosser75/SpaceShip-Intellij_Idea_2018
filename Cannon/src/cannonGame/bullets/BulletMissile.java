package cannonGame.bullets;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Describes a missile object.
 */
public class BulletMissile extends Bullet{

    // missile_bullet.png image was downloaded from http://moziru.com/explore/Missile%20clipart%20bullet/
    public BulletMissile(){
        super.bulletImg = new Image("img/missile_bullet.png");
        super.bullet = new ImageView(bulletImg);
        super.ft = new FadeTransition(); // bullet's transition on hit
        super.bulletWidth = 25;
        super.bulletHeight = 45;
        super.isBulletReady = true;
        super.bulletType = "missile";
        super.ft.setNode(bullet);
        super.ft.setDuration(new Duration(50));
        super.ft.setFromValue(0.0);
        super.ft.setToValue(1.0);
        super.ft.setCycleCount(6);
        super.ft.setAutoReverse(true);
    }

}
