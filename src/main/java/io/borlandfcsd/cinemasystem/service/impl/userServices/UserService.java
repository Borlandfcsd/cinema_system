package io.borlandfcsd.cinemasystem.service.impl.userServices;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.Role;
import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.User;
import io.borlandfcsd.cinemasystem.repository.RoleRepository;
import io.borlandfcsd.cinemasystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void signUp(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.getOne(1);
        roles.add(role);
        user.setRoles(roles);
        userRepository.saveAndFlush(user);
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}
