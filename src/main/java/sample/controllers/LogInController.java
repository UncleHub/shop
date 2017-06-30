package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.entity.User;
import sample.service.LogInService;
import sample.utils.Context;

import java.io.IOException;
import java.sql.SQLException;

public class LogInController {

    public PasswordField fldPassword;

    public TextField fldEmail;

    public Label lblError;

    LogInService logInService = new LogInService();

    public void ok(ActionEvent actionEvent) throws IOException, SQLException {

        User user = new User(fldEmail.getText(), fldPassword.getText());

        if (logInService.logIn(user)) {

            if ((Context.getInstance().getUser().getEmail()).equals("admin@mail")) {
                setWindow("view/AdminShopWindow.fxml", "Admin shop window", actionEvent);
            } else {
                setWindow("view/ShopWindow.fxml","Shop window",actionEvent);
            }
        } else lblError.setText("Incorrect email or password.");
    }

    public void switchWindow(ActionEvent actionEvent) throws IOException {

        setWindow("view/SignUp.fxml","sign u username",actionEvent);
    }

    public void closeWindow(ActionEvent actionEvent) {
        Node source = ( Node ) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage window = ( Stage ) scene.getWindow();
        window.close();
    }

    public void setWindow(String name, String title, ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(name));
        Scene scene = new Scene(parent);
        Stage nextStage = ( Stage ) (( Node ) actionEvent.getSource()).getScene().getWindow();
        nextStage.setScene(scene);
        nextStage.setTitle(title);
        nextStage.show();
    }
}