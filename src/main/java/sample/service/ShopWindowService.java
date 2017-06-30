package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entity.Bill;
import sample.entity.Product;
import sample.repository.SqlRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class ShopWindowService {

    SqlRequest sqlRequest = new SqlRequest();

    public boolean buyProd(ObservableList<Bill> bill) {

        SqlRequest sqlRequest = new SqlRequest();
        String tableName = "bill";

        for (Bill bill1 : bill) {
            Date date = new Date();
            HashMap<String, Object> billMap = new HashMap<>();
            billMap.put("userId", bill1.getUserId());
            billMap.put("idProduct", bill1.getProductId());
            billMap.put("quantity", bill1.getQuantity());
            billMap.put("price", ( double ) (Math.round((bill1.getTotal() / bill1.getQuantity()) * 100)) / 100);
            billMap.put("total", ( double ) (Math.round(bill1.getTotal() * 100)) / 100);
            billMap.put("dataOfOrder", date.toString());

            return sqlRequest.insert(tableName, billMap);
        }
        return false;
    }

    public ObservableList<Product> tableView() throws SQLException {
        String tableName = "product";
        ResultSet resultSet = sqlRequest.selectAll(tableName);
        ObservableList<Product> products = FXCollections.observableArrayList();
        while (resultSet.next()) {
            Product product = new Product();
            product.setIdProd(resultSet.getInt(1));
            product.setNameProd(resultSet.getString(2));
            product.setDescriptionProd(resultSet.getString(3));
            product.setPrice(resultSet.getDouble(4));
            products.add(product);
        }
        return products;
    }
}
