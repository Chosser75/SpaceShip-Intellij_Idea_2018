package cannonGame;

/**
 * Describes a winner object.
 */
public class Winner implements Comparable{

    private String name;
    private int score;

    public Winner(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Object o) {
        Winner w = (Winner)o;
        if (this.getScore() < w.getScore()) return -1;
        if (this.getScore() > w.getScore()) return 1;
        return 0;
    }
}
