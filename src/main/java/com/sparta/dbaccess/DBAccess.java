package com.sparta.dbaccess;

import com.sparta.example.Employee;
import com.sparta.util.PrintTimingData;

import java.util.HashSet;
import java.util.List;

import static com.sparta.util.Constants.LOGGER;

public class DBAccess {

    public void readAll(){
        LOGGER.info("DB read started for all data");
        long startTime = System.nanoTime();
        EmployeeDaoImpl.readAll();
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("DB read for all records done in: ", startTime, endTime);
    }

    public void writeNonDuplicatesOnly(HashSet<Employee> toWrite){
        LOGGER.info("Starting single threaded write for: " + toWrite.size() + " records");
        long startTime = System.nanoTime();
        for (Employee employee : toWrite) {
            EmployeeDaoImpl.write(employee);
        }
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("DB write for all records done in: ", startTime, endTime);
    }

    public void writeAllData(HashSet<Employee> toWrite, List<Employee> duplicatesAndBad){
        LOGGER.info("Starting single threaded write for: " + toWrite.size() + " good records");
        long startTime = System.nanoTime();
        for (Employee employee : toWrite) {
            EmployeeDaoImpl.write(employee);
        }
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("DB write for unique entries done in: ", startTime, endTime);
        LOGGER.info("Starting single threaded duplicate writes");
        startTime = System.nanoTime();
        for (Employee employee : duplicatesAndBad){
            EmployeeDaoImpl.write(employee);
        }
        endTime = System.nanoTime();
        PrintTimingData.logTimingData("All duplicates written in: ", startTime, endTime);
    }

}

// TEMPORARY CLASS TO REMOVE COMPILER WARNINGS
class EmployeeDaoImpl{
    public static void write(Employee next){

    }

    public static void readAll(){

    }
}
