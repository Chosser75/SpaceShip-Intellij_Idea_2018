package cannonGame.targets;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Describes Target regular type
 */
public class TargetRegular extends Target{

    public TargetRegular(Pane root){
        super(root);
        super.speed = 0; // target's speed (points per iteration)
        super.ft1 = new FadeTransition(); // target's transition on hit
        super.targetWidth = 50;
        super.targetHeight = 35;
        super.HITS_NEED = 1;
        super.hitsNeeded = 1;
        super.hits = 0;
        super.targetImg = new Image("img/target_regular.jpg");
    }

}
