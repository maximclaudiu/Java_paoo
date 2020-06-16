package Controls;
import java.sql.*;

public class MySQLite {
    Connection c = null;
    Statement stmt = null;
    String query = null;
    public MySQLite() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:SpaceGame.db");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public ResultSet Generate_scores (String abc){
        // abc reprezinta comentarii suplimentare ce pot fi adaugate la comenzile Query pentru a ordona lista ce va fi afisata.
        //      ORDER BY * ASC/DESC
        ResultSet rs= null;
        try {
            query = "SELECT Score FROM HighScores " + abc;

            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            return rs;
        }
        catch (SQLException e) { }
        return rs;
    }
    public void List(){
        ResultSet rs= this.Generate_scores("");
        try {
          while (rs.next()) {
              System.out.println(rs.getInt("Score"));
          }
        }catch(SQLException e){ }
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
                    if (rs.getInt("Score")==min) {
                        query="UPDATE HighScores Set Score="+x+" WHERE rowid= (SELECT MIN(rowid) FROM HighScores WHERE Score ="+min+")";
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

}