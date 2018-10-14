package cannonGame;

import java.sql.*;
import java.util.ArrayList;

/**
 * Helper class responsible for all interactions with database.
 * Establishes connection with DB, reads and writes the data.
 * Database saves 10 best results.
 */
public class DataBaseHelper {

    private String url = "jdbc:sqlite:src/database/winners.db";

    /**
     * Gets from database and returns the list of top winners objects.
     * @return
     */
    public ArrayList<Winner> getWinners(){
        ArrayList<Winner> winners = new ArrayList<>();
        String sqlGetData = "SELECT name, score\r\n" +
                "FROM winners\r\n" +
                "ORDER BY score DESC;";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlGetData))
        {
            while (rs.next()) {
                winners.add(new Winner(rs.getString("name"),
                        rs.getInt("score")));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return winners;
    }

    /**
     * Saves new best result to database.
     * If there are already 10 results in the database,
     * the lowest result is being replaced.
     * @param name
     * @param count
     */
    public void saveRecord(String name, int count) {
        ArrayList<Winner> winners = getWinners();
        Winner w = winners.get(winners.size()-1);
        String oldName = w.getName();
        int oldScore = w.getScore();
        String sqlUpdate = "UPDATE winners\r\n" +
                "SET name = '" + name + "', score = " + count + "\r\n" +
                "WHERE name = '" + oldName + "' AND score = " + oldScore + ";";
        String sqlAdd = "INSERT INTO winners (name, score)\r\n" +
                " VALUES ('" + name + "', " + count + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement())
        {
            if (winners.size() == 10) {
                stmt.execute(sqlUpdate);
            } else {
                stmt.execute(sqlAdd);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
