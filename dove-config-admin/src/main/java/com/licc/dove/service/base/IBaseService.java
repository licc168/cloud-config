package com.licc.dove.service.base;

import com.licc.dove.param.BaseParam;

/**
 * @author lichangchao
 * @date 2017 -05-02 21:12:47
 */
public interface IBaseService<T> {
    /**
     * 物理删除
     * @param id
     */
    void deleteById(Long id);

    /**
     * 新增或者修改
     * @param param
     */
    <P extends BaseParam> void save(P  param);

  /**
     * 逻辑删除
     * @param id
     */
    void updateDeleteFlagById(Long id) ;


      T findOne(Long id);
}
