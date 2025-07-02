package com.works.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(unique = true)
    private String title;
    private Integer price;

}
