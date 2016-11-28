package org.keega.idea.util.dao.impl;


import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.keega.idea.util.dao.annotation.SqlPrimaryKey;
import org.keega.idea.util.dao.debug.DebugController;
import org.keega.idea.util.dao.interf.*;
import org.springframework.dao.DataAccessException;

import javax.activation.DataSource;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *使用dbutil，基于annotation方式，提供简单的增删改查
 * @param <T> 实体类Bean
 */
public class BaseDao<T> extends AbstractBaseDao<T> implements IInsertDao<T>, IDeleteDao<T>, IUpdateDao<T>, ISelectDao<T>, StaticResourceProvide {

    /**
     * 根据一个实体对象插入一个对象
     *
     * @param t 实体对象
     */
    @Override
    public void insertBean(T t) {
        try {
            //queryRunner.insert(sau.getInsertSql(t), new BeanHandler<T>((Class<T>) t.getClass()));
            queryRunner.update(sau.getInsertSql(t));
        } catch (SQLException e) {//这样子做是因为dbutil在sqlserver下，这个update方法没有参数sql参数时，会报无参数错。
            try {
                jdbcTemplate.update(sau.getInsertSql(t));
            } catch (DataAccessException e1) {
                e1.printStackTrace();
            }
            // e.printStackTrace();
        }
    }

    /**
     * @param t            要插入的实体类对象
     * @param excludeField 选择不插入的某些成员字段
     */
    @Override
    public void insertBeanExcludeFields(T t, String... excludeField) {
        try {
            queryRunner.update(sau.getInsertSql(t, excludeField));
        } catch (SQLException e) {
            try {
                jdbcTemplate.update(sau.getInsertSql(t, excludeField));
            } catch (DataAccessException e1) {
                e1.printStackTrace();
            }
            //e.printStackTrace();
        }
    }

    @Override
    public void insertRowByOriginalSql(String sql,Object... realValue) {
        if (DebugController.isDebugOpent) System.out.println(sql);
        try {
            queryRunner.update(sql,realValue);
        } catch (SQLException e) {
            try {
                jdbcTemplate.update(sql,realValue);
            } catch (DataAccessException e2) {
                e2.printStackTrace();
            }
            //e.printStackTrace();
        }
    }

