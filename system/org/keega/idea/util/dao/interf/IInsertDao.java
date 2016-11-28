package org.keega.idea.util.dao.interf;

/**
 * Created by asus_n56 on 2016/9/15.
 */
public interface IInsertDao<T> {
    /**
     * 根据一个实体对象插入一个对象
     *
     * @param t 实体对象
     */
    public void insertBean(T t);

    /**
     * @param t            要插入的实体对象
     * @param excludeField 选择不插入的某些成员字段
     */
    public void insertBeanExcludeFields(T t, String... excludeField);

    public void insertRowByOriginalSql(String sql,Object... realValue);

}
