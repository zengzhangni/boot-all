package com.zzn.usercenter.service.impl;

import com.zzn.usercenter.utils.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzn.usercenter.mapper.UserSystemMapper;
import com.zzn.usercenter.model.UserSystem;
import com.zzn.usercenter.service.UserSystemService;
import com.zzn.usercenter.utils.ResponseMessage;

@Service
@Slf4j
@Transactional
public class UserSystemServiceImpl extends BaseServiceImpl<UserSystemMapper , UserSystem> implements UserSystemService {

    @Autowired
    private UserSystemMapper userSystemMapper;

    @Override
    public UserSystemMapper getMapper() { return userSystemMapper; }

    /**
     * 根据用户码查询用户系统信息
     * @param systemUserCode
     * @return
     */
    @Override
    public ResponseMessage<UserSystem> findUserSystemBySystemUserCode(String systemUserCode) {
        UserSystem userSystemInfo = userSystemMapper.findUserSystemBySystemUserCode(systemUserCode);
        return new ResponseMessage<>(userSystemInfo);
    }

    /**
     * 根据登录名和系统用户码查询用户系统信息
     * @param loginName
     * @param systemCode
     * @return
     */
    @Override
    public ResponseMessage<UserSystem> findUserSystemByLoginNameAndSystemCode(String loginName, String systemCode) {
        UserSystem userSystemInfo = userSystemMapper.findUserSystemByLoginNameAndSystemCode(loginName, systemCode);
        return new ResponseMessage<>(userSystemInfo);
    }

    /**
     * 修改用户系统信息
     * @param userSystemInfo
     * @return
     */
    @Override
    public ResponseMessage modifyUserSystemInfoById(UserSystem userSystemInfo) {
        userSystemMapper.updateById(userSystemInfo);
        return new ResponseMessage();
    }

    /**
     * 根据删除用户系统信息
     * @param id
     * @return
     */
    @Override
    public ResponseMessage deleteByUserSystemId(Integer id) {
        userSystemMapper.deleteById(id);
        return new ResponseMessage();
    }

    /**
     * 新增用户系统信息
     * @param userSystemInfo
     * @return
     */
    @Override
    public ResponseMessage insertUserSystemInfo(UserSystem userSystemInfo) {
        userSystemMapper.insert(userSystemInfo);
        return new ResponseMessage();
    }

    /**
     * 根据id查询用户系统信息
     * @param id
     * @return
     */
    @Override
    public ResponseMessage<UserSystem> findUserSystemInfoById(Integer id) {
        UserSystem userSystem = userSystemMapper.selectById(id);
        return new ResponseMessage<>(userSystem);
    }

}
