package com.works.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iid;

    private String username;
    private String roles;
    private String url;
    private String sessionId;
    private String userAgent;
    private String ip;
    private Long time;
}
