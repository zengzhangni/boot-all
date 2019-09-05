package com.zzn.excel.controller;

import com.zzn.excel.domain.User;
import com.zzn.excel.util.ExportExcelUtil;
import com.zzn.excel.util.ExportExcelWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zengzhangni
 * @date 2019/4/12
 */
@Api(tags = "表格测试")
@RestController
public class ExcelController {

    @ApiOperation(value = "获取表格")
    @GetMapping("/get/excel")
    public void getExcel(HttpServletRequest request, HttpServletResponse response) {
        List<User> list = getData();
        List<String> columnNames = Arrays.asList("ID", "姓名", "密码", "性别", "年龄", "金额", "是否拥有", "生日");
        String fileName = "用户";
        ExportExcelWrapper<User> util = new ExportExcelWrapper<>();
        util.exportExcel(fileName, fileName, columnNames, list, response, ExportExcelUtil.EXCEL_FILE_2003);
    }


    @ApiOperation(value = "获取表格")
    @GetMapping("/getExcel")
    public void getExcel2(HttpServletResponse response) {
        List<User> list = getData();
        List<String> columnNames = new ArrayList<>();
        Field[] fields = list.get(0).getClass().getDeclaredFields();
        for (Field field : fields) {
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            columnNames.add(annotation.value());
        }
        ApiModel apiModel = list.get(0).getClass().getAnnotation(ApiModel.class);
        String fileName = apiModel.value();
        ExportExcelWrapper<User> util = new ExportExcelWrapper<>();
        util.exportExcel(fileName, fileName, columnNames, list, response, ExportExcelUtil.EXCEL_FILE_2003);
    }


    private List<User> getData() {
        // 准备数据
        User user = new User();
        user.setId(1);
        user.setUserName("111");
        user.setPassWord("123456");
        user.setSex(0);
        user.setAge(18);
        user.setJe(999.99);
        user.setDs(false);
        user.setSr(new Date());
        User user1 = new User();
        user1.setId(2);
        user1.setUserName("222");
        user1.setPassWord("987654");
        user1.setSex(1);
        user1.setAge(20);
        user1.setJe(9989.99);
        user1.setDs(true);
        user1.setSr(new Date());
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        return list;
    }


    @ApiOperation("文件上传")
    @PostMapping("/sc")
    public void sc(@RequestBody MultipartFile file) {
        // 测试MultipartFile接口的各个方法
        System.out.println("文件类型ContentType=" + file.getContentType());
        System.out.println("文件组件名称Name=" + file.getName());
        System.out.println("文件原名称OriginalFileName=" + file.getOriginalFilename());
        System.out.println("文件大小Size=" + file.getSize() / 1024 + "KB");
    }
}
