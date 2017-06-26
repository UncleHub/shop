package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.dataBase.DataBase;
import sample.entity.Product;
import sample.service.CreateNewProductService;
import sample.service.DeleteProdService;
import sample.service.UpdateProductService;

import java.sql.SQLException;

public class AdminShopWindowController {


    public TableView<Product> tblProd;
    public TableColumn<Product, String> clmProdName;
    public TableColumn<Product, String> clmProdDesc;
    public TableColumn<Product, Double> clmProdPrice;
    public TableColumn<Product, Integer> clmProdId;

    public TextField fldName;
    public TextArea fldDescr;
    @FXML
    public Spinner spinner;
    public Label lblError;

    @FXML
    public void initialize() {
        clmProdId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("idProd"));
        clmProdName.setCellValueFactory(new PropertyValueFactory<Product, String>("nameProd"));
        clmProdDesc.setCellValueFactory(new PropertyValueFactory<Product, String>("descriptionProd"));
        clmProdPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        tblProd.setItems(DataBase.setTableProd());
    }

    public void closeWindow(ActionEvent actionEvent) {
        Node source = ( Node ) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage window = ( Stage ) scene.getWindow();
        window.close();
    }

    public void refreshTableView() {
        tblProd.getItems().removeAll();
        tblProd.setItems(DataBase.setTableProd());
    }

    public void updateProd(ActionEvent actionEvent) throws SQLException {
        Product selectedItem = tblProd.getSelectionModel().getSelectedItem();
        Product product = new Product();
        if (fldName.getText().isEmpty()) {
            product.setNameProd(selectedItem.getNameProd());
        } else {
            product.setNameProd(fldName.getText());
        }
        if (fldDescr.getText().isEmpty()) {
            product.setDescriptionProd(selectedItem.getDescriptionProd());
        } else {
            product.setDescriptionProd(fldDescr.getText());
        }
        if (spinner.getValue().toString().isEmpty()) {
            product.setPrice(selectedItem.getPrice());
        } else {
            product.setPrice(Double.parseDouble(spinner.getValue().toString()));
        }
        int idProd = selectedItem.getIdProd();
        UpdateProductService updateProductService = new UpdateProductService();
        if (updateProductService.updateProd(product, idProd)) {
            refreshTableView();
        }
    }

    public void deleteProd(ActionEvent actionEvent) {
        Product selectedItem = tblProd.getSelectionModel().getSelectedItem();
        DeleteProdService deleteProdService = new DeleteProdService();
        if (deleteProdService.deleteProd(selectedItem)) {
            refreshTableView();
        }
    }

    public void createNewProd(ActionEvent actionEvent) {
        lblError.setText("");
        if (!(fldDescr.getText().isEmpty() || fldDescr.getText().isEmpty() || spinner.getValue().toString().isEmpty())) {
            Product product = new Product();
            product.setNameProd(fldName.getText());
            product.setDescriptionProd(fldDescr.getText());
            product.setPrice(Double.parseDouble(spinner.getValue().toString()));
            CreateNewProductService createNewProductService = new CreateNewProductService();
            if (createNewProductService.createNewProd(product)) {
                refreshTableView();
            }
        } else lblError.setText("One of the fields is empty.");
    }
}
