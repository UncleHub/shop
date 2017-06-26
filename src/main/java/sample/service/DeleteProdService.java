package sample.service;

import sample.dataBase.DataBase;
import sample.entity.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class DeleteProdService {

    public boolean deleteProd(Product product) {

        HashMap<String, Object> productMap = new HashMap();


        productMap.put("nameProduct", product.getNameProd());
        productMap.put("description", product.getDescriptionProd());
        productMap.put("price", product.getPrice());
        DataBase dataBase = new DataBase();

        Set<Map.Entry<String, Object>> entries = productMap.entrySet();

        StringJoiner allCondition = new StringJoiner("AND ");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            StringJoiner condition = new StringJoiner("='", "", "'");
            condition.add(oneOfItem.getKey());
            condition.add(oneOfItem.getValue().toString());
            allCondition.add(condition.toString());
        }
        String sqlDelete = "DELETE FROM product WHERE " + allCondition + ";";
        return dataBase.insert(sqlDelete);
    }
}
