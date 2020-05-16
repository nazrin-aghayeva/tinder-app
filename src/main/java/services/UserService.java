package services;

import dao.DAOUserSql;
import entities.User;

public class UserService {
    DAOUserSql userDao;

    public UserService(DAOUserSql userDao) {
        this.userDao = userDao;
    }

    public void addUser(User user){
         userDao.add(user);
    }

    public int getUserId(User user){
        return userDao.getByLogin(user).getUser_id();
    }

    public boolean loggedUser(User user){
        return userDao.getByLogin(user) != null;
    }

    public boolean checkUser(User user){
        return userDao.getByLogin(user)!= null && userDao.getByLogin(user).getPassword().equals(user.getPassword());
    }

}
