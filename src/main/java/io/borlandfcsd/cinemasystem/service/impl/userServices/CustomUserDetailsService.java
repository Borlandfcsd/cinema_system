package io.borlandfcsd.cinemasystem.service.impl.userServices;

import io.borlandfcsd.cinemasystem.dao.GenericDao;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.Role;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private GenericDao userDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user =(User)userDao.getEntitiesByColumnName("email", s).get(0);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(Role role: user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),grantedAuthorities);
    }

    @Autowired
    public void setUserDao(GenericDao userDao) {
        this.userDao = userDao;
    }
}
