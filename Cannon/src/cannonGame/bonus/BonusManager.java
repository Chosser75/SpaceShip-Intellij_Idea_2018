package cannonGame.bonus;

import cannonGame.StaticUtils;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 * Creates and holds a list of bonus objects. Game uses these fixed quantity of
 * bonus objects in the que order to avoid creating excessive objects.
 */
public class BonusManager {

    private Pane root;
    private ArrayList<Bonus> bonuses = new ArrayList<>();
    private int num = 0;
    private Bonus bonus;
    private String bonusType = "noBonus";

    /**
     * Creates a list of bonuses
     * @param root:Pane
     */
    public void loadBonuses(Pane root){
        this.root = root;
        for (int i = 0; i < 20; i++){
            bonus = new Bonus(root, this);
            bonus.setNum(i);
            bonuses.add(bonus);
        }
    }

    /**
     * Returns next bonus in the que
     * @return bonus:Bonus
     */
    public Bonus getBonus(){
        bonus = bonuses.get(num);
        bonus.getBonusBody().setOpacity(1.0);
        ++num;
        if (num == 20) num = 0;
        return bonus;
    }

    /**
     * Generates a chance for the bonus to be dropped and
     * generates a type of bonus.
     * @return bonusType:String
     */
    public String tryBonus(){
        int chance = StaticUtils.getR().nextInt(10);
        switch (chance){
            case 2:
                bonusType = "missile"; // 10 shots by bulletMissile
                break;
            case 4:
                bonusType = "monster_target"; // all usual targets get hitsNeeded = 3
                break;
            case 1:
                bonusType = "multi_bullet";
                break;
            case 7:
                bonusType = "speed_up";
                break;
            case 3:
                bonusType = "speed_down";
                break;
            default:
                bonusType = "noBonus";
        }
        return bonusType;
    }
}
