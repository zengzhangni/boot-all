package com.zzn.usercenter.service;

import com.github.pagehelper.PageInfo;
import com.zzn.usercenter.model.Log;
import com.zzn.usercenter.utils.base.IBaseService;

/**
 * @author zengzhangni
 * @version 1.0 2019年8月20日
 */
public interface LogService extends IBaseService<Log> {

    void asyncAddLog(Log log);

    PageInfo<Log> queryPage(Integer pageNo, Integer pageSize);

}