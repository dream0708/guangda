package org.keega.idea.util.dao.interf;

/**
 * Created by asus_n56 on 2016/9/15.
 */
public interface IUpdateDao<T> {
    /**
     * 根据实体对象更新原有对象
     *
     * @param t 被修改过的实体对象（根据主键找到对象进行更新）
     */
    public void updateRowByBean(T t);

    /**
     * 更新行的字段由实体类对应的属性字段提供，值由实体类对应属性字段提供；更新where条件自定义
     *
     * @param t              实体类
     * @param whereCondition 更新where条件部分（需要包含'where'）
     */
    public void updateRowByBeanAndWhereCondition(T t, String whereCondition,Object... realValue);

    /**
     * 根据原生sql更新行，全部语句都自定义
     *
     * @param sql 原生sql
     */
    public void updateRowByOriginalSql(String sql,Object... realValue);
}
