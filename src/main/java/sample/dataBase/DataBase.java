package sample.dataBase;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entity.Product;

import java.sql.*;

public class DataBase {

    static Connection connection;
    static Statement statement;

    public static void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:db/peapole.sqlite");
        statement = connection.createStatement();
        System.out.println("You have join to data base of users.");
    }

    public static void close() throws SQLException {
        statement.close();
        connection.close();
    }

    public static void createTables() throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS user");
        statement.executeUpdate("CREATE TABLE user(userId INTEGER PRIMARY KEY AUTOINCREMENT, email STRING UNIQUE, password STRING, name STRING, dataOfRegistration STRING)");

        statement.executeUpdate("DROP TABLE IF EXISTS product");
        statement.executeUpdate("CREATE TABLE product(idProduct INTEGER PRIMARY KEY AUTOINCREMENT , nameProduct STRING, description STRING, price DOUBLE, userId INTEGER, dataOfCreation STRING)");

        statement.executeUpdate("DROP TABLE IF EXISTS bill");
        statement.executeUpdate("CREATE TABLE bill(idBill INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, idProduct INTEGER, quantity DOUBLE, price DOUBLE, total DOUBLE, dataOfOrder STRING)");
    }


    public static boolean insert(String sql) {

        try {
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet select(String sql) {

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ObservableList<Product> setTableProd(){
        ObservableList<Product> products = FXCollections.observableArrayList();
        try {
            ResultSet  resultSet = statement.executeQuery("SELECT * FROM product;");
            while (resultSet.next()){
                Product product = new Product();
                product.setIdProd(resultSet.getInt(1));
                product.setNameProd(resultSet.getString(2));
                product.setDescriptionProd(resultSet.getString(3));
                product.setPrice(resultSet.getDouble(4));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}

