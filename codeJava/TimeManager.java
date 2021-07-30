package codeJava;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class TimeManager {
    public static int convertTimeToSec(String time){
        return  Integer.parseInt(time.substring(6,8)) + (Integer.parseInt(time.substring(3,5)) * 60) + (Integer.parseInt(time.substring(0,2)) * 60 * 60) ;
    }

    public static String timeInstance(){
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new java.util.Date());
    }

    public static int timeInstance_Sec(){
        return convertTimeToSec(timeInstance());
    }

    public  static long timeInstance_UnixEpochTime_sec(){
        return Instant.now().getEpochSecond();
    }
}
