package com.example.timemap.controller;

import android.util.Log;

import com.example.timemap.MainActivity;
import com.example.timemap.db.DatabaseController;
import com.example.timemap.model.Event;
import com.example.timemap.model.User;

import java.util.Set;

/*
* Implementa métodos dao para evento interactuando con DatabaseController
*/
public class EventController {
    private static EventController instance;
    private DatabaseController dbController;
    private EventController(){
        dbController = new DatabaseController(MainActivity.instance.getApplicationContext());
        dbController.createDatabase();
    }
    public static EventController getInstance(){
        if(instance==null){
            instance = new EventController();
        }
        return instance;
    }

    // Métodos dao
    public boolean addEvent(Event newEvent){
        dbController.open();
        return dbController.addNewEvent(newEvent);
    }

    public boolean removeEvent(Event event){
        dbController.open();
        return dbController.removeEvent(event);
    }

    public boolean updateEvent(Event event){
        dbController.open();
        return dbController.updateEvent(event);
    }

    // recoge current user desde UserController. se obvia que currentUser ya está seteado
    public Set<Event> getCurrentUserEvents(){
        dbController.open();
        Log.e("current user",UserController.getInstance().getCurrentUser().getUsername());
        return dbController.getUserEvents(UserController.getInstance().getCurrentUser());
    }
    public Set<Event> getUserEvents(User user){
        dbController.open();
        Log.e("current user",UserController.getInstance().getCurrentUser().getUsername());
        return dbController.getUserEvents(user);
    }
}
