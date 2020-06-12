package Controls;
import java.sql.*;

public class MySQLite {
    Connection c = null;
    Statement stmt = null;
    String query = null;
    public MySQLite() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:PongGame.db");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void List(){
        try {
            query="SELECT Score FROM HighScores ORDER BY Score DESC";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                System.out.println(rs.getInt("Score"));
            }
        } catch (SQLException e) {
        }

    }
    public void Update (int x){
        int min=4000000;
        try {
            query = "SELECT Score FROM HighScores";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getInt("Score")<min){
                    min=rs.getInt("Score");
                }
            }
            if(min<x) {
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    if (rs.getInt("Score")==min){
                        query="UPDATE HighScores Set Score="+x+" WHERE Score IN( SELECT Score FROM HighScores WHERE Score="+min+" LIMIT 1)";
                    }
                }
                rs = stmt.executeQuery(query);
            }
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }
    public void closeConnection() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        MySQLite db = new MySQLite();
        db.List();
        db.Update(5000);
        db.List();
        db.closeConnection();
    }
}