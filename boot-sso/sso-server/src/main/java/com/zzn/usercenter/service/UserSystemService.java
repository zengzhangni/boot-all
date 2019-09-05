package com.zzn.usercenter.service;

import com.zzn.usercenter.model.UserSystem;
import com.zzn.usercenter.utils.ResponseMessage;
import com.zzn.usercenter.utils.base.IBaseService;

public interface UserSystemService extends IBaseService<UserSystem> {

    /**
     * 根据系统用户码查询用户系统信息
     * @param systemUserCode
     * @return
     */
    ResponseMessage<UserSystem> findUserSystemBySystemUserCode(String systemUserCode);


    /**
     * 根据登录名和系统用户码查询用户系统信息
     * @param loginName
     * @param systemCode
     * @return
     */
    ResponseMessage<UserSystem> findUserSystemByLoginNameAndSystemCode(String loginName, String systemCode);

    /**
     * 修改用户系统信息
     * @param userSystemInfo
     * @return
     */
    ResponseMessage modifyUserSystemInfoById(UserSystem userSystemInfo);

    /**
     * 删除用户系统信息
     * @param id
     * @return
     */
    ResponseMessage deleteByUserSystemId(Integer id);

    /**
     * 新增用户系统信息
     * @param userSystemInfo
     * @return
     */
    ResponseMessage insertUserSystemInfo(UserSystem userSystemInfo);

    /**
     * 根据id查询用户系统信息
     * @param id
     * @return
     */
    ResponseMessage<UserSystem> findUserSystemInfoById(Integer id);
}