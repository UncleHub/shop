package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.entity.Product;
import sample.service.AdminShopWindowService;

import java.sql.SQLException;

public class AdminShopWindowController {


    public TableView<Product> tblProd;
    public TableColumn<Product, String> clmProdName;
    public TableColumn<Product, String> clmProdDesc;
    public TableColumn<Product, Double> clmProdPrice;
    public TableColumn<Product, Integer> clmProdId;

    public TextField fldName;
    public TextArea fldDescr;
    public Spinner spinner;
    public Label lblError;

    AdminShopWindowService adminShopWindowService = new AdminShopWindowService();

    ObservableList<Product> products = FXCollections.observableArrayList();



    public void initialize() throws SQLException {
        clmProdId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("idProd"));
        clmProdName.setCellValueFactory(new PropertyValueFactory<Product, String>("nameProd"));
        clmProdDesc.setCellValueFactory(new PropertyValueFactory<Product, String>("descriptionProd"));
        clmProdPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        tblProd.setEditable(true);
        products.addListener(new ListChangeListener<Product>() {
            public void onChanged(Change<? extends Product> c) {
                refreshTable();
            }
        });
        products.addAll(adminShopWindowService.tableView());
        tblProd.setItems(products);
    }

    public void closeWindow(ActionEvent actionEvent) {
        Node source = ( Node ) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage window = ( Stage ) scene.getWindow();
        window.close();
    }

    public void updateProd(ActionEvent actionEvent) throws SQLException {
        Product selectedItem = tblProd.getSelectionModel().getSelectedItem();
        products.remove(selectedItem);
        if (!(fldName.getText().isEmpty())) {
            selectedItem.setNameProd(fldName.getText());
        }
        if (!(fldDescr.getText().isEmpty())) {
            selectedItem.setDescriptionProd(fldDescr.getText());
        }
        if (!(spinner.getValue().toString().isEmpty())) {
            selectedItem.setPrice(Double.parseDouble(spinner.getValue().toString()));
        }
        Integer idProd = selectedItem.getIdProd();
        adminShopWindowService.updateProd(selectedItem, idProd);
        products.add(selectedItem);
    }

    public void deleteProd(ActionEvent actionEvent) throws SQLException {
        Product selectedItem = tblProd.getSelectionModel().getSelectedItem();
        if (adminShopWindowService.deleteProd(selectedItem)) {
            products.remove(selectedItem);
            tblProd.refresh();
        }
    }

    public void createNewProd(ActionEvent actionEvent) throws SQLException {
        lblError.setText("");
        if (!(fldDescr.getText().isEmpty() || fldDescr.getText().isEmpty() || spinner.getValue().toString().isEmpty())) {

            Product newProduct = new Product(fldName.getText(), fldDescr.getText(), Double.parseDouble(spinner.getValue().toString()));

            Product newInsertProd = adminShopWindowService.createNewProd(newProduct);

            if (newInsertProd != null) {
                products.add(newInsertProd);
                tblProd.refresh();
                lblError.setText("");
                fldName.clear();
                fldDescr.clear();
                spinner.getValueFactory().setValue("1.0");
            }
        } else lblError.setText("One of the fields is empty.");
    }


    public void refreshTable(){
        tblProd.getItems().removeAll();
        tblProd.setItems(products);
    }
}
