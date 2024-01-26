package com.example.timemap.models;

import java.time.LocalDate;

public class Event {
    private String name;
    private LocalDate endTime;
    public Event(String name, LocalDate endTime){
        this.name = name;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }
}
