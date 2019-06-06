package com.zzn.generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 开发者
 * nickName:大黄蜂
 * email:245655812@qq.com
 * github:https://github.com/bigbeef
 */

public class Template1 {

    /**
     * 作者
     */
    private static String author;
    /**
     * 类名
     */
    private static String date;
    /**
     * 类名
     */
    private static String domainName;
    /**
     * 类包
     */
    private static String packageName;
    /**
     * 项目路径
     */
    private static String sourcePath;
    /**
     * 输出路径
     */
    private static String targetPath;


    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<>(10);
        map.put("Mapper.java", "mapper/" + domainName + "Mapper.java");
        map.put("Service.java", "service/" + domainName + "Service.java");
        map.put("ServiceImpl.java", "service/impl/" + domainName + "ServiceImpl.java");
        map.put("Controller.java", "controller/" + domainName + "Controller.java");

        for (String templateFile : map.keySet()) {
            String targetFile = map.get(templateFile).toString();
            Properties pro = new Properties();
            pro.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
            pro.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
            pro.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, sourcePath);
            VelocityEngine ve = new VelocityEngine(pro);

            VelocityContext context = new VelocityContext();
            context.put("domainName", domainName);
            context.put("packageName", packageName);
            context.put("author", author);
            context.put("date", date);

            Template t = ve.getTemplate(templateFile, "UTF-8");

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
        InputStream in = Template1.class.getClassLoader()
                .getResourceAsStream("template1.properties");
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        author = pro.getProperty("author");
        date = pro.getProperty("date");
        domainName = pro.getProperty("domainName");
        packageName = pro.getProperty("packageName");
        templateDir = pro.getProperty("templateDir");
        resultDir = pro.getProperty("resultDir");
        sourcePath = System.getProperty("user.dir") + templateDir;
        targetPath = System.getProperty("user.dir")
                + resultDir + "/" + packageName.replace(".", "/");
    }

}
