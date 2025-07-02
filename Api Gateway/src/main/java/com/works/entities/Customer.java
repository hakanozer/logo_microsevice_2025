package com.works.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.swing.text.SimpleAttributeSet;
import java.util.List;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    @Column(unique = true, length = 100)
    private String username;

    @Column(unique = true)
    private String password;

    private Boolean enabled;

    @ManyToMany
    private List<Role> roles;

}
