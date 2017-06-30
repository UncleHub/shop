package sample.repository;

import sample.repository.dataBase.DataBase;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class SqlRequest {

    DataBase dataBase = DataBase.getInstance();

    public boolean update(String tableName, String whereCondition, HashMap<String, Object> map) {

        Set<Map.Entry<String, Object>> entries = map.entrySet();

        StringJoiner allCondition = new StringJoiner(", ");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            StringJoiner condition = new StringJoiner("='", "", "'");
            condition.add(oneOfItem.getKey());
            condition.add(oneOfItem.getValue().toString());
            allCondition.add(condition.toString());
        }
        String sqlUpdate = "UPDATE " + tableName + " SET " + allCondition + " WHERE idProduct =" + whereCondition + ";";

        return dataBase.insert(sqlUpdate);
    }

    public ResultSet selectWithConditions(String tableName, String columns, HashMap<String, Object> map) {

        Set<Map.Entry<String, Object>> entries = map.entrySet();
        StringJoiner allCondition = new StringJoiner(" AND ");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            StringJoiner condition = new StringJoiner("='", "", "'");
            condition.add(oneOfItem.getKey());
            condition.add(oneOfItem.getValue().toString());
            allCondition.add(condition.toString());
        }
        String sqlSelect = "SELECT " + columns + " FROM " + tableName + " WHERE " + allCondition + ";";

        return dataBase.select(sqlSelect);
    }

    public ResultSet selectAll(String tableName) {

        String sqlSelect = "SELECT * FROM " + tableName + ";";

        return dataBase.select(sqlSelect);
    }

    public boolean insert(String tableName, HashMap<String, Object> map) {

        Set<Map.Entry<String, Object>> entries = map.entrySet();
        StringJoiner columnsLabels = new StringJoiner(",");
        StringJoiner values = new StringJoiner("','", "'", "'");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            columnsLabels.add(oneOfItem.getKey());
            values.add(oneOfItem.getValue().toString());
        }
        String sqlInsert = "INSERT INTO "+tableName+" (" + columnsLabels + ") VALUES (" + values + ");";

        return dataBase.insert(sqlInsert);

    }

    public boolean delete(String tableName, HashMap<String, Object> map) {

        Set<Map.Entry<String, Object>> entries = map.entrySet();

        StringJoiner allCondition = new StringJoiner("AND ");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            StringJoiner condition = new StringJoiner("='", "", "'");
            condition.add(oneOfItem.getKey());
            condition.add(oneOfItem.getValue().toString());
            allCondition.add(condition.toString());
        }
        String sqlDelete = "DELETE FROM "+tableName+" WHERE " + allCondition + ";";

        return dataBase.insert(sqlDelete);
    }
}
