package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.repository.dataBase.DataBase;

import java.sql.SQLException;

public class EntryPoint extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("view/SignUp.fxml"));
        Pane pane = ( Pane ) fxmlLoader.load();
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Sign up username");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        DataBase dataBase = DataBase.getInstance();
        try {
            dataBase.createConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
