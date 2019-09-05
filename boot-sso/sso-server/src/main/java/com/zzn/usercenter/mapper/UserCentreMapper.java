package com.zzn.usercenter.mapper;


import com.zzn.usercenter.model.UserCentre;
import com.zzn.usercenter.model.system.SystemUserUpdateVo;
import com.zzn.usercenter.model.vo.UserCentreVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserCentreMapper extends BaseMapper<UserCentre> {

    /**
     * 根据登录名查询用户信息
     *
     * @param loginName
     * @return
     */
    UserCentre findUserCentreByLoginName(String loginName);

    /**
     * 用户注册
     *
     * @param userCentre
     * @return
     */
    int insert(UserCentreVo userCentre);

    /**
     * 查询所有
     *
     * @return
     */
    List<UserCentre> queryAll();


    Integer resetPassword(@Param("loginName") String loginName, @Param("password") String password);

    Integer systemUserUpdate(SystemUserUpdateVo systemUserUpdateVo);

}