package com.example.timemap.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

/**
 * CustomDateTime represents a date and time with various utility methods.
 */
public class CustomDateTime implements Comparable<CustomDateTime>, Serializable {

    private Calendar calendar;


    /**
     * Constructs a CustomDateTime object with the provided year, month, day, hour, minute, and second.
     *
     * @param year    The year.
     * @param month   The month (1-12).
     * @param day     The day of the month.
     * @param hour    The hour of the day (0-23).
     * @param minute  The minute of the hour (0-59).
     * @param second  The second of the minute (0-59).
     * @throws IllegalArgumentException if the provided date or time is invalid.
     */
    public CustomDateTime(int year, int month, int day, int hour, int minute, int second) {
        this.calendar = Calendar.getInstance();
        this.calendar.setLenient(false); // Hace que la validación sea estricta

        this.calendar.set(year, month - 1, day, hour, minute, second);

        try {
            this.calendar.getTime(); // Intenta obtener la fecha, lanzará una excepción si es inválida
        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha u hora no válida.", e);
        }
    }

    /**
     * Constructs a CustomDateTime object with the provided year, month, and day, setting time to midnight (00:00:00).
     *
     * @param year  The year.
     * @param month The month (1-12).
     * @param day   The day of the month.
     */
    public CustomDateTime(int year, int month, int day) {
        this(year, month, day, 0, 0, 0);
    }

