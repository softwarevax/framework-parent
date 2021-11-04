package org.softwarevax.framework.mybatis.jdbc;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcManager implements JdbcTemplate {

    private Connection conn;

    public JdbcManager(JdbcProperties jdbcProperties) {
        try {
            Class.forName(jdbcProperties.getClassName());
            this.conn = DriverManager.getConnection(jdbcProperties.getUrl(), jdbcProperties.getUserName(), jdbcProperties.getPassword());
        } catch (ClassNotFoundException var1) {
            var1.printStackTrace();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }
    }

    @Override
    public List<Map<String, Object>> statement(String sql) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<Map<String, Object>> rows = new ArrayList<>();
            while (resultSet.next()) {
                // 遍历行
                Map<String, Object> row = new HashMap<>();
                for(int i = 1; i <= columnCount; i++) {
                    // 遍历列
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                rows.add(row);
            }
            return rows;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
           try {
               if(statement != null) {
                   statement.close();
               }
               if(resultSet != null) {
                   resultSet.close();
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
        }
        return null;
    }

    @Override
    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
