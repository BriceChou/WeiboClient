package com.bricechou.weiboclient.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author BriceChou
 * @datetime 16-6-13 17:00
 */

public class TimeFormat {
    /**
     *
     * @author BriceChou
     * @datetime 16-6-13 17:52
     * @TODO compare the current time and weibo created time
     *  if current time day before the weibo created time day,only show the weibo created time day
     *  else only show the created time with second minutes hours
     */

    public static String timeToString (String time){
        // Fri Aug 28 00:00:00 +0800 2009
        String[] dateArray = time.split(" ");
       /* Date localDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.format(localDate);*/
        String dateTime = "";
        for (int i = 1; i < dateArray.length-3; i++) {
            dateTime = dateTime + dateArray[i] + " ";
            if (i == dateArray.length - 4){
                dateTime = dateTime + "-";
            }
        }
        dateTime = dateTime + dateArray[3];
        return dateTime;
    }
}
