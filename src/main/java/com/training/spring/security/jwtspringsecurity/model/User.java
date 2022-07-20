package com.training.spring.security.jwtspringsecurity.model;

import com.training.spring.security.jwtspringsecurity.common.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Table(schema = "users")

public class User extends AbstractEntity<Long> {

    @EqualsAndHashCode.Include
    @Column(unique = true, nullable = false)
    private String mail;

    @Column
    @CreationTimestamp
    private Date createdAt;

    @Column
    @NonNull
    private String password;

    @Column
    @NonNull
    private  String name;

    @Column
    @NonNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private boolean isActive;

    private  boolean isNotLocked;

    @ElementCollection
    private ArrayList<String> authorities = new ArrayList<>();







}
