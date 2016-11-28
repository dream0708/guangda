package org.keega.idea.dao;

import org.keega.idea.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/30.
 */
public interface IUserDao {
    public void add(User user);

    public void update(User user);

    public void delete(String id);

    public User load(String id);

    public List<User> findAllUser();

    public Map<String, Object> querySingleMapByPrimaryKey(Object primaryKey);

    public Map<String, Object> querySingleMapByWhereCondition(String whereCondition, Object... realValue) ;

    public Map<String, Object> querySingleMapByOriginalSql(String sql, Object... realValue);

    public List<Map<String, Object>> queryListMapAll();


    public List<Map<String, Object>> queryListMapByWhereCondition(String whereCondition, Object... realValue);

    public List<Map<String, Object>> queryListMapByOriginalSql(String sql, Object... realValue) ;

}
