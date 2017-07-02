package sample.service;

import sample.entity.Product;
import sample.repository.SqlRequest;
import sample.utils.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AdminShopWindowService {

    SqlRequest sqlRequest = new SqlRequest();

    public boolean updateProd(Product product, Integer prodId) throws SQLException {

        String name = "product";
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("nameProduct", product.getNameProd());
        productMap.put("description", product.getDescriptionProd());
        productMap.put("price", product.getPrice());

        return sqlRequest.update(name, prodId.toString(), productMap);
    }

    public boolean deleteProd(Product product) {
        String tableName = "product";
        SqlRequest sqlRequest = new SqlRequest();

        HashMap<String, Object> productMap = new HashMap();
        productMap.put("nameProduct", product.getNameProd());
        productMap.put("description", product.getDescriptionProd());
        productMap.put("price", product.getPrice());

        return sqlRequest.delete(tableName, productMap);
    }

    public Product createNewProd(Product product) throws SQLException {
        Date date = new Date();
        String tableName = "product";
        Product newProduct = product;
        String columns = "*";
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("nameProduct", product.getNameProd());
        productMap.put("description", product.getDescriptionProd());
        productMap.put("price", product.getPrice());
        productMap.put("userId", Context.getInstance().getUser().getUserId());
        productMap.put("dataOfCreation", date.toString());

        if (sqlRequest.insert(tableName, productMap)) {

            ResultSet resultSet = sqlRequest.selectWithConditions(tableName, columns, productMap);
            newProduct.setIdProd(resultSet.getInt("idProduct"));
                       
            return newProduct;
        } else {
            return newProduct;
        }
    }

    public ArrayList<Product> tableView() throws SQLException {
        String tableName = "product";
        ResultSet resultSet = sqlRequest.selectAll(tableName);
        ArrayList<Product> products = new ArrayList<>();
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
