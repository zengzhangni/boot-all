package com.zzn.usercenter.controller;

import com.zzn.usercenter.model.UserCentre;
import com.zzn.usercenter.model.vo.UserCentreVo;
import com.zzn.usercenter.service.UserCentreService;
import com.zzn.usercenter.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户中心")
@RestController
@RequestMapping(value = "user")
public class UserCentreController {

    @Autowired
    private UserCentreService userCentreService;

    @ApiOperation(httpMethod = "POST", value = "新增用户")
    @RequestMapping("/insertUserInfo")
    public ResponseMessage insertInfo(@RequestBody UserCentre userInfo) {
        return userCentreService.insertUserInfo(userInfo);
    }

    @ApiOperation(httpMethod = "PUT", value = "修改用户")
    @PutMapping("/modifyUserInfoById")
    public ResponseMessage modifyUserById(@RequestBody UserCentre userInfo) {
        return userCentreService.modifyUserInfoById(userInfo);
    }

    @ApiOperation(httpMethod = "DELETE", value = "删除用户")
    @DeleteMapping("/deleteById/{id}")
    public ResponseMessage deleteByUserId(@PathVariable Integer id) {
        return userCentreService.deleteByUserId(id);
    }

    @ApiOperation(httpMethod = "GET", value = "查询用户")
    @GetMapping("/findUserInfoById/{id}")
    public ResponseMessage<UserCentre> findUserInfoById(@PathVariable Integer id) {
        return userCentreService.findUserInfoById(id);
    }

    @ApiOperation(httpMethod = "POST", value = "根据条件添加用户")
    @PostMapping("/insertInfo")
    public ResponseMessage inertInfo(@RequestBody UserCentreVo userInfo) {
        return userCentreService.insertInfo(userInfo);
    }

    @ApiOperation(httpMethod = "POST", value = "用户注册")
    @PostMapping("/userRegister")
    public ResponseMessage userRegister(@RequestBody UserCentreVo userCentre) {
        return userCentreService.userRegister(userCentre);
    }

    @PostMapping("/queryPage")
    public ResponseMessage queryPage(@RequestBody UserCentreVo userCentre) {
        return userCentreService.userRegister(userCentre);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/queryPage")
    public ResponseMessage queryPage(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return new ResponseMessage<>(this.userCentreService.queryPage(pageNo, pageSize));
    }

    @ApiOperation(value = "重置密码")
    @GetMapping("/resetPassword")
    public ResponseMessage resetPassword(@RequestParam("loginName") String loginName) {
        return new ResponseMessage<>(this.userCentreService.resetPassword(loginName));
    }
}
