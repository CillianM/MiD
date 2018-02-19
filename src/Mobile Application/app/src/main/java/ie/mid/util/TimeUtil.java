package ie.mid.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cillian on 13/12/2017.
 */

public class TimeUtil {

    public static String getTimeSince(String createdAt) {
        try {
            TimeUnit timeUnit;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(createdAt);
            Date now = new Date();
            long diff = now.getTime() - parsedDate.getTime();
            if(diff <= 3600000){ //minutes
                return TimeUnit.MILLISECONDS.toMinutes(diff) + " mins";
            } else if(diff <= 86400000){ //hours
                return TimeUnit.MILLISECONDS.toHours(diff) + " hrs";
            } else{ //anything else is days
                return TimeUnit.MILLISECONDS.toDays(diff) + " days";
            }
        } catch(Exception e) {
            return "";
        }
    }

}
