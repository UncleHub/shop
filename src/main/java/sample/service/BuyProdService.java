package sample.service;

import javafx.collections.ObservableList;
import sample.repository.dataBase.DataBase;
import sample.entity.Bill;

import java.util.*;

public class BuyProdService {

    public boolean buyProd(ObservableList<Bill> bill) {
       boolean resultOfInsert = false;

        for (Bill bill1 : bill) {

            DataBase dataBase = new DataBase();
            Date date = new Date();
            String nameOfTable = "bill";
            HashMap<String, Object> billMap = new HashMap<>();
            billMap.put("userId", bill1.getUserId());
            billMap.put("idProduct", bill1.getProductId());
            billMap.put("quantity", bill1.getQuantity());
            billMap.put("price", (double)(Math.round((bill1.getTotal()/bill1.getQuantity())*100))/100);
            billMap.put("total", (double)(Math.round(bill1.getTotal()*100))/100);
            billMap.put("dataOfOrder", date.toString());

            Set<Map.Entry<String, Object>> entries = billMap.entrySet();

            StringJoiner columLables = new StringJoiner(",");
            StringJoiner values = new StringJoiner("','", "'", "'");
            for (Map.Entry<String, Object> oneOfItem : entries) {
                columLables.add(oneOfItem.getKey());
                values.add(oneOfItem.getValue().toString());
            }
            String sqlInsert = "INSERT INTO " + nameOfTable + " (" + columLables + ") VALUES (" + values + ");";

            resultOfInsert = dataBase.insert(sqlInsert);
        }
        return resultOfInsert;
    }
}
