package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entity.Bill;

import java.util.StringJoiner;

public class BillService {

    public ObservableList<String> setBillList(ObservableList<Bill> bill) {
        ObservableList<String> chek = FXCollections.observableArrayList();
        for (Bill bill1 : bill) {
            StringJoiner rows = new StringJoiner("\t");
            rows.add(bill1.getProductName()).add((bill1.getQuantity() + " * " + bill1.getTotal() / bill1.getQuantity())).add("= " + bill1.getTotal());
            chek.add(rows.toString());
        }
        return chek;
    }
}
