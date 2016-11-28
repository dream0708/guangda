package org.keega.idea.dao;

import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.keega.idea.model.User;
import org.keega.idea.util.dao.debug.DebugController;
import org.keega.idea.util.dao.impl.BaseDao;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/30.
 */
@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

    @Override
    public void add(User user) {
        super.insertBean(user);
    }

    @Override
    public void update(User user) {
        super.updateRowByBean(user);
    }

    @Override
    public void delete(String id) {
        super.deleteRowByPrimaryKey(User.class, id);
    }

    @Override
    public User load(String id) {
        return super.querySingleBeanByPrimaryKey(User.class, id);
    }

    @Override
    public List<User> findAllUser() {
        return super.queryAllBeansList(User.class);
    }



    @Override
    public Map<String, Object> querySingleMapByPrimaryKey(Object primaryKey) {
        return super.querySingleMapByPrimaryKey(User.class, primaryKey);
    }
    @Override
    public Map<String, Object> querySingleMapByWhereCondition(String whereCondition, Object... realValue) {
        return super.querySingleMapByWhereCondition(User.class, whereCondition, realValue);
    }
    @Override
    public Map<String, Object> querySingleMapByOriginalSql(String sql, Object... realValue) {
        return super.querySingleMapByOriginalSql(sql,realValue);
    }
    @Override
    public List<Map<String, Object>> queryListMapAll() {
        return super.queryListMapAll(User.class);
    }
    @Override
    public List<Map<String, Object>> queryListMapByWhereCondition(String whereCondition, Object... realValue) {
        return super.queryListMapByWhereCondition(User.class, whereCondition, realValue);
    }
    @Override
    public List<Map<String, Object>> queryListMapByOriginalSql(String sql, Object... realValue) {
        return super.queryListMapByOriginalSql(sql, realValue);
    }
}
