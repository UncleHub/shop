package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.dataBase.DataBase;
import sample.entity.Bill;
import sample.entity.Product;
import sample.service.BillService;
import sample.service.BuyProdService;
import sample.utils.Context;

import java.io.IOException;

public class ShopWindowController {

    Stage stage = new Stage();
    ObservableList<Bill> bill = FXCollections.observableArrayList();
    @FXML
    public TableView<Product> tblProduct;

    @FXML
    public TableColumn<Product, String> clmProdName;
    @FXML
    public TableColumn<Product, String> clmProdDesc;
    @FXML
    public TableColumn<Product, Integer> clmProdId;
    @FXML
    public TableColumn<Product, Double> clmProdPrice;

    @FXML
    public TableView<Bill> tblBasket;
    @FXML
    public TableColumn<Bill, String> clmBskProd;
    @FXML
    public TableColumn<Bill, Double> clmBskQuan;

    @FXML
    public Label lblHello;

    @FXML
    public Label lblTotal;

    @FXML
    public Spinner spinner;

    @FXML
    public void initialize() {

        lblHello.setText("Hello " + Context.getInstance().getUser().getName());

        clmProdId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("idProd"));
        clmProdName.setCellValueFactory(new PropertyValueFactory<Product, String>("nameProd"));
        clmProdDesc.setCellValueFactory(new PropertyValueFactory<Product, String>("descriptionProd"));
        clmProdPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        tblProduct.setItems(DataBase.setTableProd());

        clmBskProd.setCellValueFactory(new PropertyValueFactory<Bill, String>("productName"));
        clmBskQuan.setCellValueFactory(new PropertyValueFactory<Bill, Double>("quantity"));
        tblBasket.setItems(bill);
        bill.addListener(new ListChangeListener<Bill>() {
            public void onChanged(Change<? extends Bill> c) {
                totalLabel();
            }
        });

    }

    public void addProdToBasket(ActionEvent actionEvent) {

        Product selectedItem = tblProduct.getSelectionModel().getSelectedItem();
        double quantity = Double.parseDouble(spinner.getValue().toString());
        Bill prod = new Bill(Context.getInstance().getUser().getUserId(), selectedItem.getIdProd(), quantity, selectedItem.getNameProd(), (selectedItem.getPrice() * quantity));
        bill.add(prod);
    }

    public void totalLabel() {

        double total = 0.0;
        for (Bill bill1 : bill) {
            total = total + bill1.getTotal();
            total = (double)(Math.round(total*100))/100;
        }
        Context.getInstance().setTotal(total);
        lblTotal.setText("Total: " + total);
    }
    public void removeProdFromBasket(ActionEvent actionEvent) {
        Bill selectedItem = tblBasket.getSelectionModel().getSelectedItem();
        bill.remove(selectedItem);
    }

    public void buyProd(ActionEvent actionEvent) throws IOException {
        BuyProdService buyProdService = new BuyProdService();
        if (buyProdService.buyProd(bill)){
            BillService billService = new BillService();
            Context.getInstance().setChek(billService.setBillList(bill));
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("view/Bill.fxml"));
            Scene scene = new Scene(parent);
            Stage nextStage = ( Stage ) (( Node ) actionEvent.getSource()).getScene().getWindow();
            nextStage.setScene(scene);
            nextStage.setTitle("Bill");
            nextStage.show();
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        Node source = ( Node ) actionEvent.getSource();
        Scene scene = source.getScene();
        Stage window = ( Stage ) scene.getWindow();
        window.close();
    }


}
