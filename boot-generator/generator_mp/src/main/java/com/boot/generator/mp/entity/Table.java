
package com.boot.generator.mp.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 表类型
 */
@Data
public class Table {

    /** 表名称 */
    private String tableName;
    /** Class名称 */
    private String javaName;
    /** 表备注 */
    private String remarks;
    /** 列集合 */
    private List<Column> cols = new ArrayList<>();

    /**
     * 取得 tableName
     * @return tableName String
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设定 tableName
     * @param tableName String
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 取得 javaName
     * @return javaName String
     */
    public String getJavaName() {
        return javaName;
    }

    public String getSubJavaName(Integer sub) {
        return javaName.substring(sub);
    }

    /**
     * 设定 javaName
     * @param javaName String
     */
    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }


    public void addCols(Column column) {
        cols.add(column);
    }
}