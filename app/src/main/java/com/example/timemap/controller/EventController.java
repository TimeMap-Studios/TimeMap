package com.example.timemap.controller;

import com.example.timemap.MainActivity;
import com.example.timemap.db.DatabaseController;

/*
* Implementa métodos dao
* */
public class EventController {
    private EventController instance;
    private DatabaseController dbController;
    private EventController(){
        dbController = new DatabaseController(MainActivity.instance.getApplicationContext());
        dbController.createDatabase();
    }

    public EventController getInstance(){
        if(instance==null){
            instance = new EventController();
        }
        return instance;
    }
/*
    public boolean addEvent(Event newEvent){
        dbHelper
    }

    public boolean removeEvent(Event event){

    }

    public boolean updateEvent(Event event){

    }

    public Set<Event> getAll(){

    }*/
}
