package org.keega.idea.service;

import org.keega.idea.dao.IUserDao;
import org.keega.idea.model.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by Administrator on 2016/9/30.
 */
@Service("userService")
public class UserService implements IUserService {

    @Inject
    @Named("userDao")
    private IUserDao userDao;

    @Override
    public void add(User user) {
        this.userDao.add(user);
    }

    @Override
    public void update(User user) {
        this.userDao.update(user);
    }

    @Override
    public void delete(String id) {
        this.userDao.delete(id);
    }

    @Override
    public User load(String id) {
        return this.userDao.load(id);
    }

    @Override
    public List<User> findAllUser() {
        return this.userDao.findAllUser();
    }

}
