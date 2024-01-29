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
                new Event("Chemistry Exam", "", CustomDateTime.now().subtractDays(1), "exam;chemistry").setEventId(1),
                new Event("Mathematics Exam", "", CustomDateTime.now().addHours(2).addSeconds(17), "exam;mathematics").setEventId(2),
                new Event("Chemistry Homework", "", CustomDateTime.now().addMinutes(5), "homework;chemistry").setEventId(3),
                new Event("New Champ Smolder Release", "", new CustomDateTime(2024, 2, 7), "League of Legends").setEventId(4),
                new Event("Philosophy Project Submission", "", CustomDateTime.now().addDays(2), "project;philosophy").setEventId(5),
                new Event("Technology Homework", "", CustomDateTime.now().addDays(4), "homework;technology").setEventId(6),
                new Event("Language Homework", "", CustomDateTime.now().addDays(1).addMinutes(15), "homework;language").setEventId(7),
                new Event("Mathematics Homework", "", CustomDateTime.now().addDays(3).addMinutes(7), "homework;mathematics").setEventId(8),
                new Event("Physics Exam", "", CustomDateTime.now().addDays(5).addMinutes(2), "exam;physics").setEventId(9),
                new Event("Literature Homework", "", CustomDateTime.now().addDays(2).addMinutes(51), "homework;literature").setEventId(10),
                new Event("Biology Project Presentation", "", CustomDateTime.now().addDays(7).addMinutes(23), "project;biology").setEventId(11),
                new Event("History Quiz", "", CustomDateTime.now().subtractDays(3).addMinutes(2), "quiz;history").setEventId(12),
                new Event("Computer Science Coding Assignment", "", CustomDateTime.now().subtractDays(6).addMinutes(87), "assignment;computer science").setEventId(13),
                new Event("Art Exhibition", "", CustomDateTime.now().addDays(8).addMinutes(35), "event;art").setEventId(14),
                new Event("Music Concert", "", CustomDateTime.now().addDays(4).addMinutes(43), "event;music").setEventId(15),
                new Event("Sports Tournament", "", CustomDateTime.now().addDays(9).addMinutes(32), "event;sports").setEventId(16)
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
