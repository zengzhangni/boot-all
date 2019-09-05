package com.zzn.usercenter.controller;

import com.zzn.usercenter.service.LogService;
import com.zzn.usercenter.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zengzhangni
 * @version 1.0 2019年8月20日
 */
@Api(tags = "SSO日志")
@RestController
@RequestMapping("log")
public class LogController {

    @Resource
    private LogService logService;


    @ApiOperation(value = "通过id查询")
    @GetMapping("/queryById")
    public ResponseMessage queryById(@RequestParam Integer id) {
        return new ResponseMessage<>(this.logService.selectById(id));
    }


    @ApiOperation(value = "分页查询")
    @GetMapping("/queryPage")
    public ResponseMessage queryPage(@RequestParam(value = "pageNo") Integer pageNo, @RequestParam Integer pageSize) {
        return new ResponseMessage<>(this.logService.queryPage(pageNo, pageSize));
    }


}