package cannonGame;

import cannonGame.targets.ITarget;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Random;

/**
 * Helper class with static through-application utils.
 */
public class StaticUtils {

    private static ArrayList<ITarget> targetsList = new ArrayList<>(); // set of target objects
    public static Label countLbl; // label node for the current score indication
    private static int count = 0; // current player's score
    private static Random r = new Random(47); // to set target's initial X-position
    private static Random r1 = new Random(74); // to set target's speed

    /**
     * Sets default values to some properties.
     */
    public static void refresh(){
        targetsList = new ArrayList<>();
        count = 0;
    }

    /**
     * Returns a list of target objects.
     * @return
     */
    public static ArrayList<ITarget> getTargetsList() {
        return targetsList;
    }

    /**
     * Returns current player's score.
     * @return
     */
    public static int getCount() {
        return count;
    }

    /**
     * Returns Random object for target positions.
     * @return
     */
    public static Random getR() {
        return r;
    }

    /**
     * Returns Random object for target speeds.
     * @return
     */
    public static Random getR1() {
        return r1;
    }

    public static void setTargetsList(ArrayList<ITarget> targetsList) {
        StaticUtils.targetsList = targetsList;
    }

    /**
     * Sets the current player's score.
     * @param count
     */
    public static void setCount(int count) {
        StaticUtils.count = count;
    }
}
