package com.zzn.generator2.utils;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * JDBC类型转Java类型
 * @author yangziran
 * @version 1.0 2014年10月20日
 */
public class JdbcTypeNameTranslator {

    private static Map<Integer, String> typeToName;
    private static Map<Integer, String> typeToJava;

    static {
        typeToName = new HashMap<>();
        typeToName.put(Types.ARRAY, "ARRAY");
        typeToName.put(Types.BIGINT, "BIGINT");
        typeToName.put(Types.BINARY, "BINARY");
        typeToName.put(Types.BIT, "BIT");
        typeToName.put(Types.BLOB, "BLOB");
        typeToName.put(Types.BOOLEAN, "BOOLEAN");
        typeToName.put(Types.CHAR, "CHAR");
        typeToName.put(Types.CLOB, "CLOB");
        typeToName.put(Types.DATALINK, "DATALINK");
        typeToName.put(Types.DATE, "DATE");
        typeToName.put(Types.DECIMAL, "DECIMAL");
        typeToName.put(Types.DISTINCT, "DISTINCT");
        typeToName.put(Types.DOUBLE, "DOUBLE");
        typeToName.put(Types.FLOAT, "FLOAT");
        typeToName.put(Types.INTEGER, "INTEGER");
        typeToName.put(Types.JAVA_OBJECT, "JAVA_OBJECT");
        typeToName.put(Types.LONGVARBINARY, "LONGVARBINARY");
        typeToName.put(Types.LONGVARCHAR, "LONGVARCHAR");
        typeToName.put(Types.NCHAR, "NCHAR");
        typeToName.put(Types.NCLOB, "NCLOB");
        typeToName.put(Types.NVARCHAR, "NVARCHAR");
        typeToName.put(Types.LONGNVARCHAR, "LONGNVARCHAR");
        typeToName.put(Types.NULL, "NULL");
        typeToName.put(Types.NUMERIC, "NUMERIC");
        typeToName.put(Types.OTHER, "OTHER");
        typeToName.put(Types.REAL, "REAL");
        typeToName.put(Types.REF, "REF");
        typeToName.put(Types.SMALLINT, "SMALLINT");
        typeToName.put(Types.STRUCT, "STRUCT");
        typeToName.put(Types.TIME, "TIME");
        typeToName.put(Types.TIMESTAMP, "TIMESTAMP");
        typeToName.put(Types.TINYINT, "TINYINT");
        typeToName.put(Types.VARBINARY, "VARBINARY");
        typeToName.put(Types.VARCHAR, "VARCHAR");

        typeToJava = new HashMap<>();
        typeToJava.put(Types.ARRAY, "Array");
        typeToJava.put(Types.BIGINT, "Long");
        typeToJava.put(Types.BINARY, "byte[]");
        typeToJava.put(Types.BIT, "Boolean");
        typeToJava.put(Types.BLOB, "Blob");
        typeToJava.put(Types.BOOLEAN, "Boolean");
        typeToJava.put(Types.CHAR, "String");
        typeToJava.put(Types.CLOB, "Clob");
        typeToJava.put(Types.DATALINK, "DATALINK");
        typeToJava.put(Types.DATE, "java.util.Date");
        typeToJava.put(Types.DECIMAL, "java.math.BigDecimal");
        typeToJava.put(Types.DISTINCT, "DISTINCT");
        typeToJava.put(Types.DOUBLE, "Double");
        typeToJava.put(Types.FLOAT, "Float");
        typeToJava.put(Types.INTEGER, "Integer");
        typeToJava.put(Types.JAVA_OBJECT, "Object");
        typeToJava.put(Types.LONGVARBINARY, "byte[]");
        typeToJava.put(Types.LONGVARCHAR, "String");
        typeToJava.put(Types.NCHAR, "String");
        typeToJava.put(Types.NCLOB, "NClob");
        typeToJava.put(Types.NVARCHAR, "String");
        typeToJava.put(Types.LONGNVARCHAR, "String");
        typeToJava.put(Types.NULL, "NULL");
        typeToJava.put(Types.NUMERIC, "java.math.BigDecimal");
        typeToJava.put(Types.OTHER, "Object");
        typeToJava.put(Types.REAL, "Double");
        typeToJava.put(Types.REF, "REF");
        typeToJava.put(Types.SMALLINT, "Short");
        typeToJava.put(Types.STRUCT, "STRUCT");
        typeToJava.put(Types.TIME, "Time");
        typeToJava.put(Types.TIMESTAMP, "java.util.Date");
        typeToJava.put(Types.TINYINT, "Byte");
        typeToJava.put(Types.VARBINARY, "byte[]");
        typeToJava.put(Types.VARCHAR, "String");

    }

    private JdbcTypeNameTranslator() {
        super();
    }

    public static String getJdbcTypeName(int jdbcType) {
        String answer = typeToName.get(jdbcType);
        if (answer == null) {
            answer = "OTHER";
        }

        return answer;
    }


    public static String getJavaType(int jdbcType) {
        String answer = typeToJava.get(jdbcType);
        if (answer == null) {
            answer = "Object";
        }

        return answer;
    }
}
