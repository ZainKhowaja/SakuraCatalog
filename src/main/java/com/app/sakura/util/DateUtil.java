package com.app.sakura.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String getCurrentDateForDbBackup(){
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

}
