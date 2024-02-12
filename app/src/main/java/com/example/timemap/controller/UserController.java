package com.example.timemap.controller;

import com.example.timemap.MainActivity;
import com.example.timemap.db.DatabaseController;
import com.example.timemap.model.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserController {
    private static UserController instance;
    private DatabaseController dbController;
    private UserController(){
        dbController = new DatabaseController(MainActivity.instance.getApplicationContext());
        dbController.createDatabase();
    }

    public static UserController getInstance(){
        if(instance==null){
            instance = new UserController();
        }
        return instance;
    }
/*
    public boolean addUser(User newUser){

    }

    public boolean removeUser(User user){

    }

    public boolean updateUser(User user){

    }
*/
    public User getCurrentUser(String pass, String username){
        dbController.open();
        User user = dbController.getUser(hashSha256(pass), username);
        dbController.close();
        return user;
    }

    public String hashSha256(String pass) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(pass.getBytes("UTF-8"));
            byte[] digest = sha256.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(String.format("%02x", digest[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
