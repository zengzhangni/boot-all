package com.zzn.usercenter.controller;

import com.zzn.usercenter.model.Menu;
import com.zzn.usercenter.service.MenuService;
import com.zzn.usercenter.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zengzhangni
 * @version 1.0 2019年8月22日
 */
@Api(tags = "菜单表")
@RestController
@RequestMapping("menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @ApiOperation(value = "菜单树")
    @GetMapping(value = "/getTree")
    public ResponseMessage<List<Menu>> getTree() {
        return new ResponseMessage<>(menuService.getTree());
    }

}