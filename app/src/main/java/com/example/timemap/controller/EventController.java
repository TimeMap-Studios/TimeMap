package com.example.timemap.controller;

import com.example.timemap.MainActivity;
import com.example.timemap.db.DatabaseController;
import com.example.timemap.model.Event;

import java.util.Set;

/*
* Implementa métodos dao para evento interactuando con DatabaseController
* */
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

    public Set<Event> getCurrentUserEvents(){
        dbController.open();
        return dbController.getUserEvents(UserController.getInstance().getCurrentUser());
    }
}
