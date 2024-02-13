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

    public boolean removeUser(User user){
        dbController.open();
        return dbController.removeUser(user);
    }

    public boolean updateUser(User user){
        dbController.open();
        return dbController.updateUser(user);
    }

    public boolean emailExists(String email){
        dbController.open();
        return dbController.queryEmailExists(email);
    }

    public boolean usernameExists(String user){
        dbController.open();
        return dbController.queryUserExists(user);
    }

    public User getLoginUser(String pass, String username){
        dbController.open();
        return dbController.queryGetUser(hashSha256(pass), username);
    }

    public void registerNewUser(String username, String email, String pass){
        dbController.open();
        User newUser = new User(username,email,pass);
        dbController.addNewUser(newUser);
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
