package ru.astronarh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astronarh.dao.UserRoleDAO;
import ru.astronarh.model.UserRoles;

import java.util.List;

@Service("userRoleService")
public class UserRoleService {

    @Autowired
    UserRoleDAO userRoleDAO;

    @Transactional
    public UserRoles addUserRole(UserRoles userRoles) {
        return userRoleDAO.addUserRole(userRoles);
    }

    @Transactional
    public UserRoles getUserRole(int id) {
        return null;
    }

    @Transactional
    public boolean deleteUserRole(int id) {
        return false;
    }

    @Transactional
    public List<UserRoles> userRoles(String login) {
        return userRoleDAO.userRoles(login);
    }

    @Transactional
    public boolean isAdmin(String login) {
        return userRoleDAO.isAdmin(login);
    }

    @Transactional
    public void deleteAdminUserRole(String login) {
        userRoleDAO.deleteAdminUserRole(login);
    }
}
