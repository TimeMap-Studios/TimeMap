package com.example.timemap.controller;

import android.database.Cursor;

import com.example.timemap.MainActivity;
import com.example.timemap.db.TestAdapter;
import com.example.timemap.model.Event;

import java.util.Set;
/*
* Implementa métodos dao
* */
public class EventController {
    private EventController instance;
    private TestAdapter dbHelper;
    private EventController(){
        dbHelper = new TestAdapter(MainActivity.instance.getApplicationContext());
        dbHelper.createDatabase();
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
