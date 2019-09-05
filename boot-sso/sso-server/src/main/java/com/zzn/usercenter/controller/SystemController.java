package com.zzn.usercenter.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zzn.usercenter.model.System;
import com.zzn.usercenter.service.SystemService;
import com.zzn.usercenter.utils.ResponseMessage;

import java.util.List;

@Api(tags = "系统")
@RestController
@RequestMapping("system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @ApiOperation(httpMethod = "GET", value = "查询系统信息")
    @GetMapping("/findSystemById/{id}")
    public ResponseMessage<System> findSystemInfoById(@PathVariable Integer id) {

        return systemService.findSystemInfoById(id);
    }

    @ApiOperation(httpMethod = "POST", value = "新增系统信息")
    @PostMapping("/insertSystemInfo")
    public ResponseMessage insertSystemInfo(@RequestBody System systemInfo) {

        return systemService.insertSystemInfo(systemInfo);
    }

    @ApiOperation(httpMethod = "PUT", value = "修改系统信息")
    @PutMapping("/modifySystemInfoById")
    public ResponseMessage modifySystemInfoById(@RequestBody System systemInfo) {

        return systemService.modifySystemInfoById(systemInfo);
    }

    @ApiOperation(httpMethod = "DELETE", value = "删除系统信息")
    @DeleteMapping("/deleteSystemInfoById/{id}")
    public ResponseMessage deleteSystemInfoById(@PathVariable Integer id) {

        return systemService.deleteSystemInfoById(id);
    }

    @ApiOperation(httpMethod = "POST", value = "批量新增")
    @PostMapping("/insertBatchSystemInfo")
    public ResponseMessage insertBatchSystemInfo(@RequestBody List<System> systemInfos) {

        return systemService.insertBatchSystemInfo(systemInfos);
    }

    @ApiOperation("查询所有菜单")
    @GetMapping("/getAll")
    public ResponseMessage getAll() {
        return new ResponseMessage<>(systemService.getAll());
    }
    @ApiOperation(value = "分页查询")
    @GetMapping("/queryPage")
    public ResponseMessage queryPage(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        return new ResponseMessage<>(this.systemService.queryPage(pageNo, pageSize));
    }

}
