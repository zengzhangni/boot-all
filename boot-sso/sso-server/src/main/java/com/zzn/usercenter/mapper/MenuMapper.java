package com.zzn.usercenter.mapper;


import com.zzn.usercenter.model.Menu;

import java.util.List;

/**
 * @author zengzhangni
 * @version 1.0 2019年8月22日
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getAll();

}