package com.example.timemap.controller;

import com.example.timemap.LoginActivity;
import com.example.timemap.MainActivity;
import com.example.timemap.db.DatabaseController;
import com.example.timemap.model.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
* This class is responsible for handling user data and interactions with the database.
*/
public class UserController {
    // Singleton instance of UserController
    private static UserController instance;
    // Database controller for handling database interactions
    private DatabaseController dbController;
    // The currently logged in user
    private User currentUser;
    // Private constructor to prevent instantiation from outside the class
    private UserController(){
        dbController = new DatabaseController(LoginActivity.getInstance().getApplicationContext());
        dbController.createDatabase();
    }
    // Gets the singleton instance of UserController
    public static UserController getInstance(){
        if(instance==null){
            instance = new UserController();
        }
        return instance;
    }
    // remove the current user from the database
    public boolean removeUser(User user){
        dbController.open();
        return dbController.removeUser(user);
    }
    // update the current user in the database
    public boolean updateUser(User user){
        dbController.open();
        return dbController.updateUser(user);
    }
    // check if the email exists in the database
    public boolean emailExists(String email){
        dbController.open();
        return dbController.queryEmailExists(email);
    }
    // check if the username exists in the database
    public boolean usernameExists(String user){
        dbController.open();
        return dbController.queryUserExists(user);
    }
    // get the user from the database
    public User getLoginUser(String pass, String username){
        dbController.open();
        return dbController.queryGetUser(hashSha256(pass), username);
    }
    // register a new user in the database
    public void registerNewUser(String username, String email, String pass){
        dbController.open();
        User newUser = new User(username,email,pass);
        dbController.addNewUser(newUser);
    }
    // hash the password using SHA-256
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
    // get the current user
    public User getCurrentUser() {
        return currentUser;
    }
    // set the current user
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
