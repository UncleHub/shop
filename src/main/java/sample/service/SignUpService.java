package sample.service;


import sample.dataBase.DataBase;
import sample.entity.User;
import sample.utils.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SignUpService {


    public boolean register(User user) throws SQLException {

        DataBase dataBase = new DataBase();
        Date date = new Date();
        String nameOfTable = "user";

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", user.getName());
        userMap.put("password", user.getPassword());
        userMap.put("email", user.getEmail());
        userMap.put("dataOfRegistration", date.toString());


        Set<Map.Entry<String, Object>> entries = userMap.entrySet();
        StringJoiner columLables = new StringJoiner(",");
        StringJoiner values = new StringJoiner("','", "'", "'");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            columLables.add(oneOfItem.getKey());
            values.add(oneOfItem.getValue().toString());
        }

        String sqlInsert = "INSERT INTO " + nameOfTable + " (" + columLables + ") VALUES (" + values + ");";

        boolean insert = dataBase.insert(sqlInsert);

        StringJoiner allCondition = new StringJoiner(" AND ");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            StringJoiner condition = new StringJoiner("='", "", "'");
            condition.add(oneOfItem.getKey());
            condition.add(oneOfItem.getValue().toString());
            allCondition.add(condition.toString());
        }
        String sqlSelect = "SELECT * FROM " + nameOfTable + " WHERE " + allCondition + ";";

        ResultSet select = dataBase.select(sqlSelect);

        if (select.next()) {

            Context instance = Context.getInstance();
            String email = select.getString("email");
            String password = select.getString("password");
            String name = select.getString("name");
            int userId = select.getInt("userId");
            instance.setUser(new User(email,password,name,userId));
        }

        return insert;
    }
}