    @Override
    public void deleteRowByPrimaryKey(Class<T> clz, Object primaryKeyValue) {
        try {
            String tableName = sdu.getTableName(clz);
            Field[] fields = clz.getDeclaredFields();
            String primaryKeyField = null;
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].isAnnotationPresent(SqlPrimaryKey.class)) {
                    primaryKeyField = fields[i].getAnnotation(SqlPrimaryKey.class).value();
                    break;
                }
            }
            String sql = Delete_From + tableName + Where + primaryKeyField + Equals +"?";
            if (DebugController.isDebugOpent) System.out.println(sql);
            queryRunner.update(sql,primaryKeyValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRowByWhereCondition(Class<T> clz, String whereCondition,Object... realValue) {
        if (whereCondition == null || "".equals(whereCondition.trim()) || !whereCondition.startsWith("where"))
            throw new RuntimeException("执行此删除方法时，where 条件不能为空，并且必须以‘where’开头！");
        try {
            String tableName = sdu.getTableName(clz);
            String sql = Delete_From + tableName + Space + whereCondition;
            if (DebugController.isDebugOpent) System.out.println(sql);
            queryRunner.update(sql,realValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRowByOriginalSql(String sql,Object... realValue) {
        if (DebugController.isDebugOpent) System.out.println(sql);
        try {
            queryRunner.update(sql,realValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override//update tableName set aa = '',bb='' ``` where primaryKey = '';
    public void updateRowByBean(T t) {
        try {
            String tableName = suu.getTableName(t);
            queryRunner.update(suu.getUpdateSql(t, tableName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRowByBeanAndWhereCondition(T t, String whereCondition,Object... realValue) {
        if (whereCondition == null || "".equals(whereCondition.trim()) || !whereCondition.startsWith("where"))
            throw new RuntimeException("执行此更新方法时，where 条件不能为空，并且必须以‘where’开头！");
        try {
            String tableName = suu.getTableName(t);
            queryRunner.update(suu.getWhereUpdateSql(t, tableName, whereCondition),realValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRowByOriginalSql(String sql,Object... realValue) {
        if (DebugController.isDebugOpent) System.out.println(sql);
        try {
            queryRunner.update(sql,realValue);
        } catch (SQLException e) {
            //e.printStackTrace();
            try {
                jdbcTemplate.update(sql, realValue);
            } catch (DataAccessException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public T querySingleBeanByPrimaryKey(Class<T> clz, Object primaryKey) {
        String tableName = ssu.getTableName(clz);
        Field[] fields = clz.getDeclaredFields();
        Method[] methods = clz.getDeclaredMethods();
        String whereCondition = null;
        for (int i = 0; i < fields.length; i++) {
            whereCondition = ssu.setSqlPrimaryKey(fields[i], methods, "?");
            break;
        }
        String f = ssu.getQueryFields(clz);
        String sql = Select + f + From + tableName + Space + whereCondition;
        if (DebugController.isDebugOpent) System.out.println(sql);
        return queryHandle(clz,sql,primaryKey);
    }

    @Override
    public T querySingleBeanByWhereCondition(Class<T> clz, String whereCondition,Object... realValue) {
        if (whereCondition == null || "".equals(whereCondition.trim()) || !whereCondition.startsWith("where"))
            throw new RuntimeException("执行此查询方法时，where 条件不能为空，并且必须以‘where’开头！");
        String tableName = ssu.getTableName(clz);
        String f = ssu.getQueryFields(clz);
        String sql = Select + f + From + tableName + Space + whereCondition;
        if (DebugController.isDebugOpent) System.out.println(sql);
        return queryHandle(clz,sql,realValue);
    }

    /**
     * 使用原生sql的时候，查询的字段需要对应到实体类的属性字段，所以使用别名的方式查询对应实体类的属性字段
     * @param clz
     * @param sql 原生sql（需要注意查询返回的必须是单行数据，查询的字段，必须被实体类对象字段所包含）
     * @return
     */
    @Override
    public T querySingleBeanByOriginalSql(Class<T> clz,String sql,Object... realValue) {
        if (DebugController.isDebugOpent) System.out.println(sql);
        T t = null;
        try {
            t = queryRunner.query(sql, new BeanHandler<T>(clz),realValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public List<T> queryAllBeansList(Class<T> clz) {
        String tableName = ssu.getTableName(clz);
        String f = ssu.getQueryFields(clz);
        String sql = Select + f + From + tableName;
        if (DebugController.isDebugOpent) System.out.println(sql);
        return handleListBean(clz,sql);
    }

    @Override
    public List<T> queryBeansListByWhereCondition(Class<T> clz, String whereCondition,Object... realValue) {
        if (whereCondition == null || "".equals(whereCondition.trim()) || !whereCondition.startsWith("where"))
            throw new RuntimeException("执行此查询方法时，where 条件不能为空，并且必须以‘where’开头！");
        String tableName = ssu.getTableName(clz);
        String f = ssu.getQueryFields(clz);
        String sql = Select + f + From + tableName + Space + whereCondition;
        if (DebugController.isDebugOpent) System.out.println(sql);
        return handleListBean(clz,sql,realValue);
    }

    /**
     * 使用原生sql的时候，查询的字段需要对应到实体类的属性字段，所以使用别名的方式查询对应实体类的属性字段
     * @param clz
     * @param sql 原生sql
     * @return
     */
    @Override
    public List<T> queryBeansListByOriginalSql(Class<T> clz,String sql,Object... realValue) {
        if (DebugController.isDebugOpent) System.out.println(sql);
        List<T> list = null;
        try {
            list = queryRunner.query(sql, new BeanListHandler<T>(clz),realValue);//queryRunner.query
            //List<Map<String,Object>> maps = queryRunner.query("", new MapListHandler());
        } catch (SQLException e) {
            //e.printStackTrace();
            try {
                list = queryRunner.query(sql, new BeanListHandler<T>(clz));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public Map<String, Object> querySingleMapByPrimaryKey(Class<T> clz,Object primaryKey) {
        String tableName = ssu.getTableName(clz);
        Field[] fields = clz.getDeclaredFields();
        Method[] methods = clz.getDeclaredMethods();
        String whereCondition = null;
        for (int i = 0; i < fields.length; i++) {
            whereCondition = ssu.setSqlPrimaryKey(fields[i], methods, "?");
            break;
        }
        String f = ssu.getQueryFields(clz);
        String sql = Select + f + From + tableName + Space + whereCondition;
        if (DebugController.isDebugOpent) System.out.println(sql);
        return queryMapHandle(clz,sql,primaryKey);
    }

    @Override
    public Map<String, Object> querySingleMapByWhereCondition(Class<T> clz,String whereCondition, Object... realValue) {
        if (whereCondition == null || "".equals(whereCondition.trim()) || !whereCondition.startsWith("where"))
            throw new RuntimeException("执行此查询方法时，where 条件不能为空，并且必须以‘where’开头！");
        String tableName = ssu.getTableName(clz);
        String f = ssu.getQueryFields(clz);
        String sql = Select + f + From + tableName + Space + whereCondition;
        if (DebugController.isDebugOpent) System.out.println(sql);
        return queryMapHandle(clz,sql,realValue);
    }

    @Override
    public Map<String, Object> querySingleMapByOriginalSql(String sql, Object... realValue) {
        if (DebugController.isDebugOpent) System.out.println(sql);
        Map<String,Object> t = null;
        try {
            t = queryRunner.query(sql, new MapHandler(),realValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public List<Map<String, Object>> queryListMapAll(Class<T> clz) {
        String tableName = ssu.getTableName(clz);
        String f = ssu.getQueryFields(clz);
        String sql = Select + f + From + tableName;
        if (DebugController.isDebugOpent) System.out.println(sql);
        return handleListMap(clz,sql);
    }

    @Override
    public List<Map<String, Object>> queryListMapByWhereCondition(Class<T> clz,String whereCondition, Object... realValue) {
        if (whereCondition == null || "".equals(whereCondition.trim()) || !whereCondition.startsWith("where"))
            throw new RuntimeException("执行此查询方法时，where 条件不能为空，并且必须以‘where’开头！");
        String tableName = ssu.getTableName(clz);
        String f = ssu.getQueryFields(clz);
        String sql = Select + f + From + tableName + Space + whereCondition;
        if (DebugController.isDebugOpent) System.out.println(sql);
        return handleListMap(clz,sql,realValue);
    }

    @Override
    public List<Map<String, Object>> queryListMapByOriginalSql(String sql, Object... realValue) {
        if (DebugController.isDebugOpent) System.out.println(sql);
        List<Map<String,Object>> list = null;
        try {
            list = queryRunner.query(sql, new MapListHandler(),realValue);
            //List<Map<String,Object>> maps = queryRunner.query("", new MapListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
