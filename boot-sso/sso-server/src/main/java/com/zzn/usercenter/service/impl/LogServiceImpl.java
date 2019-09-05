package com.zzn.usercenter.service.impl;

import com.github.pagehelper.PageInfo;
import com.zzn.usercenter.mapper.LogMapper;
import com.zzn.usercenter.model.Log;
import com.zzn.usercenter.service.LogService;
import com.zzn.usercenter.utils.base.BaseServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;

/**
 * @author zengzhangni
 * @version 1.0 2019年8月20日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log> implements LogService {


    @Resource
    private LogMapper logMapper;

    @Override
    public LogMapper getMapper() {
        return logMapper;
    }

    @Override
    @Async
    public void asyncAddLog(Log log) {
        logMapper.insert(log);
    }

    @Override
    public PageInfo<Log> queryPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        PageHelper.orderBy("create_time desc");
        return new PageInfo<>(logMapper.queryAll());
    }


}