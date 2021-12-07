package com.sparta.util;

import static com.sparta.util.Constants.LOGGER;

public class PrintTimingData {
    public static void logTimingData(String message, long startTime, long endTime){
        if ((endTime-startTime) > 1000000){
            LOGGER.info(message + (endTime-startTime) + " ms");
        } else 
    }
}
