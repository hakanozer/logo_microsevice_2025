package com.works.entities;

import com.works.models.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    private Long pid;

    private Date basketdate = new Date();
}
