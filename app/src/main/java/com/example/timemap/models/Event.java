package com.example.timemap.models;

import com.example.timemap.ui.eventList.EventListFragment;
import com.example.timemap.utils.StringTools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Event implements Comparable<Event> {
    private String name;
    private CustomDateTime endTime;
    private Set<String> filters;

    public Event(String name, CustomDateTime endTime, String filters) {
        setFilters(filters);
        this.name = name;
        this.endTime = endTime;
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

    public void setEndTime(CustomDateTime endTime) {
        this.endTime = endTime;
    }

    public Set<String> getFilters() {
        if (filters == null) return new HashSet<>();
        return filters;
    }

    public String getFiltersAsString(){
        StringBuilder sb = new StringBuilder();
        filters.forEach(f->{sb.append(f+";");});
        return sb.toString();
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

    public boolean hasFilter(String selectedFilter) {
        if (selectedFilter == null || selectedFilter.trim() == "" || filters == null) return false;
        if (selectedFilter == EventListFragment.DEFAULT_FILTER) return true;
        for (String filter : filters) {
            if (filter.equals(selectedFilter)) return true;
        }
        return false;
    }

    public boolean isAtDay(CustomDateTime date) {
        if (date == null) return false;
        if (date.sameDate(endTime)) return true;
        return false;
    }

    public String getRemainingTime() {
        return endTime.timeRemaining(CustomDateTime.now());
    }

    @Override
    public int compareTo(Event otherEvent) {
        // Primero, comparar por endTime
        int endTimeComparison = this.endTime.compareTo(otherEvent.endTime);
        if (endTimeComparison != 0) {
            return endTimeComparison;
        }

        // Si endTime es igual, comparar por nombre
        return this.name.compareTo(otherEvent.name);
    }
}
