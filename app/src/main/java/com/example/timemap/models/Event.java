package com.example.timemap.models;

import com.example.timemap.ui.eventList.EventListFragment;
import com.example.timemap.utils.StringTools;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Event {
    private String name;
    private LocalDate endTime;
    private Set<String> filters;

    public Event(String name, LocalDate endTime, String filters) {
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

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
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

    public boolean hasFilter(String selectedFilter) {
        if (selectedFilter == null || selectedFilter.trim() == "" || filters == null) return false;
        if (selectedFilter == EventListFragment.DEFAULT_FILTER) return true;
        for (String filter : filters) {
            if (filter.equals(selectedFilter)) return true;
        }
        return false;
    }
}
