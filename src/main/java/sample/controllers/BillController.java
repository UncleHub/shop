package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.utils.Context;

import java.io.IOException;
import java.util.Date;

public class BillController {
    public Label lblName;
    public Label lblDate;
    public Label lblTotal;
    public javafx.scene.control.ListView<String> listView;

    @FXML
    public void initialize() {
        lblName.setText("Name: " + Context.getInstance().getUser().getName());

        Date date = new Date();

        lblDate.setText("Data of order: "+date.toString());

        lblTotal.setText("Total: "+Context.getInstance().getTotal().toString());

        listView.setItems(Context.getInstance().getChek());

    }


    public void closeWindow(ActionEvent actionEvent) {
        Node source = ( Node ) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage window = ( Stage ) scene.getWindow();
        window.close();
    }

    public void backToWindowShop(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("view/ShopWindow.fxml"));
        Scene scene = new Scene(parent);
        Stage nextStage = ( Stage ) (( Node ) actionEvent.getSource()).getScene().getWindow();
        nextStage.setScene(scene);
        nextStage.setTitle("Shop window");
        nextStage.show();
    }
}
