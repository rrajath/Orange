package com.rrajath.orange.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by rrajath on 4/1/15.
 */
public class DateUtils {

    public static String getTimeElapsed(long creationTime) {
        long currentTime = new Date().getTime();

        // API returns UNIX time which is in seconds and must be converted to milliseconds
        long duration = currentTime - (creationTime*1000);

        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
        long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);

        if (diffInDays != 0) {
            return "" + diffInDays + "d";
        } else if (diffInHours != 0) {
            return "" + diffInHours + "h";
        } else if (diffInMinutes != 0) {
            return "" + diffInMinutes + "m";
        } else {
            return "" + diffInSeconds + "s";
        }
    }
}
