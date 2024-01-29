package com.example.timemap.models;

import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class EventList {
    private static EventList instance;
    private Set<Event> events;
    private Set<CustomDateTime> days;

    public EventList() {
        events = new TreeSet<>();
        days = new TreeSet<>();
        loadTestData();
    }

    public static EventList getInstance() {
        if (instance == null) instance = new EventList();
        return instance;
    }

    private void loadTestData() {
        addEvents(
                new Event("Examen de química", "", CustomDateTime.now(), "examen;química").setEventId(1),
                new Event("Examen de matemáticas", "", CustomDateTime.now(), "examen;matemáticas").setEventId(2),
                new Event("Deberes de química", "", CustomDateTime.now(), "deberes;química").setEventId(3),
                new Event("Sale el nuevo champ Smolder", "", new CustomDateTime(2024, 2, 7), "League of legends").setEventId(4),
                new Event("Entrega de filosofía", "", CustomDateTime.now().addDays(2), "proyecto;filosofía").setEventId(5),
                new Event("Deberes de tecnología", "", CustomDateTime.now().addDays(4), "deberes;tecnología").setEventId(6),
                new Event("Deberes de lengua", "", CustomDateTime.now().addDays(1), "deberes;lengua").setEventId(7),
                new Event("Deberes de matemáticas", "", CustomDateTime.now().addDays(3), "deberes;matemáticas").setEventId(8)
        );
    }

    /**
     * Adds an Event to the list
     *
     * @param e Event to add
     * @return false if the set already contains the Event, true if it not
     */
    public boolean addEvent(Event e) {
        if (e == null) return false;
        if (true) { // implementar añadirlo a la base de datos
            if(events.add(e)){
                if(!containsDate(e.getEndTime())) days.add(e.getEndTime());
                return true;
            }
        }
        return false;
    }

    public boolean containsDate(CustomDateTime date){
        for(CustomDateTime day : days){
            if(date.isAtSameDate(day)) return true;
        }
        return false;
    }

    /**
     * Removes an Event from the set
     *
     * @param e Event to remove
     * @return true if the event is removed, false if the set does not contains the Event
     */
    public boolean removeEvent(Event e) {
        if (e == null) return false;
        if (true) { // implementar eliminarlo de la base de datos
            return events.remove(e);
        }
        return false;
    }

    /**
     * If the list contains a event with the same id, replaces it
     *
     * @param e Event to add
     * @return false if the set already contains the Event, false if it not
     */
    public boolean editEvent(Event e) {
        if (e == null) return false;
        if (true) { // implementar editarlo en la base de datos
            if (removeEvent(e)) {
                return addEvent(e);
            }
        }
        return false;
    }

    public long getNewEventId() {
        return new Random().nextLong();
    }

    /**
     * @return The set of events
     */
    public Set<Event> getEvents() {
        return events;
    }

    public Set<CustomDateTime> getDays() {
        return days;
    }

    /**
     * Empties the event list and adds all the events in the collection
     *
     * @param events The set of events
     */
    public void setEvents(Collection<Event> events) {
        events = new TreeSet<>();
        addEvents(events.toArray(new Event[events.size()]));
    }

    /**
     * Empties the set of events and adds as many events as provided as params
     *
     * @param events Events to add to the list
     */
    public void setEvents(Event... events) {
        this.events = new TreeSet<>();
        addEvents(events);
    }

    /**
     * Adds all the events in the collection
     *
     * @param events The collection of events
     */
    public void addEvents(Collection<Event> events) {
        addEvents(events.toArray(new Event[events.size()]));
    }

    /**
     * Adds as many events as provided as params
     *
     * @param events Events to add to the list
     */
    public void addEvents(Event... events) {
        for (Event e : events) {
            addEvent(e);
        }
    }

    public Set<Event> getEventsByDay(CustomDateTime date) {
        Set<Event> events = new TreeSet<>();
        this.events.forEach(e -> {
            if (e.isItAtDay(date)) events.add(e);
        });
        return events;
    }

    public Set<Event> getTodayEvents() {
        return getEventsByDay(CustomDateTime.now());
    }

    public Event getEventById(int id) {
        for (Event e : events) {
            if (e.getEventId() == id) return e;
        }
        return null;
    }
}
