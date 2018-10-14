package cannonGame.targets;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Random;

/**
 * Abstract class for targets
 */
public abstract class Target implements ITarget{

    public Target(Pane root){
        this.root = root;
    }

    protected ImageView targetBody;
    protected String targDir; // target's movement initial direction (left/right)
    protected int speed; // target's speed (points per iteration)
    protected FadeTransition ft1; // target's transition on hit
    protected static Pane root;
    protected double targetWidth;
    protected double targetHeight;
    protected int HITS_NEED; // initial number of hits needed to destroy this target
    protected int hitsNeeded; // current number of hits needed to destroy this target(can be increased by bonus effect)
    protected int hits; // number of bullets touched this target
    protected Image targetImg;

    /**
     * Creates target's ImageView object and sets specific coordinates.
     * @param axisX:double
     * @param axisY:double
     */
    @Override
    public void addTarget (double axisX, double axisY){
        targetBody = new ImageView(targetImg);
        targetBody.setFitHeight(targetHeight);
        targetBody.setFitWidth(targetWidth);
        targetBody.setX(axisX);
        targetBody.setY(axisY);
    }

    //  Animation targets
    public SimpleObjectProperty<AnimationTimer> atTargets = new SimpleObjectProperty<>(this, "atTargets", new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (targDir == "right"){
                if (targetBody.getX() < root.getWidth() - 50){
                    targetBody.setX(targetBody.getX() + speed);
                } else {
                    targDir = "left";
                }
            } else if (targDir == "left") {
                if (targetBody.getX() > 0) {
                    targetBody.setX(targetBody.getX() - speed);
                } else {
                    targDir = "right";
                }
            }
        }
    });

    /**
     * Defines and plays target's fade transition
     * @param fadeFrom:double
     * @param fadeTo:double
     */
    public void hitTransition(double fadeFrom, double fadeTo){
        ft1.setNode(targetBody);
        ft1.setDuration(new Duration(40));
        ft1.setFromValue(fadeFrom);
        ft1.setToValue(fadeTo);
        ft1.setCycleCount(6);
        ft1.setAutoReverse(true);
        ft1.play();
    }

    /**
     * Sets target's movement direction ("left" or "right")
     * @param targDir:String
     */
    @Override
    public void setTargDir(String targDir) {
        this.targDir = targDir;
    }

    /**
     * Returns target's ImageView object
     * @return ImageView
     */
    @Override
    public ImageView getTarget() {
        return targetBody;
    }

    /**
     * Sets speed of target's movement (pixels/animation iteration)
     * @param r1:Random
     */
    @Override
    public void setSpeed(Random r1) {
        while (speed == 0) {
            speed = r1.nextInt(9);
        }
    }

    /**
     * Starts target's animation
     */
    @Override
    public void animationTargetStart(){
        atTargets.get().start();
    }

    /**
     * Stops target's animation
     */
    @Override
    public void animationTargetStop(){
        atTargets.get().stop();
    }

    /**
     * Return target's width
     * @return double
     */
    public double getTargetWidth() {
        return targetWidth;
    }

    /**
     * Return target's height
     * @return double
     */
    public double getTargetHeight() {
        return targetHeight;
    }

    /**
     * Returns hitsNeeded param
     * @return int
     */
    public int getHitsNeeded() {
        return hitsNeeded;
    }

    /**
     * Sets hitsNeeded param
     * @param hn:int
     */
    @Override
    public void setHitsNeeded(int hn) {
        hitsNeeded = hn;
    }

    /**
     * Returns number of hits were made by bullets and/or missiles
     * @return int
     */
    public int getHits() {
        return hits;
    }

    /**
     * Sets number of hits were made by bullets and/or missiles
     * @param hits:int
     */
    public void setHits(int hits) {
        this.hits = hits;
    }

    /**
     * Sets target's movement speed (pixels per animation iteration)
     * @param speed:int
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Returns initial number of hits needed to destroy this target
     * @return int
     */
    public int getHITS_NEED() {
        return HITS_NEED;
    }
}
