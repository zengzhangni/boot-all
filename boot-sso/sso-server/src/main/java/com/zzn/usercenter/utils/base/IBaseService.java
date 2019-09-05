package com.zzn.usercenter.utils.base;


import java.util.List;

import com.zzn.usercenter.model.BaseModel;

public interface IBaseService<T extends BaseModel> {

    /**
     * 新增
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    int insertBatch(List<T> list);

    /**
     * 根据id修改
     *
     * @param t
     * @return
     */
    int updateById(T t);

    /**
     * 按id删除
     *
     * @param id
     * @return
     */
    int deleteById(Object id);

    /**
     * 按id查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    T selectById(Object id);

    /**
     * 按条件查询
     *
     * @param t
     * @return
     */
    T queryConditionsToObject(T t);

    /**
     * 按条件查询对象集合
     *
     * @param t
     * @return
     */
    List<T> queryConditionsToList(T t);
}
