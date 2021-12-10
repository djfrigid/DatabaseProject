package com.sparta.model.util;

import static com.sparta.model.util.Constants.LOGGER;

public class PrintTimingData {
    public static void logTimingData(String message, long startTime, long endTime){
        if ((endTime-startTime) > 1000000){
            LOGGER.info(message + (endTime-startTime)/1000000 + " ms");
        } else LOGGER.info(message + (endTime-startTime) + " ns");
    }
}
