package com.example.timemap.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

public class CustomDateTime implements Comparable<CustomDateTime>, Serializable {

    private Calendar calendar;

    // Constructor que inicializa la fecha y la hora
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

    public CustomDateTime(int year, int month, int day) {
        this(year, month, day, 0, 0, 0);
    }

    public CustomDateTime(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("El objeto Calendar no puede ser nulo.");
        }
        this.calendar = calendar;
    }

    /**
     * CustomDateTime of the millisecond provided as param
     * @param milliseconds the milliseconds since January 1, 1970
     */
    public CustomDateTime(long milliseconds) {
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(milliseconds);
    }

    /**
     * miliseconds since January 1, 1970
     * @return the miliseconds since January 1, 1970
     */
    public long getAsMilliseconds() {
        return calendar.getTimeInMillis();
    }

    /**
     * Get the CustomDateTime of this moment
     * @return the CustomDateTime of this moment
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
    public String getMonthName() {

        int month = this.getMonth();
        if (month >= 1 && month <= 12) {
            return MONTH_NAMES[month - 1];
        } else {
            throw new IllegalArgumentException("No válid!");
        }
    }

    public static final String[] DAY_NAMES = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
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

    public String getDayFullString(){
        return getWeekDayName() + " " + getDay() + "/" + getMonth() + "/" + getYear();
    }

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

    // Método toString para representación de cadena
    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d %02d:%02d:%02d",
                getYear(), getMonth(), getDay(), getHour(), getMinute(), getSecond());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CustomDateTime otherDateTime = (CustomDateTime) obj;
        return this.calendar.equals(otherDateTime.calendar);
    }

    public int hashCode() {
        return this.calendar.hashCode();
    }

    public boolean isAtSameDate(CustomDateTime otherDateTime) {
        return getYear() == otherDateTime.getYear() &&
                getMonth() == otherDateTime.getMonth() &&
                getDay() == otherDateTime.getDay();
    }

    public CustomDateTime firstDayOfWeek() {
        Calendar calendar = getCalendar();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        int daysToAdd = Calendar.MONDAY - dayOfWeek; // Calcula la cantidad de días para llegar al primer día de la semana (lunes)
        if (dayOfWeek == Calendar.MONDAY) {
            daysToAdd = 0; // Si ya es lunes, no es necesario agregar días
        } else if (dayOfWeek > Calendar.MONDAY) {
            daysToAdd += 7; // Si ya ha pasado el lunes, suma 7 días para obtener la próxima semana
        }

        Calendar temp = cloneCalendar(calendar);
        temp.add(Calendar.DAY_OF_MONTH, daysToAdd);

        return new CustomDateTime(temp);
    }

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

    public static Calendar cloneCalendar(Calendar original){
        Calendar buffer = Calendar.getInstance();
        buffer.setTimeInMillis(original.getTimeInMillis());
        return buffer;
    }

    public static Calendar nextDayCalendar(Calendar original){
        Calendar buffer = cloneCalendar(original);
        buffer.add(Calendar.DAY_OF_MONTH, 1);
        return buffer;
    }

    public static Calendar previousDayCalendar(Calendar original) {
        Calendar buffer = cloneCalendar(original);
        buffer.add(Calendar.DAY_OF_MONTH, -1);
        return buffer;
    }

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

    public CustomDateTime addDays(int days) {
        this.calendar.add(Calendar.DAY_OF_MONTH, days);
        return this;
    }

    public CustomDateTime addHours(int hours) {
        this.calendar.add(Calendar.HOUR_OF_DAY, hours);
        return this;
    }

    public CustomDateTime addMinutes(int minutes) {
        this.calendar.add(Calendar.MINUTE, minutes);
        return this;
    }

    public CustomDateTime addSeconds(int seconds) {
        this.calendar.add(Calendar.SECOND, seconds);
        return this;
    }

    public CustomDateTime addMilis(long millis) {
        calendar.setTimeInMillis(calendar.getTimeInMillis() + millis);
        return this;
    }

    public CustomDateTime subtractDays(int days) {
        this.calendar.add(Calendar.DAY_OF_MONTH, -days);
        return this;
    }

    public CustomDateTime subtractMillis(long millis) {
        calendar.setTimeInMillis(calendar.getTimeInMillis() - millis);
        return this;
    }

    public int getWeekOfYear() {
        // Obtener el número de semana
        return this.calendar.get(Calendar.WEEK_OF_YEAR);
    }

    @Override
    public int compareTo(CustomDateTime otherDateTime) {
        return this.calendar.compareTo(otherDateTime.calendar);
    }
}