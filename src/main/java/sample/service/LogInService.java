package sample.service;

import sample.entity.User;
import sample.repository.SqlRequest;
import sample.utils.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class LogInService {

    public boolean logIn(User user) throws SQLException {

        SqlRequest sqlRequest = new SqlRequest();
        String nameOfTable = "user";
        String columns = "*";

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("password", user.getPassword());

        ResultSet select = sqlRequest.selectWithConditions(nameOfTable, columns, userMap);

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
