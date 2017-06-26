package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    @FXML
    public PasswordField fldPassword;
    @FXML
    public TextField fldEmail;
    @FXML
    public Label lblError;

    public void ok(ActionEvent actionEvent) throws IOException, SQLException {
        User user = new User(fldEmail.getText(), fldPassword.getText());
        LogInService logInService = new LogInService();
        if (logInService.register(user)) {

            if ((Context.getInstance().getUser().getEmail()).equals("admin@mail")) {
                Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("view/AdminShopWindow.fxml"));
                Scene scene = new Scene(parent);
                Stage nextStage = ( Stage ) (( Node ) actionEvent.getSource()).getScene().getWindow();
                nextStage.setScene(scene);
                nextStage.setTitle("Admin shop window");
                nextStage.show();

            } else {
                Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("view/ShopWindow.fxml"));
                Scene scene = new Scene(parent);
                Stage nextStage = ( Stage ) (( Node ) actionEvent.getSource()).getScene().getWindow();
                nextStage.setScene(scene);
                nextStage.setTitle("Shop window");
                nextStage.show();
            }
        } else lblError.setText("Incorrect email or password.");
    }

    public void switchWindow(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("view/SignUp.fxml"));
        Scene scene = new Scene(parent);
        Stage nextStage = ( Stage ) (( Node ) actionEvent.getSource()).getScene().getWindow();
        nextStage.setScene(scene);
        nextStage.setTitle("sign u username");
        nextStage.show();
    }

    public void closeWindow(ActionEvent actionEvent) {
        Node source = ( Node ) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage window = ( Stage ) scene.getWindow();
        window.close();
    }
}