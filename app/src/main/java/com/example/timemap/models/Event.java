package com.example.timemap.models;

import com.example.timemap.ui.eventList.EventListFragment;
import com.example.timemap.utils.StringTools;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Event implements Comparable<Event>, Serializable {

    private long eventId;
    private String name;
    private CustomDateTime endTime;
    private String description;
    private Set<String> filters;

    public Event(String name, String description, CustomDateTime endTime, String filters) {
        setFilters(filters);
        this.name = name;
        this.description = description;
        this.endTime = endTime;
    }

    public Event() {

    }

    public long getEventId() {
        return eventId;
    }

    public Event setEventId(long eventId) {
        this.eventId = eventId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = new CustomDateTime(endTime);
    }

    public void setEndTime(CustomDateTime endTime) {
        this.endTime = endTime;
    }

    public Calendar getEndTimeAsCalendar() {
        return endTime.getAsCalendar();
    }

    public String getRemainingTime() {
        return endTime.timeRemaining(CustomDateTime.now());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getFilters() {
        if (filters == null) return new HashSet<>();
        return filters;
    }

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

    public String getFiltersAsString() {
        StringBuilder sb = new StringBuilder();
        filters.forEach(f -> {
            sb.append(f + ";");
        });
        return sb.toString();
    }

    public boolean hasFilter(String selectedFilter) {
        if (selectedFilter == null || selectedFilter.trim() == "" || filters == null) return false;
        if (selectedFilter == EventListFragment.DEFAULT_FILTER) return true;
        for (String filter : filters) {
            if (filter.equals(selectedFilter)) return true;
        }
        return false;
    }

    public boolean isItAtDay(CustomDateTime date) {
        if (date == null) return false;
        if (date.sameDate(endTime)) return true;
        return false;
    }

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

    public int hashAllFields() {
        return Objects.hash(eventId, name, endTime, description, filters);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Event otherEvent = (Event) obj;
        return eventId == otherEvent.eventId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    @Override
    public int compareTo(Event otherEvent) {
        int endTimeComparison = this.endTime.compareTo(otherEvent.endTime);
        if (endTimeComparison != 0) {
            return endTimeComparison;
        }
        return this.name.compareTo(otherEvent.name);
    }
}
