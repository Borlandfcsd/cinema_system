package io.borlandfcsd.cinemasystem.entity.hibernateEntity.user;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.Role;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name="last_name",nullable = false)
    private String lastName;
    @Column(nullable = false, unique=true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Transient
    private String confirmPassword;
    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
