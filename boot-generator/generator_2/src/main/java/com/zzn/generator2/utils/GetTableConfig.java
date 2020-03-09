/**
 *
 */
package com.zzn.generator2.utils;


import com.zzn.generator2.config.PropertyHolder;
import com.zzn.generator2.entity.Column;
import com.zzn.generator2.entity.Table;
import org.apache.commons.lang.StringUtils;

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

    public static void main(String[] args) throws Exception {
        System.out.println(getTableConfig().size());
    }

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

        List<Table> tableList = getTableList(connection, tableName.toLowerCase());

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
     */
    private static List<Table> getTableList(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String schema = "";
        ResultSet tables = metaData.getTables(connection.getCatalog(), schema, tableName, new String[]{"TABLE"});

        List<Table> tableList = new ArrayList<>();
        while (tables.next()) {
            // 获取表信息
            Table table = new Table();
            String newTableName = tables.getString("TABLE_NAME");
            table.setTableName(newTableName);
            table.setRemarks(tables.getString("REMARKS"));
            table.setJavaName(pj(newTableName.toLowerCase(), "_"));
            // 获取到列集合
            ResultSet columns = metaData.getColumns(connection.getCatalog(), schema, newTableName, "%");
            while (columns.next()) {
                Column column = new Column();
                String columnName = columns.getString("COLUMN_NAME");
                if (columnName.indexOf("‘") == 0 && columnName.lastIndexOf("’") > 1) {
                    columnName = columnName.substring(1, columnName.length() - 1);
                }
                column.setSerial(String.valueOf(columns.getRow()));
                column.setJavaName(pj(columnName.toLowerCase(), "_"));
                column.setColumnName(columnName);
                column.setSqlType(JdbcTypeNameTranslator.getJdbcTypeName(columns.getInt("DATA_TYPE")));
                column.setJavaType(JdbcTypeNameTranslator.getJavaType(columns.getInt("DATA_TYPE")));
                column.setRemarks(columns.getString("REMARKS"));
                column.setLength(columns.getInt("COLUMN_SIZE"));
                column.setIsNotNull(columns.getString("IS_NULLABLE"));
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

    private static String pj(String name, String op) {
        StringBuilder builder;
        if (StringUtils.isNotBlank(op)) {
            builder = new StringBuilder();

            String[] split = name.split(op);
            for (String s : split) {
                builder.append(captureName(s));
            }
        } else {
            builder = new StringBuilder(name);
        }

        return builder.toString();
    }

    private static String captureName(String str) {
        System.out.println(str);
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
