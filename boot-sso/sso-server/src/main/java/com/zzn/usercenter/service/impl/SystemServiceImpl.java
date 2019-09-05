package com.zzn.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzn.usercenter.utils.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzn.usercenter.mapper.SystemMapper;
import com.zzn.usercenter.model.System;
import com.zzn.usercenter.service.SystemService;
import com.zzn.usercenter.utils.ResponseMessage;

import java.util.List;

@Service
@Slf4j
@Transactional
public class SystemServiceImpl extends BaseServiceImpl<SystemMapper, System> implements SystemService {

    @Autowired
    private SystemMapper systemMapper;

    @Override
    public SystemMapper getMapper() {
        return systemMapper;
    }

    /**
     * 查询系统信息
     *
     * @param id
     * @return
     */
    @Override
    public ResponseMessage<System> findSystemInfoById(Integer id) {
        System system = systemMapper.selectById(id);
        return new ResponseMessage<>(system);
    }

    /**
     * 新增系统信息
     *
     * @param systemInfo
     * @return
     */
    @Override
    public ResponseMessage insertSystemInfo(System systemInfo) {
        systemMapper.insert(systemInfo);
        return new ResponseMessage();
    }

    /**
     * 修改系统信息
     *
     * @param systemInfo
     * @return
     */
    @Override
    public ResponseMessage modifySystemInfoById(System systemInfo) {
        systemMapper.updateById(systemInfo);
        return new ResponseMessage();
    }

    /**
     * 删除系统信息
     *
     * @param id
     * @return
     */
    @Override
    public ResponseMessage deleteSystemInfoById(Integer id) {
        systemMapper.deleteById(id);
        return new ResponseMessage();
    }

    /**
     * 批量新增
     *
     * @param systemInfos
     * @return
     */
    @Override
    public ResponseMessage insertBatchSystemInfo(@Param("systemInfos") List<System> systemInfos) {
        systemMapper.insertBatch(systemInfos);
        return new ResponseMessage();
    }

    @Override
    public System queryBySystemCode(String systemCode) {
        return systemMapper.queryBySystemCode(systemCode);
    }

    @Override
    public List<System> getAll() {
        return systemMapper.getAll();
    }

    @Override
    public PageInfo<System> queryPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        PageHelper.orderBy("create_time desc");
        return new PageInfo<>(systemMapper.getAll());
    }

}
