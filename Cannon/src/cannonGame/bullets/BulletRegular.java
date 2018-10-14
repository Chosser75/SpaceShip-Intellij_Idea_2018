package cannonGame.bullets;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Describes a regular bullet object.
 */
public class BulletRegular extends Bullet{

    public BulletRegular(){
        // the image bullet_fire.png was downloaded from http://es.terraria.wikia.com/wiki/Cabezas_de_meteoro
        super.bulletImg = new Image("img/bullet_fire.png");
        super.bullet = new ImageView(bulletImg);
        super.ft = new FadeTransition(); // bullet's transition on hit
        super.bulletWidth = 20;
        super.bulletHeight = 28;
        super.isBulletReady = true;
        super.bulletType = "regular";
        super.ft.setNode(bullet);
        super.ft.setDuration(new Duration(50));
        super.ft.setFromValue(0.0);
        super.ft.setToValue(1.0);
        super.ft.setCycleCount(6);
        super.ft.setAutoReverse(true);
    }

}
