package com.example.timemap.model;

import com.example.timemap.ui.eventList.EventListFragment;
import com.example.timemap.utils.StringTools;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Event implements Comparable<Event>, Serializable {

    private static final long serialVersionUID = 1L;
    private long eventId;
    private String name;
    private CustomDateTime endTime;
    private String description;
    private User user;
    private Set<String> filters;

    public Event(String name, String description, CustomDateTime endTime, String filters, User user) {
        setFilters(filters);
        this.name = name;
        this.description = description;
        this.endTime = endTime;
        this.user = user;
    }

    public Event() {
    }

    /**
     * Gets the id of the event
     * @return the id of the event
     */
    public long getEventId() {
        return eventId;
    }

    /**
     * Sets the id of the event
     * @param eventId the id of the event
     */
    public Event setEventId(long eventId) {
        this.eventId = eventId;
        return this;
    }

    /**
     * Gets the name of the event
     * @return the name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the event
     * @param name the name of the event
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the end time of the event
     * @return the endtime as a CustomDateTime
     */
    public CustomDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time
     * @param endTime the Calendar that correspond to the endtime
     */
    public void setEndTime(Calendar endTime) {
        this.endTime = new CustomDateTime(endTime);
    }

    /**
     * Sets the end time
     * @param miliseconds the miliseconds that correspond to the endtime
     */
    public void setEndTime(long miliseconds) {
        this.endTime = new CustomDateTime(miliseconds);
    }

    /**
     * Sets the end time
     * @param endTime a CustomDateTime representing the end time
     */
    public void setEndTime(CustomDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     *  Gets the end time of the event as miliseconds
     * @return
     */
    public long getEndTimeAsMiliseconds() {
        return endTime.getAsMilliseconds();
    }

    /**
     * Gets the end time of the event as a Calendar
     * @return
     */
    public Calendar getEndTimeAsCalendar() {
        return endTime.getCalendar();
    }

    /**
     * Gets the remaining time
     * @return the remaining time to the end of the event as a string
     */
    public String getRemainingTime() {
        return endTime.getTimeRemaining(CustomDateTime.now());
    }

    /**
     * Gets the description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the filters
     * @return the filters as a set
     */
    public Set<String> getFilters() {
        if (filters == null) return new HashSet<>();
        return filters;
    }

    /**
     * Sets the filters
     * @param filters all the filters as a String separated by ";"
     */
    public void setFilters(String filters) {
        if (filters == null || filters.trim() == "") {
            this.filters = null;
        } else {
            this.filters = new HashSet<>();
            Arrays.stream(filters.split(";")).forEach(f -> {
                f = f.trim().toLowerCase();
                if (f != "") this.filters.add(StringTools.capitalize(f));
            });
        }
    }

    /**
     * @return all the filters as a String separated by ";"
     */
    public String getFiltersAsString() {
        StringBuilder sb = new StringBuilder();
        filters.forEach(f -> {
            sb.append(f + ";");
        });
        return sb.toString();
    }

    /**
     * Checks if the event have a filter
     * @param selectedFilter the filter to check if the event have
     * @return true if the event have that filter, false if not
     */
    public boolean hasFilter(String selectedFilter) {
        if (selectedFilter == null || selectedFilter.trim() == "" || filters == null) return false;
        if (selectedFilter == EventListFragment.DEFAULT_FILTER) return true;
        for (String filter : filters) {
            if (filter.equals(selectedFilter)) return true;
        }
        return false;
    }

    /**
     * Checks if the event is at a day
     * @param date the day to check if the day is at
     * @return true if the event is at the day provided as parameter
     */
    public boolean isItAtDay(CustomDateTime date) {
        if (date == null) return false;
        if (date.isAtSameDate(endTime)) return true;
        return false;
    }

    /**
     * Compares if all fields are equal to tho ones of the objet provided as parameter
     * @param obj the object to compare
     * @return true if the object have the same values in all fields, false if obj its null, a different class or have a different value in any field
     */
    public boolean equalsAllFields(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Event otherEvent = (Event) obj;
        return eventId == otherEvent.eventId &&
                Objects.equals(name, otherEvent.name) &&
                Objects.equals(endTime, otherEvent.endTime) &&
                Objects.equals(description, otherEvent.description) &&
                Objects.equals(filters, otherEvent.filters);
    }

    /**
     * @return Hash code calculated using all fields
     */
    public int hashAllFields() {
        return Objects.hash(eventId, name, endTime, description, filters);
    }

    /**
     *
     * @param obj the object to be compared
     * @return true if have the same id, false if obj its null, a different class or have a different id
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Event otherEvent = (Event) obj;
        return eventId == otherEvent.eventId;
    }

    /**
     * @return Hash code calculated just using the id
     */
    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    /**
     * Compares first by endTime, then by name
     * @param otherEvent the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Event otherEvent) {
        int endTimeComparison = this.endTime.compareTo(otherEvent.endTime);
        if (endTimeComparison != 0) {
            return endTimeComparison;
        }
        return this.name.compareTo(otherEvent.name);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}