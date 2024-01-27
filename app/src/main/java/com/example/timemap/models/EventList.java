package com.example.timemap.models;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class EventList {
    private static EventList instance;
    private Set<Event> events;

    public EventList() {
        events = new TreeSet<>();
        loadTestData();
    }

    public static EventList getInstance() {
        if (instance == null) instance = new EventList();
        return instance;
    }

    private void loadTestData() {
        CustomDateTime aux = CustomDateTime.now();
        addEvents(
                new Event("Examen de química", CustomDateTime.now(), "examen;química"),
                new Event("Examen de matemáticas", CustomDateTime.now(), "examen;matemáticas"),
                new Event("Deberes de química", CustomDateTime.now(), "deberes;química"),
                new Event("Sale el nuevo champ Smolder", new CustomDateTime(2024, 2, 7), "League of legends"),
                new Event("Entrega de filosofía", CustomDateTime.now().addDays(2), "proyecto;filosofía"),
                new Event("Deberes de tecnología", CustomDateTime.now().addDays(4), "deberes;tecnología"),
                new Event("Deberes de lengua", CustomDateTime.now().addDays(1), "deberes;lengua"),
                new Event("Deberes de matemáticas", CustomDateTime.now().addDays(3), "deberes;matemáticas")
        );
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

    /**
     * Adds an Event to the list
     *
     * @param e Event to add
     * @return false if the list already contains the Event, false if it not
     */
    public boolean addEvent(Event e) {
        if (events.contains(e)) return false;
        events.add(e);
        return true;
    }

    public Set<Event> getEvents() {
        return events;
    }

    /**
     * Empties the event list and adds all the events in the collection
     *
     * @param events The collection of events
     */
    public void setEvents(Collection<Event> events) {
        events = new TreeSet<>();
        addEvents(events.toArray(new Event[events.size()]));
    }

    /**
     * Empties the event list and adds as many events as provided as params
     *
     * @param events Events to add to the list
     */
    public void setEvents(Event... events) {
        this.events = new TreeSet<>();
        addEvents(events);
    }

    public Set<Event> getEventsByDay(CustomDateTime date) {
        Set<Event> events = new TreeSet<>();
        this.events.forEach(e -> {
            if (e.isItAtDay(date)) events.add(e);
        });
        return events;
    }

    public Set<Event> getTodayEvents() {
        return getEventsByDay(CustomDateTime.today());
    }

    public Event getEventById(int id) {
        for (Event e : events) {
            if (e.getEventId() == id) return e;
        }
        return null;
    }
}
