package com.example.timemap.controller;

import com.example.timemap.MainActivity;
import com.example.timemap.db.TestAdapter;
import com.example.timemap.model.User;

import java.util.Set;

public class UserController {
    private static UserController instance;
    private TestAdapter dbHelper;
    private UserController(){
        dbHelper = new TestAdapter(MainActivity.instance.getApplicationContext());
        dbHelper.createDatabase();
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
        dbHelper.open();
        User user = dbHelper.getUser(pass, username);
        dbHelper.close();
        return user;
    }
}
