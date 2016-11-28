package org.keega.idea.util.dao.interf;

/**
 * Created by asus_n56 on 2016/9/15.
 */
public interface IDeleteDao<T> {
    /**
     * 根据主键值删除行
     *
     * @param primaryKeyValue 主键值
     */
    public void deleteRowByPrimaryKey(Class<T> clz,Object primaryKeyValue);

    /**
     * 根据给定where条件删除；
     * delete from tableName 部分已经有了，需要自定义where条件。（需要包含 'where'）
     *
     * @param whereCondition 自定义where条件段sql
     */
    public void deleteRowByWhereCondition(Class<T> clz,String whereCondition,Object... realValue);

    /**
     * 根据原生sql删除行，全部语句都自定义
     *
     * @param sql 原生sql
     */
    public void deleteRowByOriginalSql(String sql,Object... realValue);
}
