package sample.service;


import sample.entity.User;
import sample.repository.SqlRequest;
import sample.utils.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class SignUpService {

    SqlRequest sqlRequest = new SqlRequest();

    public boolean register(User user) throws SQLException {

        Date date = new Date();
        String nameOfTable = "user";
        String columns = "*";

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", user.getName());
        userMap.put("password", user.getPassword());
        userMap.put("email", user.getEmail());
        userMap.put("dataOfRegistration", date.toString());
        sqlRequest.insert(nameOfTable, userMap);
        ResultSet select = sqlRequest.selectWithConditions(nameOfTable, columns, userMap);

        if (select.next()) {
            Context instance = Context.getInstance();
            String email = select.getString("email");
            String password = select.getString("password");
            String name = select.getString("name");
            int userId = select.getInt("userId");
            instance.setUser(new User(email, password, name, userId));
            return true;
        } else {
            return false;
        }
    }
}
