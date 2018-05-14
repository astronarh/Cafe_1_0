package ru.astronarh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astronarh.dao.UserDAO;
import ru.astronarh.model.User;

import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    UserDAO userDao;

    @Transactional
    public User addUser(User user) {
        return userDao.addUser(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Transactional
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    public void changeUser(User user) {
        userDao.changeUser(user);
    }
}
