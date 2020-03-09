/**
 *
 */
package com.boot.generator.mp.util;


import com.boot.generator.mp.config.PropertyHolder;
import com.boot.generator.mp.db.ConnectionFactory;
import com.boot.generator.mp.entity.Column;
import com.boot.generator.mp.entity.Table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取数据库表结构
 */
public class GetTableConfig {

    /**
     * 获取库表结构
     * @return
     * @throws SQLException
     * @author yangziran
     */
    public static List<Table> getTableConfig() throws Exception {
        return getDBTableConfig();
    }

    /**
     * 获取数据源为数据库的库表表结构
     * @return
     * @throws SQLException
     * @author yangziran
     */
    public static List<Table> getDBTableConfig() throws SQLException {


        // 获取到数据库连接
        Connection connection = ConnectionFactory.getConnection();

        // 处理表名称 支持全库生成 有以下几种模式:1、全库，%；2、多表，xx_%|xx_%,yy_%|xx_%,yy_bb|xx_aa,yy_bb
        String tableName = PropertyHolder.getJDBCProperty("table");
        String[] tableNames = tableName.split(",");

        List<Table> tableList = new ArrayList<>();
        for (String string : tableNames) {
            tableList.addAll(getTableList(connection, string.toLowerCase()));
        }

        // 关闭连接
        connection.close();


        return tableList;
    }

    /**
     * 处理配置文件中设定的表结构
     * @param connection
     * @param tableName
     * @return
     * @throws SQLException
     * @author 阳自然
     */
    private static List<Table> getTableList(Connection connection, String tableName) throws SQLException {

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet set = metaData.getSchemas(connection.getCatalog(), "");
        String schema = "";
        if (set.next()) {
            schema = set.getString(2);
        }
        ResultSet tables = metaData.getTables(connection.getCatalog(), schema, tableName, new String[]{"TABLE"});

        List<Table> tableList = new ArrayList<Table>();
        while (tables.next()) {
            // 获取表信息
            Table table = new Table();
            String TABLE_NAME = tables.getString("TABLE_NAME");
            table.setTableName(TABLE_NAME);
            table.setJavaName(StringUtility.convertTableNameToClass(TABLE_NAME.toLowerCase(), "_", false));
            tableList.add(table);
            // 获取到列集合
            ResultSet columns = metaData.getColumns(connection.getCatalog(), schema, TABLE_NAME, "%");
            while (columns.next()) {
                Column column = new Column();
                String COLUMN_NAME = columns.getString("COLUMN_NAME");
                if (COLUMN_NAME.indexOf("‘") == 0 && COLUMN_NAME.lastIndexOf("’") > 1) {
                    COLUMN_NAME = COLUMN_NAME.substring(1, COLUMN_NAME.length() - 1);
                }
                column.setSerial(String.valueOf(columns.getRow()));
                column.setColumnName(COLUMN_NAME);
                column.setJavaName(StringUtility.convertFieldToParameter(COLUMN_NAME.toLowerCase(), "_"));
                column.setSqlType(JdbcTypeNameTranslator.getJdbcTypeName(columns.getInt("DATA_TYPE")));
                column.setJavaType(JdbcTypeNameTranslator.getJavaType(columns.getInt("DATA_TYPE")));
                column.setRemarks(columns.getString("REMARKS"));
                column.setIsNotNull(columns.getString("IS_NULLABLE"));
                column.setLength(columns.getInt("COLUMN_SIZE"));
                table.addCols(column);
            }
            // 关闭连接
            columns.close();

            tableList.add(table);
        }


        // 关闭连接
        tables.close();
        return tableList;
    }

}