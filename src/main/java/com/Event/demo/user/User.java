package com.Event.demo.user;
import com.Event.demo.common.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name ="user_name", nullable=false,unique=true)
    private String userName;
    @Column(name="password",nullable=false)
    private String password;
    @Column(name="email",nullable=false,unique=true)
    private String email;
    @Column(name="enabled")
    private boolean enabled;// used to check wether the record is active or not.
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt = LocalDateTime.now();


}
