package com.zzn.usercenter.controller;

import com.zzn.usercenter.model.UserSystem;
import com.zzn.usercenter.service.UserSystemService;
import com.zzn.usercenter.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户系统")
@RestController
@RequestMapping(value = "userSystem")
public class UserSystemController {

    @Autowired
    private UserSystemService userSystemService;

    @ApiOperation(httpMethod = "GET", value = "根据系统用户码查询用户系统信息")
    @GetMapping("/findUserSystemBySystemUserCode")
    public ResponseMessage<UserSystem> findUserSystemBySystemUserCode (@RequestParam("systemUserCode") String systemUserCode) {

        return userSystemService.findUserSystemBySystemUserCode(systemUserCode);
    }

    @ApiOperation(httpMethod = "GET", value = "根据用户登录名和系统编码查询查询用户系统信息")
    @GetMapping("findUserSystemByLoginNameAndSystemCode")
    public ResponseMessage<UserSystem> findUserSystemByLoginNameAndSystemCode (@RequestParam("loginName") String loginName,
                                                                              @RequestParam("systemCode") String systemCode) {
        return userSystemService.findUserSystemByLoginNameAndSystemCode(loginName, systemCode);
    }

    @ApiOperation(httpMethod = "PUT",value = "修改用户系统信息")
    @PutMapping("/modifyUserSystemInfoById")
    public ResponseMessage modifyUserSystemInfoById (@RequestBody UserSystem userSystemInfo){

        return userSystemService.modifyUserSystemInfoById(userSystemInfo);
    }

    @ApiOperation(httpMethod = "DELETE",value = "删除用户系统信息")
    @DeleteMapping("/deleteByUserSystemId/{id}")
    public ResponseMessage deleteByUserSystemId (@PathVariable Integer id){
        return userSystemService.deleteByUserSystemId(id);
    }

    @ApiOperation(httpMethod = "POST",value = "新增用户系统信息")
    @PostMapping("/insertUserSystemInfo")
    public ResponseMessage insertUserSystemInfo (@RequestBody UserSystem userSystemInfo){

        return userSystemService.insertUserSystemInfo(userSystemInfo);
    }

    @ApiOperation(httpMethod = "GET",value = "查询用户系统信息")
    @GetMapping("/findUserSystemInfoById/{id}")
    public ResponseMessage<UserSystem> findUserSystemInfoById (@PathVariable Integer id){

        return userSystemService.findUserSystemInfoById(id);
    }

}
