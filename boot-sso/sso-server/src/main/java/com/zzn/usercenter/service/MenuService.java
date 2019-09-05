package com.zzn.usercenter.service;

import com.zzn.usercenter.model.Menu;
import com.zzn.usercenter.utils.base.IBaseService;

import java.util.List;

/**
 * @author zengzhangni
 * @version 1.0 2019年8月22日
 */
public interface MenuService extends IBaseService<Menu> {

    List<Menu> getTree();

}