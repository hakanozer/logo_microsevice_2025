package com.works.models;

@lombok.Data
public class Data {
    private String access_token;
    private String token_type;
    private long expires_in;
    private User user;
}
