package cannonGame.targets;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Describes TargetBigMonster target type.
 */
public class TargetBigMonster extends Target{

    public TargetBigMonster(Pane root){
        super(root);
        super.speed = 0; // target's speed (points per iteration)
        super.ft1 = new FadeTransition(); // target's transition on hit
        super.targetWidth = 60;
        super.targetHeight = 38;
        super.HITS_NEED = 3;
        super.hitsNeeded = 3;
        super.hits = 0;
        // target_big_monster.jpg image was downloaded from
        // https://lekuva.net/131005/vnimanie-tezi-mesta-gamzhat-ot-opasni-bakterii.html
        super.targetImg = new Image("img/target_big_monster.jpg");
    }
}
