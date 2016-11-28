package org.keega.idea.service;

import org.keega.idea.model.User;

import java.util.List;

/**
 * Created by Administrator on 2016/9/30.
 */
public interface IUserService {
    public void add(User user);

    public void update(User user);

    public void delete(String id);

    public User load(String id);

    public List<User> findAllUser();
}
