package sample.service;

import sample.repository.dataBase.DataBase;
import sample.entity.User;
import sample.utils.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class LogInService {
    public boolean register(User user) throws SQLException {

        DataBase dataBase = new DataBase();

        String nameOfTable = "user";

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("password", user.getPassword());
        Set<Map.Entry<String, Object>> entries = userMap.entrySet();
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
            return true;
        } else {
            return false;
        }

    }
}
