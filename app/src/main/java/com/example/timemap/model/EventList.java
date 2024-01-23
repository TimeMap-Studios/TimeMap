package com.example.timemap.model;

import java.util.ArrayList;

public class EventList {
    private ArrayList<Event> userEventList;
    private final User user;

    public EventList(User user) {
        this.userEventList = new ArrayList<>();
        this.user = user;
    }

    public ArrayList<Event> getUserEventList() {
        return userEventList;
    }

    public void setUserEventList(ArrayList<Event> userEventList) {
        this.userEventList = userEventList;
    }

    public User getUser() {
        return user;
    }

    public Event getEventById(int id){
        for (Event e : userEventList) {
            if(e.getEventId() == id){return e;}
        }
        return null;
    }
}
