package com.zzn.usercenter.service;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zzn.usercenter.model.System;
import com.zzn.usercenter.utils.ResponseMessage;
import com.zzn.usercenter.utils.base.IBaseService;

public interface SystemService extends IBaseService<System> {

    /**
     * 查询系统信息
     *
     * @param id
     * @return
     */
    ResponseMessage<System> findSystemInfoById(Integer id);

    /**
     * 新增系统信息
     *
     * @param systemInfo
     * @return
     */
    ResponseMessage insertSystemInfo(System systemInfo);

    /**
     * 修改系统信息
     *
     * @param systemInfo
     * @return
     */
    ResponseMessage modifySystemInfoById(System systemInfo);

    /**
     * 删除系统信息
     *
     * @param id
     * @return
     */
    ResponseMessage deleteSystemInfoById(Integer id);

    /**
     * 批量新增
     *
     * @param systemInfos
     * @return
     */
    ResponseMessage insertBatchSystemInfo(List<System> systemInfos);


    /**
     * 通过系统编码查询信息
     *
     * @param systemCode 系统编码
     * @return
     */
    System queryBySystemCode(String systemCode);

    /**
     * 查询所有
     *
     * @return
     */
    List<System> getAll();

    PageInfo<System> queryPage(Integer pageNo, Integer pageSize);

}