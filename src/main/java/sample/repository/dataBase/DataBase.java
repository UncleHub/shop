package sample.repository.dataBase;


import java.sql.*;

public class DataBase {

    Connection connection = null;
    Statement statement = null;


    static DataBase instance;

    private DataBase() {
    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        instance.connection = DriverManager.getConnection("jdbc:sqlite:db/ShopDataBase.sqlite");
        instance.statement = instance.connection.createStatement();
        System.out.println("You have join to data base of users.");
    }

    public void close() throws SQLException {
        instance.statement.close();
        instance.connection.close();
    }

    public void createTables() throws SQLException {
        instance.statement.executeUpdate("DROP TABLE IF EXISTS user");
        instance.statement.executeUpdate("CREATE TABLE user(userId INTEGER PRIMARY KEY AUTOINCREMENT, email STRING UNIQUE, password STRING, name STRING, dataOfRegistration STRING)");

        instance.statement.executeUpdate("DROP TABLE IF EXISTS product");
        instance.statement.executeUpdate("CREATE TABLE product(idProduct INTEGER PRIMARY KEY AUTOINCREMENT , nameProduct STRING, description STRING, price DOUBLE, userId INTEGER, dataOfCreation STRING)");

        instance.statement.executeUpdate("DROP TABLE IF EXISTS bill");
        instance.statement.executeUpdate("CREATE TABLE bill(idBill INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, idProduct INTEGER, quantity DOUBLE, price DOUBLE, total DOUBLE, dataOfOrder STRING)");
    }


    public boolean insert(String sql) {
        try {
            return instance.statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet select(String sql) {
        try {
            return instance.statement.executeQuery(sql);
        } catch (SQLException e) {
            return null;
        }
    }
}

