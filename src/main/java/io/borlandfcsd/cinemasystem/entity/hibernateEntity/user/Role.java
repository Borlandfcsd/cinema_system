package io.borlandfcsd.cinemasystem.entity.hibernateEntity.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
