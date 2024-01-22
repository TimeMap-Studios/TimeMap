package com.example.timemap.model;

import java.time.LocalDateTime;

public class Event {
    private long eventId;
    private String name;
    private String description;
    private LocalDateTime limit;

    public Event(String name, String description, LocalDateTime limit) {
        this.name = name;
        this.description = description;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLimit() {
        return limit;
    }

    public void setLimit(LocalDateTime limit) {
        this.limit = limit;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
