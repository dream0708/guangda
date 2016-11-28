package org.keega.idea.util.dao.interf;

import java.util.List;
import java.util.Map;

/**
 * Created by asus_n56 on 2016/9/15.
 */
public interface ISelectDao<T> {
    /**
     * 根据主键查询一个实体对象
     *
     * @param primaryKey 主键值
     * @return 查询的实体对象
     */
    public T querySingleBeanByPrimaryKey(Class<T> clz,Object primaryKey);

    /**
     * 根据给定的where条件查询实体对象
     *
     * @param whereCondition 查询的where条件部分（需要包含'where'）
     * @return 根据条件查找到的实体类对象
     */
    public T querySingleBeanByWhereCondition(Class<T> clz,String whereCondition,Object... realValue);

    /**
     * 根据原生sql查询实体类对象
     *
     * @param sql 原生sql（需要注意查询返回的必须是单行数据，查询的字段，必须被实体类对象字段所包含）
     * @return 查询的实体类对象
     */
    public T querySingleBeanByOriginalSql(Class<T> clz,String sql,Object... realValue);

    /**
     * 查找全部实体对象
     * @return 返回的List实体对象
     */
    public List<T> queryAllBeansList(Class<T> clz);

    /**
     *根据自定义的where条件查找一组实体对象
     * @param whereCondition 查找的where条件
     * @return 返回的List实体对象
     */
    public List<T> queryBeansListByWhereCondition(Class<T> clz,String whereCondition,Object... realValue);

    /**
     * 使用原生sql语句一组实体对象
     * @param sql 原生sql
     * @return 返回的List实体对象
     */
    public List<T> queryBeansListByOriginalSql(Class<T> clz,String sql,Object... realValue);


    public Map<String, Object> querySingleMapByPrimaryKey(Class<T> clz,Object primaryKey);

    public Map<String, Object> querySingleMapByWhereCondition(Class<T> clz,String whereCondition,Object... realValue);

    public Map<String, Object> querySingleMapByOriginalSql(String sql, Object... realValue);

    public List<Map<String, Object>> queryListMapAll(Class<T> clz);

    public List<Map<String, Object>> queryListMapByWhereCondition(Class<T> clz,String whereCondition,Object... realValue);

    public List<Map<String, Object>> queryListMapByOriginalSql(String sql, Object... realValue);

}
