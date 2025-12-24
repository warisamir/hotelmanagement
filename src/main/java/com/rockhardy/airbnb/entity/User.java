package com.rockhardy.airbnb.entity;

import com.rockhardy.airbnb.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name ="app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)// to fetch the roles while creating user
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
