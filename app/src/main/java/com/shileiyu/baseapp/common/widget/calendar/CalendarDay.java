package com.shileiyu.baseapp.common.widget.calendar;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

/**
 * @author shilei.yu
 * @since on 2018/3/29.
 */

public class CalendarDay {
    private transient Calendar calendar;
    private transient Date date;

    private final int year;
    private final int month;
    private final int day;

    public CalendarDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public boolean isBefore(@NonNull CalendarDay other) {
        if (year == other.year) {
            return ((month == other.month) ? (this.day < other.day) : (month < other.month));
        }
        return year < other.year;
    }

    public boolean isAfter(@NonNull CalendarDay other) {
        if (year == other.year) {
            return (month == other.month) ? (day > other.day) : (month > other.month);
        } else {
            return year > other.year;
        }
    }

    public boolean isInRange(@Nullable CalendarDay minDate, @Nullable CalendarDay maxDate) {
        return !(minDate != null && minDate.isAfter(this)) && !(maxDate != null && maxDate.isBefore(this));
    }

    public static CalendarDay from(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new CalendarDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof CalendarDay) {
            CalendarDay m = (CalendarDay) obj;
            return year == m.year && month == m.month && day == m.day;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "{CalendarDay year=" + year + ",month=" + month + ",day=" + day + "}";
    }

    @NonNull
    public Calendar getCalendar() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(year, month, day);
        }
        return calendar;
    }

    public Date getDate() {
        return getCalendar().getTime();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public static void main(String[] a) {
        CalendarDay from = CalendarDay.from(new Date());
        System.out.println(from.toString());
    }
}
