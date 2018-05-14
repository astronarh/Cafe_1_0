package ru.astronarh.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.astronarh.model.UserRoles;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class UserRoleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public UserRoles addUserRole(UserRoles userRoles) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(userRoles);
        return userRoles;
    }

    public UserRoles getUserRole(int id) {
        return null;
    }

    public boolean deleteUserRole(int id) {
        return false;
    }

    public List<UserRoles> userRoles(String login) {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserRoles> userRoles = session.createCriteria(UserRoles.class).list();
        return userRoles.stream().filter(userRole -> userRole.getLogin().equals(login)).collect(Collectors.toList());
    }

    public boolean isAdmin(String login) {
        List<UserRoles> userRoles = this.userRoles(login);
        return userRoles.size() > 1;
    }

    public void deleteAdminUserRole(String login) {
        Session session = this.sessionFactory.getCurrentSession();
        List<UserRoles> userRoles = this.userRoles(login);
        userRoles.forEach(userRole -> { if (userRole.getRole().equals("ROLE_ADMIN")) session.delete(userRole); } );
    }
}
