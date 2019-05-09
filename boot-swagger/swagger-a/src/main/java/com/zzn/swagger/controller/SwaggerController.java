package com.zzn.swagger.controller;

import com.zzn.common.response.ResponseMessage;
import com.zzn.swagger.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengzhangni
 * @date 2019/5/8
 */
@Api(tags = "用户中心")
@RestController
@RequestMapping("user")
public class SwaggerController {

    @GetMapping("/login")
    @ApiOperation(value = "登录")
    public ResponseMessage<Integer> login1(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord) {
        Integer login = login(userName, passWord);
        return new ResponseMessage<>(login);
    }

    private Integer login(String userName, String passWord) {
        if ("admin".equals(userName) && "admin".equals(passWord)) {
            return 1;
        } else {
            return 0;
        }
    }

    @ApiOperation(value = "添加美容邦用户表")
    @PostMapping("/insert")
    public ResponseMessage<User> insert(@RequestBody User user) {
        return new ResponseMessage<>(user);
    }


}
