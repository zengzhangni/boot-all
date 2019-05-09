package com.zzn.grou.swagger.controller2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengzhangni
 * @date 2019/5/5
 */
@Api(tags ="测试")
@RestController
@RequestMapping("cs")
public class CsController {

    @ApiOperation(value = "获取")
    @GetMapping("/get")
    public void get() {
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
    @DeleteMapping("/delete")
    public void delete() {
    }
}
