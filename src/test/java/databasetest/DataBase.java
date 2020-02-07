package databasetest;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataBase {

    private static DataBase instance = null;

    private DataBase(){
    }

    public static DataBase getInstance(){
        if(instance != null)
            return instance;

        instance = new DataBase();
        instance.createDB();
        return instance;
    }

    private static Connection connection;

    private void createDB() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");

            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS person ( " +
                                         "uid TEXT PRIMARY KEY, " +
                                         "name TEXT NOT NULL, " +
                                         "age INTEGER)");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void dropPersonTable() throws SQLException{
        try{
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM person");
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void addPerson(String uid, String name, Integer age) throws SQLException {
        PreparedStatement insertStmt = connection.prepareStatement(
                "INSERT INTO person(uid, name, age) VALUES(?, ?, ?)");
        insertStmt.setString(1, uid);
        insertStmt.setString(2, name);
        insertStmt.setInt(3, age);
        insertStmt.executeUpdate();
    }

    public List<Integer> getPersonsAges() throws SQLException {
        Statement statement = connection.createStatement();
        List<Integer> ages = new ArrayList<Integer>();
        ResultSet rs = statement.executeQuery(
                "SELECT age FROM person ORDER BY age DESC");
        while (rs.next()) {
            ages.add(rs.getInt("age"));
        }
        return ages;
    }

}