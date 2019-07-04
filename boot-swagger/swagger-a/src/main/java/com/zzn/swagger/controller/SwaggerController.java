package com.zzn.swagger.controller;

import com.zzn.common.response.ResponseMessage;
import com.zzn.swagger.domain.Element;
import com.zzn.swagger.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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


    public List<Element> getList() {
        List<Element> list = new ArrayList<>();
        list.add(new Element(1, null, "一级菜单"));
        list.add(new Element(2, 1, "1-1级菜单"));
        list.add(new Element(3, 1, "1-2级菜单"));
        list.add(new Element(4, 2, "1-1-1级菜单"));
        list.add(new Element(5, 1, "1-3级菜单"));
        list.add(new Element(6, 4, "1-1-1-1级菜单"));
        list.add(new Element(7, null, "一级菜单"));
        list.add(new Element(8, 7, "7-1级菜单"));
        return list;
    }

    @ApiOperation(value = "菜单树")
    @PostMapping(value = "/getTree")
    public List<Element> getTree() {

        List<Element> baseLists = new ArrayList<>();
        // 总菜单，出一级菜单，一级菜单没有父id
        List<Element> list = getList();
        for (Element e : list) {
            if (e.getPId() == null) {
                baseLists.add(e);
            }
        }
        // 遍历一级菜单
        for (Element e : baseLists) {
            // 将子元素 set进一级菜单里面
            e.setChilds(getChild(e.getId(), list));
        }

        return baseLists;
    }


    private List<Element> getChild(Integer pid, List<Element> elements) {
        List<Element> childs = new ArrayList<>();
        for (Element e : elements) {
            if (e.getPId() != null) {
                if (e.getPId().equals(pid)) {
                    // 子菜单的下级菜单
                    childs.add(e);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Element e : childs) {
            // 继续添加子元素
            e.setChilds(getChild(e.getId(), elements));
        }

        //停下来的条件，如果 没有子元素了，则停下来
        if (childs.size() == 0) {
            return null;
        }
        return childs;
    }
}
