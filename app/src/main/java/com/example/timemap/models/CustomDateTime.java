package com.example.timemap.models;

import java.util.Calendar;

public class CustomDateTime implements Comparable<CustomDateTime> {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    // Constructor que inicializa la fecha y la hora
    public CustomDateTime(int year, int month, int day, int hour, int minute, int second) {
        if (isValidDate(year, month, day) && isValidTime(hour, minute, second)) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        } else {
            throw new IllegalArgumentException("Fecha u hora no válida.");
        }
    }

    public CustomDateTime(int year, int month, int day) {
        if (isValidDate(year, month, day) && isValidTime(hour, minute, second)) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = 0;
            this.minute = 0;
            this.second = 0;
        } else {
            throw new IllegalArgumentException("Fecha u hora no válida.");
        }
    }

    public CustomDateTime(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("El objeto Calendar no puede ser nulo.");
        }

        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH es 0-based
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
        this.second = calendar.get(Calendar.SECOND);
    }

    public CustomDateTime(long totalSeconds) {
        if (totalSeconds < 0) {
            throw new IllegalArgumentException("El número de segundos debe ser no negativo.");
        }

        int daysInYear = isLeapYear(this.year) ? 366 : 365;

        // Calcular años
        this.year = 1970 + (int) (totalSeconds / (daysInYear * 24 * 3600));

        // Restar los segundos correspondientes a los años calculados
        totalSeconds -= (long) (this.year - 1970) * daysInYear * 24 * 3600;

        // Calcular días del año actual
        int daysInMonth = 0;
        int month = 1;
        while (totalSeconds >= daysInMonth(month, this.year) * 24 * 3600) {
            totalSeconds -= (long) daysInMonth(month, this.year) * 24 * 3600;
            month++;
        }
        this.month = month;

        this.day = (int) (totalSeconds / (24 * 3600)) + 1;
        totalSeconds -= (long) (this.day - 1) * 24 * 3600;

        this.hour = (int) (totalSeconds / 3600);
        totalSeconds -= (long) this.hour * 3600;

        this.minute = (int) (totalSeconds / 60);
        this.second = (int) (totalSeconds % 60);
    }

    public static CustomDateTime now() {
        return new CustomDateTime(Calendar.getInstance());
    }

    public static CustomDateTime today() {
        Calendar calendar = Calendar.getInstance();
        // Establecer la hora, minutos y segundos a cero
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new CustomDateTime(calendar);
    }

    public int daysInMonth(int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Mes no válido.");
        }

        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        }

        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return daysInMonth[month];
    }

    // Métodos para obtener los componentes de la fecha
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getMonthName() {
        String[] monthNames = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        if (month >= 1 && month <= 12) {
            return monthNames[month - 1];
        } else {
            throw new IllegalArgumentException("Mes no válido");
        }
    }

    public int getDay() {
        return day;
    }

    // Métodos para obtener los componentes de la hora
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    // Método toString para representación de cadena
    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
    }

    // Método para validar la fecha
    public boolean isValidDate(int year, int month, int day) {
        if (year < 1 || month < 1 || month > 12 || day < 1 || day > maxDaysInMonth(year, month)) {
            return false;
        }
        return true;
    }

    public Calendar getAsCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.year);
        calendar.set(Calendar.MONTH, this.month - 1); // Calendar.MONTH es 0-based
        calendar.set(Calendar.DAY_OF_MONTH, this.day);
        calendar.set(Calendar.HOUR_OF_DAY, this.hour);
        calendar.set(Calendar.MINUTE, this.minute);
        calendar.set(Calendar.SECOND, this.second);
        return calendar;
    }

    // Método para validar la hora
    public boolean isValidTime(int hour, int minute, int second) {
        return hour >= 0 && hour < 24 && minute >= 0 && minute < 60 && second >= 0 && second < 60;
    }

    // Método para obtener el número máximo de días en un mes dado un año
    public int maxDaysInMonth(int year, int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Mes no válido.");
        }

        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        }

        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return daysInMonth[month];
    }

    // Método para verificar si un año es bisiesto
    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CustomDateTime otherDateTime = (CustomDateTime) obj;
        return year == otherDateTime.year &&
                month == otherDateTime.month &&
                day == otherDateTime.day &&
                hour == otherDateTime.hour &&
                minute == otherDateTime.minute &&
                second == otherDateTime.second;
    }

    public int hashCode() {
        int result = 17; // Valor inicial recomendado para evitar colisiones

        result = 31 * result + year;
        result = 31 * result + month;
        result = 31 * result + day;
        result = 31 * result + hour;
        result = 31 * result + minute;
        result = 31 * result + second;

        return result;
    }

    public boolean sameDate(CustomDateTime otherDateTime) {
        return this.year == otherDateTime.year &&
                this.month == otherDateTime.month &&
                this.day == otherDateTime.day;
    }

    // Método para calcular la diferencia de tiempo en formato HH:mm:ss
    public String timeRemaining(CustomDateTime otherDateTime) {
        long totalSeconds = getTimeInSeconds(otherDateTime) - getTimeInSeconds(this);

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

    // Método para convertir DateTime a segundos teniendo en cuenta los años bisiestos
    private long getTimeInSeconds(CustomDateTime dateTime) {
        long totalSeconds = 0;

        for (int year = 1970; year < dateTime.year; year++) {
            totalSeconds += isLeapYear(year) ? 366 * 24 * 3600 : 365 * 24 * 3600;
        }

        totalSeconds += getSecondsInYear(dateTime);

        totalSeconds += dateTime.hour * 3600 + dateTime.minute * 60 + dateTime.second;

        return totalSeconds;
    }

    // Método auxiliar para obtener la cantidad de segundos en el año específico
    private long getSecondsInYear(CustomDateTime dateTime) {
        long totalSeconds = 0;

        for (int month = 1; month < dateTime.month; month++) {
            totalSeconds += daysInMonth(month, dateTime.year) * 24 * 3600;
        }

        totalSeconds += (dateTime.day - 1) * 24 * 3600;

        return totalSeconds;
    }

    public CustomDateTime firstDayOfWeek() {
        Calendar calendar = getAsCalendar();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        int daysToAdd = Calendar.MONDAY - dayOfWeek; // Calcula la cantidad de días para llegar al primer día de la semana (lunes)
        if (dayOfWeek == Calendar.MONDAY) {
            daysToAdd = 0; // Si ya es lunes, no es necesario agregar días
        } else if (dayOfWeek > Calendar.MONDAY) {
            daysToAdd += 7; // Si ya ha pasado el lunes, suma 7 días para obtener la próxima semana
        }

        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

        return new CustomDateTime(calendar);
    }

    public CustomDateTime[] currentWeek() {
        CustomDateTime[] weekDaysArray = new CustomDateTime[7];
        CustomDateTime firstDayOfWeek = firstDayOfWeek();
        Calendar calendar = firstDayOfWeek.getAsCalendar();
        for (int i = 0; i < 7; i++) {
            weekDaysArray[i] = new CustomDateTime(calendar);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return weekDaysArray;
    }

    public CustomDateTime[] nextWeek() {
        CustomDateTime[] nextWeekArray = new CustomDateTime[7];
        CustomDateTime firstDayOfWeek = firstDayOfWeek(); // Obtener el primer día de la semana actual
        Calendar calendar = firstDayOfWeek.getAsCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 7); // Avanzar una semana

        for (int i = 0; i < 7; i++) {
            nextWeekArray[i] = new CustomDateTime(calendar);
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Avanzar al siguiente día
        }

        return nextWeekArray;
    }

    public CustomDateTime[] previousWeek() {
        CustomDateTime[] previousWeekArray = new CustomDateTime[7];
        CustomDateTime firstDayOfWeek = firstDayOfWeek(); // Obtener el primer día de la semana actual
        Calendar calendar = firstDayOfWeek.getAsCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -7); // Retroceder una semana

        for (int i = 0; i < 7; i++) {
            previousWeekArray[i] = new CustomDateTime(calendar);
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Avanzar al siguiente día
        }

        return previousWeekArray;
    }

    public CustomDateTime addDays(int days) {
        if (days == 0) {
            return this; // No es necesario realizar cambios
        }

        int sign = (days > 0) ? 1 : -1;

        while (days != 0) {
            int maxDaysInMonth = maxDaysInMonth(this.year, this.month);
            int daysToAdd = Math.min(Math.abs(days), maxDaysInMonth - this.day + 1);

            this.day += sign * daysToAdd;
            days -= sign * daysToAdd;

            if (days != 0) {
                // Si todavía hay días por agregar, avanzar al siguiente mes
                this.month += sign;
                if (this.month > 12) {
                    this.month = 1;
                    this.year += sign;
                } else if (this.month < 1) {
                    this.month = 12;
                    this.year -= sign;
                }
            }
        }

        return this;
    }

    public int weekOfYear() {
        // Obtener el número de semana
        return getAsCalendar().get(Calendar.WEEK_OF_YEAR);
    }
    

    public void subtractDays(int days) {
        addDays(-days);
    }

    @Override
    public int compareTo(CustomDateTime otherDateTime) {

        int yearComparison = Integer.compare(this.year, otherDateTime.year);
        if (yearComparison != 0) return yearComparison;

        int monthComparison = Integer.compare(this.month, otherDateTime.month);
        if (monthComparison != 0) return monthComparison;

        int dayComparison = Integer.compare(this.day, otherDateTime.day);
        if (dayComparison != 0) return dayComparison;

        int hourComparison = Integer.compare(this.hour, otherDateTime.hour);
        if (hourComparison != 0) return hourComparison;

        int minuteComparison = Integer.compare(this.minute, otherDateTime.minute);
        if (minuteComparison != 0) return minuteComparison;

        return Integer.compare(this.second, otherDateTime.second);
    }
}
