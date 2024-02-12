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
    private User currentUser;
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
    public boolean removeUser(User user){

    }

    public boolean updateUser(User user){

    }
*/
    public boolean emailExists(String email){
        dbController.open();
        boolean result = dbController.queryEmailExists(email);
        dbController.close();
        return result;
    }

    public boolean usernameExists(String user){
        dbController.open();
        boolean result = dbController.queryUserExists(user);
        dbController.close();
        return result;
    }

    public User getLoginUser(String pass, String username){
        dbController.open();
        User user = dbController.queryGetUser(hashSha256(pass), username);
        dbController.close();
        return user;
    }

    public void registerNewUser(String username, String email, String pass){
        dbController.open();
        User newUser = new User(username,email,pass);
        dbController.addNewUser(newUser);
        dbController.close();
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

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
