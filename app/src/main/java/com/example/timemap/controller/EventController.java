package com.example.timemap.controller;

import com.example.timemap.MainActivity;
import com.example.timemap.db.DatabaseController;
import com.example.timemap.model.Event;

import java.util.Set;

/*
* Implementa métodos dao
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
        boolean result = dbController.addNewEvent(newEvent);
        dbController.close();
        return result;
    }
/*
    public boolean removeEvent(Event event){

    }

    public boolean updateEvent(Event event){

    }
*/
    public Set<Event> getCurrentUserEvents(){
        dbController.open();
        Set<Event> events = dbController.getUserEvents(UserController.getInstance().getCurrentUser());
        dbController.close();
        return events;
    }
}
