package com.example.timemap.model;

import com.example.timemap.controller.EventController;
import com.example.timemap.controller.UserController;

import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a list of events with methods to add, remove, edit, and retrieve events.
 */
public class EventList {
    private static EventList instance;
    private Set<Event> events;
    private Set<CustomDateTime> days;

    /**
     * Constructs an EventList instance, initializes sets, and loads test data.
     */
    public EventList() {
        days = new TreeSet<>();
        User user= UserController.getInstance().getCurrentUser();
        events = EventController.getInstance().getUserEvents(user);
        if(events ==null){
            events = new TreeSet<>();
        } else {
            setDayList(events);
        }
    }

    public static void resetInstance(){
        instance=null;
    }
    /**
     * Adds the days from a set of events
     * @param events The event set to get the days from
     */
    private void setDayList(Set<Event> events){
        events.forEach( e -> {
            if (!containsDate(e.getEndTime())) days.add(e.getEndTime());
        });
    }

    /**
     * Gets the singleton instance of EventList.
     *
     * @return The singleton instance of EventList.
     */
    public static EventList getInstance() {
        if (instance == null) instance = new EventList();
        return instance;
    }


    /**
     * Adds an Event to the list.
     *
     * @param e Event to add.
     * @return false if the set already contains the Event, true if it does not.
     */
    public boolean addEvent(Event e) {
        if (e == null) return false;
        if(!EventController.getInstance().addEvent(e)){
            return false;
        }
        if (events.add(e)) {
            if (!containsDate(e.getEndTime())) days.add(e.getEndTime());
            return true;
        }
        return false;
    }

    /**
     * Checks if the set contains events on a specific date.
     *
     * @param date The date to check.
     * @return false if the set doesn't have events on that day, true if there's at least one event.
     */
    public boolean containsDate(CustomDateTime date) {
        for (CustomDateTime day : days) {
            if (date.isAtSameDate(day)) return true;
        }
        return false;
    }

    /**
     * Removes an Event from the set.
     *
     * @param e Event to remove.
     * @return true if the event is removed, false if the set does not contain the Event.
     */
    public boolean removeEvent(Event e) {
        if (e == null) return false;
        if(!EventController.getInstance().removeEvent(e)) return false;
        return events.remove(e);
    }

    /**
     * Edits an Event in the list.
     *
     * @param e Event to edit.
     * @return false if the set already contains the Event, false if it does not.
     */
    public boolean editEvent(Event e) {
        if (e == null) return false;
        if (!EventController.getInstance().updateEvent(e)) return false;
        if (removeEvent(e)) {
            return addEvent(e);
        }
        return false;
    }

    /**
     * Generates a new unique event ID using a random long value.
     *
     * @return A new unique event ID.
     */
    public long getNewEventId() {
        return new Random().nextLong();
    }

    /**
     * Gets the set of events.
     *
     * @return The set of events.
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * Gets the set of days that have any event.
     *
     * @return The set of days.
     */
    public Set<CustomDateTime> getDays() {
        return days;
    }

    /**
     * Empties the event list and adds all the events in the collection.
     *
     * @param events The set of events.
     */
    public void setEvents(Collection<Event> events) {
        this.events = new TreeSet<>();
        addEvents(events.toArray(new Event[events.size()]));
    }

    /**
     * Empties the set of events and adds as many events as provided as parameters.
     *
     * @param events Events to add to the list.
     */
    public void setEvents(Event... events) {
        this.events = new TreeSet<>();
        addEvents(events);
    }

    /**
     * Adds all the events in the collection.
     *
     * @param events The collection of events.
     */
    public void addEvents(Collection<Event> events) {
        addEvents(events.toArray(new Event[events.size()]));
    }

    /**
     * Adds as many events as provided as parameters.
     *
     * @param events Events to add to the list.
     */
    public void addEvents(Event... events) {
        for (Event e : events) {
            addEvent(e);
        }
    }

    /**
     * Retrieves a set of events occurring on a specific date.
     *
     * @param date The date to check for events.
     * @return The set of events on the given date.
     */
    public Set<Event> getEventsByDay(CustomDateTime date) {
        Set<Event> events = new TreeSet<>();
        this.events.forEach(e -> {
            if (e.isItAtDay(date)) events.add(e);
        });
        return events;
    }

    /**
     * Retrieves a set of events occurring today.
     *
     * @return The set of events happening today.
     */
    public Set<Event> getTodayEvents() {
        return getEventsByDay(CustomDateTime.now());
    }

    /**
     * Retrieves an event by its ID.
     *
     * @param id The ID of the event to retrieve.
     * @return The event with the specified ID, or null if not found.
     */
    public Event getEventById(int id) {
        for (Event e : events) {
            if (e.getEventId() == id) return e;
        }
        return null;
    }
}
