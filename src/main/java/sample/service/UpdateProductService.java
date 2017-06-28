package sample.service;

import sample.repository.dataBase.DataBase;
import sample.entity.Product;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class UpdateProductService {


    public boolean updateProd(Product product, int prodId) throws SQLException {
        DataBase dataBase = new DataBase();

        HashMap<String, Object> productMap = new HashMap<>();

        productMap.put("nameProduct", product.getNameProd());
        productMap.put("description", product.getDescriptionProd());
        productMap.put("price", product.getPrice());

        Set<Map.Entry<String, Object>> entries = productMap.entrySet();

        StringJoiner allCondition = new StringJoiner(", ");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            StringJoiner condition = new StringJoiner("='", "", "'");
            condition.add(oneOfItem.getKey());
            condition.add(oneOfItem.getValue().toString());
            allCondition.add(condition.toString());
        }

        String sqlUpdate = "UPDATE product SET " + allCondition + " WHERE idProduct =" + prodId + ";";
        return dataBase.insert(sqlUpdate);

    }
}