    /**
     * Constructs a CustomDateTime object from a Calendar object.
     *
     * @param calendar The Calendar object.
     * @throws IllegalArgumentException if the provided Calendar object is null.
     */
    public CustomDateTime(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("El objeto Calendar no puede ser nulo.");
        }
        this.calendar = calendar;
    }

    /**
     * Constructs a CustomDateTime object from the milliseconds since January 1, 1970.
     *
     * @param milliseconds The milliseconds since January 1, 1970.
     */
    public CustomDateTime(long milliseconds) {
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(milliseconds);
    }

    /**
     * Gets the milliseconds since January 1, 1970.
     *
     * @return The milliseconds since January 1, 1970.
     */
    public long getAsMilliseconds() {
        return calendar.getTimeInMillis();
    }

    /**
     * Gets the current CustomDateTime.
     *
     * @return The CustomDateTime of this moment.
     */
    public static CustomDateTime now() {
        return new CustomDateTime(Calendar.getInstance());
    }



    public int getYear() {
        return this.calendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return this.calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH es 0-based
    }


    public static final String[] MONTH_NAMES = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    /**
     * Gets the name of the month.
     *
     * @return The name of the month.
     * @throws IllegalArgumentException if the month is not within the valid range.
     */
    public String getMonthName() {

        int month = this.getMonth();
        if (month >= 1 && month <= 12) {
            return MONTH_NAMES[month - 1];
        } else {
            throw new IllegalArgumentException("No válid!");
        }
    }

    public static final String[] DAY_NAMES = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    /**
     * Gets the name of the day of the week.
     *
     * @return The name of the day of the week.
     * @throws IllegalArgumentException if the day is not within the valid range.
     */
    public String getWeekDayName() {
        int day = Calendar.DAY_OF_WEEK;
        if (day >= 1 && day <= 7) {
            return DAY_NAMES[day- 1];
        } else {
            throw new IllegalArgumentException("No válid!");
        }
    }


    public int getDay() {
        return this.calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getHour() {
        return this.calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return this.calendar.get(Calendar.MINUTE);
    }

    public int getSecond() {
        return this.calendar.get(Calendar.SECOND);
    }

    public Calendar getCalendar() {
        return (Calendar) this.calendar.clone();
    }

    /**
     * Gets a formatted string representing the full date, including the day of the week.
     *
     * @return The formatted string (e.g., "Monday 01/02/2023").
     */
    public String getDayFullString(){
        return getWeekDayName() + " " + getDay() + "/" + getMonth() + "/" + getYear();
    }

    /**
     * Calculates the time remaining between this CustomDateTime and another CustomDateTime.
     *
     * @param otherDateTime The CustomDateTime to compare.
     * @return A formatted string indicating the time difference, such as "02d 03h 15m" or "-01h 30m 20s".
     */
    public String getTimeRemaining(CustomDateTime otherDateTime) {
        long totalSeconds = otherDateTime.calendar.getTimeInMillis() / 1000 - this.calendar.getTimeInMillis() / 1000;

        long sign = (totalSeconds < 0) ? -1 : 1;
        totalSeconds = Math.abs(totalSeconds);

        long days = totalSeconds / (24 * 3600);
        long remainingSeconds = totalSeconds % (24 * 3600);
        long hours = remainingSeconds / 3600;
        long minutes = (remainingSeconds % 3600) / 60;
        long seconds = remainingSeconds % 60;

        if (days > 0) {
            return String.format("%s%02dd %02dh %02dm", (sign > 0) ? "-" : " ", days, hours, minutes);
        } else {
            return String.format("%s%02dh %02dm %02ds", (sign > 0) ? "-" : " ", hours, minutes, seconds);
        }
    }


    /**
     * Overrides the toString method for string representation of CustomDateTime.
     *
     * @return A formatted string representation of CustomDateTime.
     */
    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d %02d:%02d:%02d",
                getYear(), getMonth(), getDay(), getHour(), getMinute(), getSecond());
    }

    /**
     * Overrides the equals method to compare this CustomDateTime with another object.
     *
     * @param obj The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CustomDateTime otherDateTime = (CustomDateTime) obj;
        return this.calendar.equals(otherDateTime.calendar);
    }

    /**
     * Calculates the hash code for this CustomDateTime based on its internal Calendar object.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return this.calendar.hashCode();
    }

    /**
     * Checks if this CustomDateTime is on the same date as another CustomDateTime.
     *
     * @param otherDateTime The CustomDateTime to compare.
     * @return true if both objects represent the same date, false otherwise.
     */
    public boolean isAtSameDate(CustomDateTime otherDateTime) {
        return getYear() == otherDateTime.getYear() &&
                getMonth() == otherDateTime.getMonth() &&
                getDay() == otherDateTime.getDay();
    }

    /**
     * Gets the first day of the week for this CustomDateTime.
     *
     * @return A new CustomDateTime representing the first day of the week.
     */
    public CustomDateTime firstDayOfWeek() {
        Calendar calendar = getCalendar();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        int daysToAdd = Calendar.SUNDAY - dayOfWeek + 1; // Calculate the number of days to reach the first day of the week (Sunday)
        if (dayOfWeek == Calendar.SUNDAY) {
            daysToAdd = 0; // If it's already Sunday, no need to add days
        }

        Calendar temp = cloneCalendar(calendar);
        temp.add(Calendar.DAY_OF_MONTH, daysToAdd);

        return new CustomDateTime(temp);
    }

    /**
     * Gets a set of CustomDateTime objects representing the current week.
     *
     * @return The set of CustomDateTime objects for the current week.
     */
    public Set<CustomDateTime> currentWeek() {
        Set<CustomDateTime> weekDaysArray = new TreeSet<>();
        CustomDateTime firstDayOfWeek = firstDayOfWeek();
        Calendar calendar = cloneCalendar(firstDayOfWeek.getCalendar());
        for (int i = 0; i < 7; i++) {
            weekDaysArray.add(new CustomDateTime(calendar));
            calendar = nextDayCalendar(calendar);
        }
        return weekDaysArray;
    }

    /**
     * Clones a Calendar object.
     *
     * @param original The original Calendar object to clone.
     * @return The cloned Calendar object.
     */
    public static Calendar cloneCalendar(Calendar original){
        Calendar buffer = Calendar.getInstance();
        buffer.setTimeInMillis(original.getTimeInMillis());
        return buffer;
    }

    /**
     * Gets the Calendar object for the next day.
     *
     * @param original The original Calendar object.
     * @return The Calendar object for the next day.
     */
    public static Calendar nextDayCalendar(Calendar original){
        Calendar buffer = cloneCalendar(original);
        buffer.add(Calendar.DAY_OF_MONTH, 1);
        return buffer;
    }

    /**
     * Gets the Calendar object for the previous day.
     *
     * @param original The original Calendar object.
     * @return The Calendar object for the previous day.
     */
    public static Calendar previousDayCalendar(Calendar original) {
        Calendar buffer = cloneCalendar(original);
        buffer.add(Calendar.DAY_OF_MONTH, -1);
        return buffer;
    }

    /**
     * Gets a set of CustomDateTime objects representing the next week.
     *
     * @return The set of CustomDateTime objects for the next week.
     */
    public Set<CustomDateTime> nextWeek() {
        Set<CustomDateTime> nextWeekArray = new TreeSet<>();
        CustomDateTime firstDayOfWeek = firstDayOfWeek(); // Obtener el primer día de la semana actual
        Calendar calendar = cloneCalendar(firstDayOfWeek.getCalendar());
        calendar.add(Calendar.DAY_OF_MONTH, 7); // Avanzar una semana
        for (int i = 0; i < 7; i++) {
            nextWeekArray.add(new CustomDateTime(calendar));
            calendar = nextDayCalendar(calendar);
        }

        return nextWeekArray;
    }

    /**
     * Gets a set of CustomDateTime objects representing the previous week.
     *
     * @return The set of CustomDateTime objects for the previous week.
     */
    public Set<CustomDateTime> previousWeek() {
        TreeSet<CustomDateTime> previousWeekArray = new TreeSet<>();
        CustomDateTime firstDayOfWeek = firstDayOfWeek(); // Obtener el primer día de la semana actual
        Calendar calendar = cloneCalendar(firstDayOfWeek.getCalendar());
        calendar.add(Calendar.DAY_OF_MONTH, -7); // Retroceder una semana
        for (int i = 0; i < 7; i++) {
            previousWeekArray.add(new CustomDateTime(calendar));
            calendar = nextDayCalendar(calendar); // Avanzar al siguiente día
        }
        return previousWeekArray;
    }

    /**
     * Adds the specified number of days to this CustomDateTime.
     *
     * @param days The number of days to add.
     * @return The updated CustomDateTime.
     */
    public CustomDateTime addDays(int days) {
        this.calendar.add(Calendar.DAY_OF_MONTH, days);
        return this;
    }

    /**
     * Adds the specified number of hours to this CustomDateTime.
     *
     * @param hours The number of hours to add.
     * @return The updated CustomDateTime.
     */
    public CustomDateTime addHours(int hours) {
        this.calendar.add(Calendar.HOUR_OF_DAY, hours);
        return this;
    }

    /**
     * Adds the specified number of minutes to this CustomDateTime.
     *
     * @param minutes The number of minutes to add.
     * @return The updated CustomDateTime.
     */
    public CustomDateTime addMinutes(int minutes) {
        this.calendar.add(Calendar.MINUTE, minutes);
        return this;
    }

    /**
     * Adds the specified number of seconds to this CustomDateTime.
     *
     * @param seconds The number of seconds to add.
     * @return The updated CustomDateTime.
     */
    public CustomDateTime addSeconds(int seconds) {
        this.calendar.add(Calendar.SECOND, seconds);
        return this;
    }

    /**
     * Subtracts the specified number of days from this CustomDateTime.
     *
     * @param days The number of days to subtract.
     * @return The updated CustomDateTime.
     */
    public CustomDateTime subtractDays(int days) {
        this.calendar.add(Calendar.DAY_OF_MONTH, -days);
        return this;
    }

    /**
     * Gets the week of the year for this CustomDateTime.
     *
     * @return The week of the year.
     */
    public int getWeekOfYear() {
        // Obtener el número de semana
        return this.calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Compares this CustomDateTime with another CustomDateTime for ordering.
     *
     * @param otherDateTime The CustomDateTime to compare.
     * @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(CustomDateTime otherDateTime) {
        return this.calendar.compareTo(otherDateTime.calendar);
    }
}