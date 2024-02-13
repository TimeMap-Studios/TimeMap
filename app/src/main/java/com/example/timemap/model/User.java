package com.example.timemap.model;

import com.example.timemap.controller.UserController;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private long id;
    private String username;
    private String email;
    private String pass;

    public User(String username, String email, String pass) {
        this.username = username;
        this.email = email;
        this.pass = UserController.getInstance().hashSha256(pass);
    }

    public User(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
