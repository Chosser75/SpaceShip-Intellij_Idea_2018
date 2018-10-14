package cannonGame.bullets;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Creates and holds separate lists of different types of ammo objects. Game uses these fixed quantity of
 * ammo objects to avoid creating excessive objects.
 */
public class BulletsCartridge {
    private LinkedList<IBullet> bulletsRegular = new LinkedList<>(); //list of regular bullets
    private LinkedList<IBullet> bulletsMissile = new LinkedList<>(); //list of missiles
    private IBullet bullet;

    /**
     * Creates specified number of ammo objects and adds them to the related lists
     * @param root:Pane
     */
    public void loadCartridge(Pane root){
        for (int i = 0; i < 30; i++) {
            BulletRegular bulletRegular = new BulletRegular();
            bulletRegular.getBullet().setOpacity(0.0);
            root.getChildren().add(bulletRegular.getBullet());
            BulletMissile bulletMissile = new BulletMissile();
            bulletMissile.getBullet().setOpacity(0.0);
            root.getChildren().add(bulletMissile.getBullet());
            bulletsRegular.add(bulletRegular);
            bulletsMissile.add(bulletMissile);
        }
    }

    /**
     * Returns next regular bullet object from a list.
     * @return IBullet
     */
    public IBullet getBulletRegular(){
        bullet = bulletsRegular.removeFirst();
        bullet.getBullet().setOpacity(1.0);
        return bullet;
    }

    /**
     * Returns next missile object from a list.
     * @return IBullet
     */
    public IBullet getBulletMissile(){
        bullet = bulletsMissile.removeFirst();
        bullet.getBullet().setOpacity(1.0);
        return bullet;
    }

    /**
     * Adds regular bullet object to a list
     * @param br:IBullet
     */
    public void addBulletRegular(IBullet br){
        bulletsRegular.add(br);
    }

    /**
     * Adds missile object to a list
     * @param bm:IBullet
     */
    public void addBulletMissile(IBullet bm){
        bulletsMissile.add(bm);
    }

}
