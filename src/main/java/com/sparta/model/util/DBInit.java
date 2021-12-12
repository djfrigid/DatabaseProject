package com.sparta.model.util;

import java.io.*;
import java.util.Map;

public class DBInit {
    public static void initDB(String dbUser, String dbPass){
        ProcessBuilder processBuilder = new ProcessBuilder();
        if(System.getProperty("os.name").contains("Windows")){
            Map<String, String> environmentVariables  = processBuilder.environment();
            environmentVariables.put("DBUSER", dbUser);
            environmentVariables.put("DBPASS", dbPass);
            processBuilder.command("cmd", "/c", "start", "initDB.bat").directory(new File(System.getProperty("user.dir")));
        }else {
            Map<String, String> environmentVariables  = processBuilder.environment();
            environmentVariables.put("DBUSER", dbUser);
            environmentVariables.put("DBPASS", dbPass);
            processBuilder.command("./initDB.sh").directory(new File(System.getProperty("user.dir")));
        }
        try {
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
