package com.zzn.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzn.usercenter.model.system.SystemUserRegisterVo;
import com.zzn.usercenter.model.system.SystemUserUpdateVo;
import com.zzn.usercenter.utils.ResponseContants;
import com.zzn.usercenter.utils.base.BaseServiceImpl;
import com.zzn.usercenter.utils.sso.Md5Util;
import com.zzn.usercenter.service.UserCentreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzn.usercenter.mapper.UserCentreMapper;
import com.zzn.usercenter.mapper.UserSystemMapper;
import com.zzn.usercenter.model.UserCentre;
import com.zzn.usercenter.model.UserSystem;
import com.zzn.usercenter.model.vo.UserCentreVo;
import com.zzn.usercenter.utils.ResponseMessage;
import com.zzn.usercenter.utils.generatedcodeutils.UserCentreUtil;

import java.util.Date;

@Service
@Slf4j
@Transactional
public class UserCentreServiceImpl extends BaseServiceImpl<UserCentreMapper, UserCentre> implements UserCentreService {

    @Autowired
    private UserCentreMapper userCentreMapper;

    @Autowired
    private UserSystemMapper userSystemMapper;

    @Override
    public UserCentreMapper getMapper() {
        return userCentreMapper;
    }

    /**
     * 新增用户
     *
     * @param userInfo
     * @return
     */
    @Override
    public ResponseMessage insertUserInfo(UserCentre userInfo) {
        userCentreMapper.insert(userInfo);
        return new ResponseMessage();
    }

    /**
     * 修改用户信息
     *
     * @param userInfo
     * @return
     */
    @Override
    public ResponseMessage modifyUserInfoById(UserCentre userInfo) {
        userCentreMapper.updateById(userInfo);
        return new ResponseMessage();
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Override
    public ResponseMessage deleteByUserId(Integer id) {
        userCentreMapper.deleteById(id);
        return new ResponseMessage();
    }

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    public ResponseMessage<UserCentre> findUserInfoById(Integer id) {
        UserCentre userCentre = userCentreMapper.selectById(id);
        return new ResponseMessage<>(userCentre);
    }

    /**
     * 根据条件添加用户
     *
     * @param userInfo
     * @return
     */
    @Override
    public ResponseMessage insertInfo(UserCentreVo userInfo) {
        UserCentre user = userCentreMapper.findUserCentreByLoginName(userInfo.getLoginName());
        if (user == null) {
            String userCode = UserCentreUtil.getUserCentreCode();
            UserCentre userCentre = new UserCentre();
            userCentre.setUserCode(userCode);
            userCentre.setLoginName(userInfo.getLoginName());
            userCentre.setLoginPassword(userInfo.getLoginPassword());
            userCentre.setPhone(userInfo.getPhone());
            userCentre.setUserImgUrl(userInfo.getUserImgUrl());
            userCentre.setGender(userInfo.getGender());
            userCentre.setUserName(userInfo.getUserName());
            userCentre.setCreateTime(new Date());
            //新增一条用户数据
            userCentreMapper.insert(userCentre);
            //新增一条系统用户数据
            insertUserSystem(userInfo.getLoginName(), userCode, userInfo.getSystemCode(), userInfo.getSystemUserCode());
        } else {
            UserSystem userSystem = userSystemMapper.findUserSystemByLoginNameAndSystemCode(userInfo.getLoginName(), userInfo.getSystemCode());
            if (userSystem == null) {
                insertUserSystem(userInfo.getLoginName(), user.getUserCode(), userInfo.getSystemCode(), userInfo.getSystemUserCode());
            }
        }
        return new ResponseMessage();
    }

    @Override
    public UserCentre queryByLoginName(String loginName) {
        return userCentreMapper.findUserCentreByLoginName(loginName);
    }


    private void insertUserSystem(String loginName, String userCode, String systemCode, String systemUserCode) {
        UserSystem userSystem1 = new UserSystem();
        userSystem1.setLoginName(loginName);
        userSystem1.setUserCode(userCode);
        userSystem1.setSystemCode(systemCode);
        userSystem1.setSystemUserCode(systemUserCode);
        userSystemMapper.insert(userSystem1);
    }


    @Override
    public ResponseMessage userRegister(UserCentreVo userCentre) {
        if (StringUtils.isNotBlank(userCentre.getLoginName()) && StringUtils.isNotBlank(userCentre.getLoginPassword())) {
            UserCentre user = userCentreMapper.findUserCentreByLoginName(userCentre.getLoginName());
            if (user == null) {
                UserCentre user2 = new UserCentre();
                BeanUtils.copyProperties(userCentre, user2);
                String userCentreCode = UserCentreUtil.getUserCentreCode();
                user2.setUserCode(userCentreCode);
                userCentreMapper.insert(user2);
            } else {
                return new ResponseMessage(501, ResponseContants.CURRENT_USER_EXIST_MSG);
            }
        } else {
            return new ResponseMessage(502, ResponseContants.REGISTER_PARAM_NULL_MSG);
        }
        return new ResponseMessage(200, "用户注册成功");
    }

    @Override
    public PageInfo<UserCentre> queryPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        PageHelper.orderBy("create_time desc");
        return new PageInfo<>(userCentreMapper.queryAll());
    }

    @Override
    public Integer resetPassword(String loginName) {
        String password = Md5Util.md5("123456");
        return userCentreMapper.resetPassword(loginName, password);
    }

    @Override
    public ResponseMessage systemUserRegister(SystemUserRegisterVo vo) {
        UserCentre user = userCentreMapper.findUserCentreByLoginName(vo.getLoginName());
        if (user == null) {
            UserCentre user2 = new UserCentre();
            String userCentreCode = UserCentreUtil.getUserCentreCode();
            user2.setUserCode(userCentreCode);
            user2.setLoginName(vo.getLoginName());
            user2.setLoginPassword(Md5Util.md5(vo.getPassword()));
            user2.setSystemSource(vo.getSystemCode());
            userCentreMapper.insert(user2);
        }
        return new ResponseMessage();
    }

    @Override
    public ResponseMessage systemUserUpdate(SystemUserUpdateVo systemUserUpdateVo) {
        return new ResponseMessage<>(userCentreMapper.systemUserUpdate(systemUserUpdateVo));
    }

}
