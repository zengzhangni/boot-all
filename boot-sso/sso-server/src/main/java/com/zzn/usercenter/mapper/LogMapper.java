package com.zzn.usercenter.mapper;

import com.zzn.usercenter.model.Log;

import java.util.List;

/**
 * @author zengzhangni
 * @version 1.0 2019年8月20日
 */
public interface LogMapper extends BaseMapper<Log> {

    List<Log> queryAll();

}