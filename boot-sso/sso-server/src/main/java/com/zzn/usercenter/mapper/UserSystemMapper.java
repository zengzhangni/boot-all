package com.zzn.usercenter.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zzn.usercenter.model.UserSystem;

@Repository
@Mapper
public interface UserSystemMapper extends BaseMapper<UserSystem> {

    /**
     * 根据用户码查询用户系统信息
     * @param userCode
     * @return
     */
    UserSystem findUserSystemBySystemUserCode(String userCode);

    /**
     * 根据登录名和系统用户码查询用户系统信息
     * @param loginName
     * @param systemCode
     * @return
     */
    UserSystem findUserSystemByLoginNameAndSystemCode(@Param("loginName") String loginName,@Param("systemCode") String systemCode);

    /**
     * 根据登录名查询用户系统信息
     * @param loginName
     * @return
     */
    UserSystem findSystemInfoByLoginName(String loginName);


}