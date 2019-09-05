package com.zzn.usercenter.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.zzn.usercenter.model.System;

import java.util.List;

@Repository
@Mapper
public interface SystemMapper extends BaseMapper<System> {


    System queryBySystemCode(String systemCode);

    List<System> getAll();

}