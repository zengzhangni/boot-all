package com.zzn.usercenter.service.impl;

import com.zzn.usercenter.mapper.MenuMapper;
import com.zzn.usercenter.model.Menu;
import com.zzn.usercenter.service.MenuService;
import com.zzn.usercenter.utils.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zengzhangni
 * @version 1.0 2019年8月22日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Resource
    private MenuMapper menuMapper;

    @Override
    public MenuMapper getMapper() {
        return menuMapper;
    }

    @Override
    public List<Menu> getTree() {
        List<Menu> all = menuMapper.getAll();
        List<Menu> baseLists = new ArrayList<>();
        // 总菜单，出一级菜单，一级菜单没有父id
        for (Menu e : all) {
            if (e.getPId() == null) {
                baseLists.add(e);
            }
        }
        // 遍历一级菜单
        for (Menu e : baseLists) {
            // 将子元素 set进一级菜单里面
            e.setChilds(getChild(e.getId(), all));
        }

        return baseLists;
    }

    private List<Menu> getChild(Integer pid, List<Menu> elements) {
        List<Menu> childs = new ArrayList<>();
        for (Menu e : elements) {
            if (e.getPId() != null) {
                if (e.getPId().equals(pid)) {
                    // 子菜单的下级菜单
                    childs.add(e);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Menu e : childs) {
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