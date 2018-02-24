package com.example.masato.githubfeed.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.example.masato.githubfeed.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Masato on 2018/01/27.
 */

public class DateUtil {

    private static final DateFormat dateFormat  = new SimpleDateFormat("yyyy/MM/dd");

    public static String getReadableDateForFeed(Date date, Context context) {
        if (date == null) {
            return "";
        }
        final long now = System.currentTimeMillis() - TimeZone.getDefault().getRawOffset();
        final long fiveDaysInMillis = 5 * 24 * 60 * 60 * 1000;
        long diff = now - date.getTime();
        if (diff < fiveDaysInMillis) {
            return getTimeElapsed(diff, context);
        } else {
            return dateFormat.format(date);
        }
    }

    private static String getTimeElapsed(long time, Context context) {
        if (time < 0) {
            Log.i("gh_feed", Long.toString(time));
            return "";
        }
        Resources resources = context.getResources();
        final long aMinuteInMillis = 60 * 1000;
        final long anHourInMillis = 60 * aMinuteInMillis;
        final long aDayInMillis = 24 * anHourInMillis;
        if (aDayInMillis <= time) {
            int days = (int) (time / aDayInMillis);
            return Integer.toString(days) + resources.getString(R.string.days_ago);
        } else if (anHourInMillis <= time && time < aDayInMillis) {
            int hours = (int) (time / anHourInMillis);
            return Integer.toString(hours) + resources.getString(R.string.hours_ago);
        } else if (aMinuteInMillis <= time && time < anHourInMillis) {
            int minutes = (int) (time / aMinuteInMillis);
            return Integer.toString(minutes) + resources.getString(R.string.minutes_ago);
        } else {
            int seconds = (int) (time / 1000);
            return Integer.toString(seconds) + resources.getString(R.string.seconds_ago);
        }
    }
}
