package com.zzn.grou.swagger.controller1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户中心")
@RestController
@RequestMapping("user")
public class UserController {

    @ApiOperation(value = "登录")
    @GetMapping("/login")
    public void login1() {
    }

    @ApiOperation(value = "添加")
    @PostMapping("/insert")
    public void insert() {
    }

    @ApiOperation(value = "修改")
    @PutMapping("/edit")
    public void edit() {
    }

    @ApiOperation(value = "删除")
    @PutMapping("/delete")
    public void delete() {
    }
}