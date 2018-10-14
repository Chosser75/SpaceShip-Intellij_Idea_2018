package cannonGame.targets;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Describes TargetSmallMonster target type.
 */
public class TargetSmallMonster extends Target{

    public TargetSmallMonster(Pane root){
        super(root);
        super.speed = 0; // target's speed (points per iteration)
        super.ft1 = new FadeTransition(); // target's transition on hit
        super.targetWidth = 50;
        super.targetHeight = 35;
        super.HITS_NEED = 2;
        super.hitsNeeded = 2;
        super.hits = 0;
        // target_small_monster.jpg image was downloaded from https://uberpeople.net/threads/monster-vs-red-bull.200033/
        super.targetImg = new Image("img/target_small_monster.jpg");
    }

}
