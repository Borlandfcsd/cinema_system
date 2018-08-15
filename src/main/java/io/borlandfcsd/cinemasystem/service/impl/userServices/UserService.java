package io.borlandfcsd.cinemasystem.service.impl.userServices;

import io.borlandfcsd.cinemasystem.dao.GenericDao;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.Role;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class UserService {
    private static UserService instance;

    private GenericDao userDao;
    private GenericDao roleDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService() {

    }


    @SuppressWarnings("unchecked")
    public void signUp(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add((Role) roleDao.getEntity(1L));
        user.setRoles(roles);
        userDao.addEntity(user);
    }

    @SuppressWarnings(value = "unchecked")
    public User getByEmail(String email) {
        List<User> users = userDao.getEntitiesByColumnName("email", email);
        if (users.size() > 0) {
            return (User) users.get(0);
        }
        return null;
    }


    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    @Autowired
    public void setUserDao(GenericDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(GenericDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public static void setInstance(UserService instance) {
        UserService.instance = instance;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
}
