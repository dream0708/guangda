package org.keega.idea.util.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.keega.idea.util.dao.bduti_utill.SqlAddUtil;
import org.keega.idea.util.dao.bduti_utill.SqlDeleteUtil;
import org.keega.idea.util.dao.bduti_utill.SqlSelectUtil;
import org.keega.idea.util.dao.bduti_utill.SqlUpdateUtil;
import org.keega.idea.util.dao.interf.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 提供这个抽象类，用来备用，基于其他方式（dbutil以外）实现这些简单的增删改查
 * Created by asus_n56 on 2016/9/13.
 */
public abstract class AbstractBaseDao<T> implements IInsertDao<T>, IDeleteDao<T>, IUpdateDao<T>, ISelectDao<T>, StaticResourceProvide {

    @Inject
    protected SqlAddUtil<T> sau;
    @Inject
    protected SqlDeleteUtil<T> sdu;
    @Inject
    protected SqlUpdateUtil<T> suu;
    @Inject
    protected SqlSelectUtil<T> ssu;
    @Inject
    protected QueryRunner queryRunner;
    @Inject
    protected JdbcTemplate jdbcTemplate;

    protected T queryHandle(Class<T> clz,String sql,Object... realValue) {
        T t = null;
        try {
            t = queryRunner.query(sql, new BeanHandler<T>(clz),realValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    protected List<T> handleListBean(Class<T> clz, String sql, Object... realValue) {
        List<T> list = null;
        try {
            list = queryRunner.query(sql, new BeanListHandler<T>(clz),realValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    protected Map<String,Object> queryMapHandle(Class<T> clz, String sql, Object... realValue) {
        //T t = null;//new MapListHandler()
        Map<String,Object> t = null;
        try {
            t = queryRunner.query(sql, new MapHandler(),realValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    protected List<Map<String,Object>> handleListMap(Class<T> clz,String sql,Object... realValue) {
        List<Map<String,Object>> list = null;
        try {
            list = queryRunner.query(sql, new MapListHandler(),realValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
