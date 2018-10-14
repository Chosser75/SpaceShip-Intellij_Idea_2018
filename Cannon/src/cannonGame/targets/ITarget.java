package cannonGame.targets;

import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Interface for targets
 */
public interface ITarget {

    void addTarget(double axisX, double axisY); // should create target's ImageView object and sets specific coordinates.
    void setTargDir(String targDir); // should set target's movement direction ("left" or "right")
    void setSpeed(Random r1); // should set speed of target's movement (pixels/animation iteration)
    ImageView getTarget(); // should return target's ImageView object
    double getTargetWidth(); // should return target's width
    double getTargetHeight(); // should return target's height
    void hitTransition(double fadeFrom, double fadeTo); // should define and plays target's fade transition
    void animationTargetStart(); // should start target's animation
    void animationTargetStop(); // should stop target's animation
    int getHitsNeeded(); // should return hitsNeeded param
    void setHitsNeeded(int hn); // should set hitsNeeded param
    int getHits(); // should return number of hits were made by bullets and/or missiles
    void setHits(int hits); // should set number of hits were made by bullets and/or missiles
    void setSpeed(int speed); // should set target's movement speed (pixels per animation iteration)
    int getHITS_NEED(); // should return initial number of hits needed to destroy this target
}
