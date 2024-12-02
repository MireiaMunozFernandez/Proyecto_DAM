package com.example.proyecto_dam.Mapper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateMapper {
    public static Calendar Now() {
        return Calendar.getInstance();
    }
    public static long DateToLong(Calendar date) {
        return date.getTimeInMillis();
    }
    public static Calendar LongToDate(long date) {
        Calendar d = new GregorianCalendar();
        d.setTimeInMillis(date);
        return d;
    }
    public static String DateToString(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        String result = String.format("%02d", day) + "/" + String.format("%02d", month) + "/" + String.format("%04d", year);
        return result;
    }
    public static Calendar StringToDate(String date) {
        Calendar d;
        if(date == null) {
            d = Now();
        } else {
            String[] dList = date.split("/");
            if(dList.length == 3) {
                int year = Integer.valueOf(dList[2]);
                int month = Integer.valueOf(dList[1]);
                int day = Integer.valueOf(dList[0]);
                d = IntToDate(year, month, day);
            } else {
                d = Now();
            }
        }
        return d;
    }

    public static Calendar IntToDate(int year, int month, int day) {
                Calendar calendar = new GregorianCalendar();
                calendar.set(year, month - 1, day);
        return calendar;
    }

        public static int Compare(Calendar c1, Calendar c2) {
            if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
                return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
            if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH))
                return c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
            return c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
        }

}
