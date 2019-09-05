package com.zzn.usercenter.utils.base;


import java.util.List;

import com.zzn.usercenter.mapper.BaseMapper;
import com.zzn.usercenter.model.BaseModel;


public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseModel> implements IBaseService<T> {

    /**
     * mappers 注入方法
     *
     * @return
     */
    public abstract M getMapper();

    @Override
    public int insert(T t) {
        return this.getMapper().insert(t);
    }

    @Override
    public int insertBatch(List<T> list) {
        return this.getMapper().insertBatch(list);
    }

    @Override
    public int updateById(T t) {
        return this.getMapper().updateById(t);
    }

    @Override
    public int deleteById(Object id) {
        return this.getMapper().deleteById(id);
    }

    @Override
    public T selectById(Object id) {
        return this.getMapper().selectById(id);
    }

    @Override
    public T queryConditionsToObject(T t) {
        return this.getMapper().queryConditionsToObject(t);
    }

    @Override
    public List<T> queryConditionsToList(T t) {
        return this.getMapper().queryConditionsToList(t);
    }
}
