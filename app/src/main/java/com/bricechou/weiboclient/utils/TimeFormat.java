package com.bricechou.weiboclient.utils;

/**
 * To format the home page weibo content time.
 *
 * @author BriceChou
 * @datetime 16-6-13 17:00
 */
public class TimeFormat {

    /**
     * if current time day before the weibo created time day,only show the weibo created time day
     * else only show the created time with second minutes hours
     *
     * @author BriceChou
     * @datetime 16-6-13 17:52
     * @TODO compare the current time and weibo created time
     */
    public static String timeToString(String time) {
        String[] dateArray = time.split(" ");
        String dateTime = "";
        for (int i = 1; i < dateArray.length - 3; i++) {
            dateTime = dateTime + dateArray[i] + " ";
            if (i == dateArray.length - 4) {
                dateTime = dateTime + "-";
            }
        }
        dateTime = dateTime + dateArray[3];
        return dateTime;
    }
}
