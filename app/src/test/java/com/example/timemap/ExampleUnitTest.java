package com.example.timemap;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.timemap.models.CustomDateTime;
import com.example.timemap.models.Event;
import com.example.timemap.models.EventList;

import java.util.Set;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testConstructorWithValidDateTime() {
        CustomDateTime dateTime = new CustomDateTime(2022, 1, 30, 12, 0, 0);
        assertEquals("Year should be 2022", 2022, dateTime.getYear());
        assertEquals("Month should be 1 (January)", 1, dateTime.getMonth());
        assertEquals("Day should be 30", 30, dateTime.getDay());
        assertEquals("Hour should be 12", 12, dateTime.getHour());
        assertEquals("Minute should be 0", 0, dateTime.getMinute());
        assertEquals("Second should be 0", 0, dateTime.getSecond());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidDateTime() {
        // Attempt to create an invalid date
        new CustomDateTime(2022, 2, 30, 12, 0, 0);
    }

    @Test
    public void testNow() {
        CustomDateTime now = CustomDateTime.now();
        assertTrue("Now should be an instance of CustomDateTime", now instanceof CustomDateTime);
    }

    @Test
    public void testGetMonthName() {
        CustomDateTime dateTime = new CustomDateTime(2022, 1, 30, 12, 0, 0);
        assertEquals("Month name should be January", "January", dateTime.getMonthName());
    }

    @Test
    public void testInsertSingleEvent() {
        EventList eventList = new EventList();
        CustomDateTime eventDateTime = CustomDateTime.now().addDays(3);
        Event event = new Event("Test Event", "", eventDateTime, "exam;chemistry").setEventId(69);

        boolean result = eventList.addEvent(event);

        assertTrue("Event should be added successfully", result);
        assertTrue("Event should be in the list", eventList.getEventsByDay(eventDateTime).contains(event));
    }

    @Test
    public void testInsertMultipleEventsSameDay() {
        EventList eventList = new EventList();
        CustomDateTime eventDateTime = CustomDateTime.now().addDays(3);

        Event event1 = new Event("Event 1", "", eventDateTime, "exam;chemistry").setEventId(70);
        Event event2 = new Event("Event 2", "", eventDateTime, "homework;mathematics").setEventId(71);
        Event event3 = new Event("Event 3", "", eventDateTime, "project;philosophy").setEventId(72);

        boolean result1 = eventList.addEvent(event1);
        boolean result2 = eventList.addEvent(event2);
        boolean result3 = eventList.addEvent(event3);

        assertTrue("Event 1 should be added successfully", result1);
        assertTrue("Event 2 should be added successfully", result2);
        assertTrue("Event 3 should be added successfully", result3);

        Set<Event> eventsOnDay = eventList.getEventsByDay(eventDateTime);

        assertTrue("Event 1 should be in the list", eventsOnDay.contains(event1));
        assertTrue("Event 2 should be in the list", eventsOnDay.contains(event2));
        assertTrue("Event 3 should be in the list", eventsOnDay.contains(event3));
    }

    @Test
    public void testRemoveEvent() {
        EventList eventList = new EventList();
        CustomDateTime eventDateTime = CustomDateTime.now().addDays(3);
        Event event = new Event("Test Event", "", eventDateTime, "exam;chemistry").setEventId(73);

        eventList.addEvent(event);
        boolean result = eventList.removeEvent(event);

        assertTrue("Event should be removed successfully", result);
        assertFalse("Event should not be in the list", eventList.getEventsByDay(eventDateTime).contains(event));
    }

    @Test
    public void testRemoveNonexistentEvent() {
        EventList eventList = new EventList();
        CustomDateTime eventDateTime = CustomDateTime.now().addDays(3);
        Event event = new Event("Test Event", "", eventDateTime, "exam;chemistry").setEventId(74);

        boolean result = eventList.removeEvent(event); // Attempt to remove an event not in the list

        assertFalse("Event should not be removed", result);
    }


/*
    @Test
    public void testCurrentWeek() {
        CustomDateTime dateTime = new CustomDateTime(2024, 1, 30);
        Set<CustomDateTime> weekDays = dateTime.currentWeek();
        assertEquals("Size of the week should be 7", 7, weekDays.size());
        assertTrue("Current day should be in the set", weekDays.contains(dateTime));
        System.out.println("Current week starts in: " +  weekDays.stream().findFirst().orElse(null).getDay());
        assertTrue("Current week should start in 29", weekDays.stream().findFirst().orElse(null).getDay() == 29);
    }

    @Test
    public void testNextWeek() {
        CustomDateTime dateTime = new CustomDateTime(2024, 1, 30);
        Set<CustomDateTime> nextWeek = dateTime.nextWeek();
        assertEquals("Size of the next week should be 7", 7, nextWeek.size());
        System.out.println("Next week starts in: " + nextWeek.stream().findFirst().orElse(null).getDay());
        assertTrue("Next week should start in 5", nextWeek.stream().findFirst().orElse(null).getDay() == 5);
    }

    @Test
    public void testPreviousWeek() {
        CustomDateTime dateTime = new CustomDateTime(2024, 1, 30);
        Set<CustomDateTime> previousWeek = dateTime.previousWeek();
        assertEquals("Size of the previous week should be 7", 7, previousWeek.size());
        System.out.println("Previous week starts in: " +  previousWeek.stream().findFirst().orElse(null).getDay());
        assertTrue("Previous week should start in 22", previousWeek.stream().findFirst().orElse(null).getDay() == 22);
    }*/
}