package sample.service;

import sample.repository.dataBase.DataBase;
import sample.entity.Product;
import sample.utils.Context;

import java.util.*;

public class CreateNewProductService {

    public boolean createNewProd(Product product) {

        DataBase dataBase = new DataBase();
        Date date = new Date();

        String nameOfTable = "product";

        HashMap<String, Object> productMap = new HashMap<>();

        productMap.put("nameProduct", product.getNameProd());
        productMap.put("description", product.getDescriptionProd());
        productMap.put("price", product.getPrice());
        productMap.put("userId", Context.getInstance().getUser().getUserId());
        productMap.put("dataOfCreation", date.toString());


        Set<Map.Entry<String, Object>> entries = productMap.entrySet();
        StringJoiner columLables = new StringJoiner(",");
        StringJoiner values = new StringJoiner("','", "'", "'");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            columLables.add(oneOfItem.getKey());
            values.add(oneOfItem.getValue().toString());
        }

        String sqlInsert = "INSERT INTO " + nameOfTable + " (" + columLables + ") VALUES (" + values + ");";

        return dataBase.insert(sqlInsert);


    }
}
