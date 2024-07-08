package com.cdac.iaf.bookmycoolie.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeConversionUtil {

    public static String convertTime(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(time);
    }

    public static String convertTime(Calendar time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(time.getTime());
    }

    public static String getFullDate(String date){
        try {
            // Create a SimpleDateFormat object for parsing the input date
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            // Parse the input date string into a Date object
            Date date2 = inputFormat.parse(date);

            // Create a SimpleDateFormat object for formatting the date
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
            // Format the Date object into the desired string format
            return outputFormat.format(date2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFullDate2(String date){
        try {
            // Create a SimpleDateFormat object for parsing the input date
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            // Parse the input date string into a Date object
            Date date2 = inputFormat.parse(date);

            // Create a SimpleDateFormat object for formatting the date
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("dd MM yyyy");
            // Format the Date object into the desired string format
            return outputFormat.format(date2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFullDateIndia(String startDate, String endDate){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());
        try {
            Date sDate = inputFormat.parse(startDate);
            Date eDate = inputFormat.parse(endDate);
            SimpleDateFormat outputFormat1 = new SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault());
            SimpleDateFormat outputFormat2 = new SimpleDateFormat("hh:mm a");
            outputFormat1.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // Set timezone to India Standard Time
            return outputFormat1.format(sDate) +" to "+ outputFormat2.format(eDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
