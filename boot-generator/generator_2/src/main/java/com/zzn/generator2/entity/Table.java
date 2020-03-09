
package com.zzn.generator2.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 表类型
 */
@Data
public class Table {

    /**
     * 表名称
     */
    private String tableName;
    /**
     * Class名称
     */
    private String javaName;
    /**
     * 表备注
     */
    private String remarks;
    /**
     * 列集合
     */
    private List<Column> cols = new ArrayList<>();

    public String getSubJavaName(Integer sub) {
        return javaName.substring(sub);
    }

    public void addCols(Column column) {
        cols.add(column);
    }
}