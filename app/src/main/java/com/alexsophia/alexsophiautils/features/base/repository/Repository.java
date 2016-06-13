package com.alexsophia.alexsophiautils.features.base.repository;


import android.content.Context;

import java.util.List;

/**
 * 此类为数据源操作接口类，包括对数据的插入或更新，清除数据、获取数据等操作
 * @param <T> 数据类
 * @param <D> 数据操作类
 */
public interface Repository<T,D> {

    /**
     * 插入或更新数据
     * @param context
     * @param t
     */
    void insertOrUpdate(Context context, T t);

    /**
     * 清除数据
     * @param context
     */
    void clear(Context context);

    /**
     * 根据主键删除数据
     * @param context
     * @param id
     */
    void deleteDataWithId(Context context, long id);

    /**
     * 根据主键获取数据
     * @param context
     * @param id
     * @return
     */
    T getDataById(Context context, long id);

    /**
     * 获取所有数据
     * @param context
     * @return
     */
    List<T> getAll(Context context);

    /**
     * 获取数据库操作类实例
     * @param context
     * @return
     */
    D getDao(Context context);
}
