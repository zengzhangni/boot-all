package com.zzn.usercenter.service;

import com.github.pagehelper.PageInfo;
import com.zzn.usercenter.model.UserCentre;
import com.zzn.usercenter.model.system.SystemUserRegisterVo;
import com.zzn.usercenter.model.system.SystemUserUpdateVo;
import com.zzn.usercenter.model.vo.UserCentreVo;
import com.zzn.usercenter.utils.ResponseMessage;
import com.zzn.usercenter.utils.base.IBaseService;

public interface UserCentreService extends IBaseService<UserCentre> {

    /**
     * 新增用户信息
     *
     * @param userInfo
     * @return
     */
    ResponseMessage insertUserInfo(UserCentre userInfo);

    /**
     * 修改用户信息
     *
     * @param userInfo
     * @return
     */
    ResponseMessage modifyUserInfoById(UserCentre userInfo);

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    ResponseMessage deleteByUserId(Integer id);

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    ResponseMessage<UserCentre> findUserInfoById(Integer id);

    /**
     * 添加用户
     *
     * @param userInfo
     * @return
     */
    ResponseMessage insertInfo(UserCentreVo userInfo);

    /**
     * 根据用户登录名查询用户
     *
     * @param loginName
     * @return
     */
    UserCentre queryByLoginName(String loginName);


    /**
     * 用户注册
     *
     * @return
     */
    ResponseMessage userRegister(UserCentreVo userCentre);


    PageInfo<UserCentre> queryPage(Integer pageNo, Integer pageSize);

    Integer resetPassword(String loginName);


    ResponseMessage systemUserRegister(SystemUserRegisterVo systemUserRegisterVo);

    ResponseMessage systemUserUpdate(SystemUserUpdateVo systemUserUpdateVo);

}
