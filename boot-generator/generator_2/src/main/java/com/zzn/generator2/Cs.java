package com.zzn.generator2;

import com.zzn.generator2.entity.Column;
import com.zzn.generator2.entity.Table;
import com.zzn.generator2.utils.ConnectionFactory;
import com.zzn.generator2.utils.GetTableConfig;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zengzhangni
 * @date 2019/12/19
 */
public class Cs {

    /**
     * 作者
     */
    private static String author;
    /**
     * 类包
     */
    private static String packageName;
    /**
     * 实体类包
     */
    private static String entityPackageName;
    /**
     * 表前缀
     */
    private static String tablePrefix;
    /**
     * 项目路径
     */
    private static String sourcePath;
    /**
     * 输出路径
     */
    private static String targetPath;


    public static void main(String[] args) throws Exception {
        List<Table> tables = GetTableConfig.getTableConfig();
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (Table table : tables) {
            es.submit(() -> {
                try {
                    cj(table.getSubJavaName(2), table.getCols(), table.getRemarks());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        es.shutdown();
    }


    private static void cj(String domainName, List<Column> columns, String remark) throws IOException {
        Map<String, Object> map = new HashMap<>(10);
        map.put("Controller.java", "controller/" + domainName + "Controller.java");
        map.put("Service.java", "service/" + domainName + "Service.java");
        map.put("ServiceImpl.java", "service/impl/" + domainName + "ServiceImpl.java");
        map.put("Mapper.java", "mapper/" + domainName + "Mapper.java");
        map.put("Model.java", "model/" + domainName + ".java");
        map.put("Mapper.xml", "xml/" + domainName + "Mapper.xml");


        for (String templateFile : map.keySet()) {
            String targetFile = map.get(templateFile).toString();
            Properties pro = new Properties();
            pro.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, sourcePath);
            pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
            pro.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
            VelocityEngine ve = new VelocityEngine(pro);

            VelocityContext context = new VelocityContext();
            context.put("packageName", packageName);
            context.put("entityPackageName", entityPackageName);
            context.put("tablePrefix", tablePrefix);
            context.put("domainName", domainName);
            context.put("author", author);
            context.put("date", new Date());
            context.put("list", columns);
            context.put("tableRemark", remark);

            org.apache.velocity.Template t = ve.getTemplate(templateFile, "UTF-8");

//            System.out.println(t);
//            System.out.println(t.getData());

            File file = new File(targetPath, targetFile);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outStream,
                    StandardCharsets.UTF_8);
            BufferedWriter sw = new BufferedWriter(writer);
            t.merge(context, sw);
            System.out.println(t.getData());
            sw.flush();
            sw.close();
            outStream.close();
            System.out.println("成功生成Java文件:"
                    + (targetPath + targetFile).replaceAll("/", "\\\\"));
        }
    }


    static {
        //生成代码后目录
        String resultDir;
        //模板路径
        String templateDir;
        Properties pro = new Properties();
        InputStream in = Cs.class.getClassLoader()
                .getResourceAsStream("newtemplate.properties");
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        author = pro.getProperty("author");
        packageName = pro.getProperty("packageName");
        entityPackageName = pro.getProperty("entityPackageName");
        tablePrefix = pro.getProperty("tablePrefix");
        templateDir = pro.getProperty("templateDir");
        resultDir = pro.getProperty("resultDir");
        sourcePath = System.getProperty("user.dir") + templateDir;
        targetPath = System.getProperty("user.dir")
                + resultDir + "/" + packageName.replace(".", "/");
    }

    public static void main1(String[] args) throws SQLException {

        Connection conn = ConnectionFactory.getConnection();
        ResultSet tables = conn.getMetaData().getTables(conn.getCatalog(), "", "s_%", new String[]{"TABLE"});
        while (tables.next()) {
            System.out.println(tables.getString("TABLE_NAME"));
            System.out.println(tables.getString("REMARKS"));
        }

    }
}
