package org.tinder.app.services;

import org.tinder.app.dao.DAOUsersSql;
import org.tinder.app.entities.User;

public class  UsersService {
    private DAOUsersSql userDao;

    public UsersService(DAOUsersSql userDao) {
        this.userDao = userDao;
    }

    public int getUserId(User user){
        return userDao.getByLogin(user).getId();
    }

    public boolean checkUser(User user){
        User res = userDao.getByLogin(user);
        return res != null && res.getPassword().equals(user.getPassword());
    }


    public boolean checkUserByLogin(User user){
        return userDao.getByLogin(user) != null;
    }

    public void add(User item) {
        userDao.add(item);
    }

}
