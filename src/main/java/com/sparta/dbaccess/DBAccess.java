package com.sparta.dbaccess;

import com.sparta.employee.Employee;
import com.sparta.util.PrintTimingData;

import java.util.HashSet;
import java.util.List;

import static com.sparta.util.Constants.LOGGER;
import static com.sparta.util.Constants.EMPLOYEE_DEO;

public class DBAccess {

    public static void readAll(){
        LOGGER.info("DB read started for all data");
        long startTime = System.nanoTime();
        List <Employee> listAllEmployees = EMPLOYEE_DEO.getAllEmployees();
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("DB read for all records done in: ", startTime, endTime);
    }

    public static void writeNonDuplicatesOnly(HashSet<Employee> toWrite) {
        LOGGER.info("Starting single threaded write for: " + toWrite.size() + " records");
        int count = 0;
        long startTime = System.nanoTime();
        for (Employee employee : toWrite) {
            EMPLOYEE_DEO.insertEmployee(employee);
            count++;
            if (count % 100 == 0){
                LOGGER.info("Records inserted so far: " + count);
            }
        }
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("DB write for all records done in: ", startTime, endTime);
    }

    public static void writeAllData(HashSet<Employee> toWrite, List<Employee> duplicatesAndBad){
        LOGGER.info("Starting single threaded write for: " + toWrite.size() + " good records");
        long startTime = System.nanoTime();
        for (Employee employee : toWrite) {
            EMPLOYEE_DEO.insertEmployee(employee);
        }
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("DB write for unique entries done in: ", startTime, endTime);
        LOGGER.info("Starting single threaded duplicate writes");
        startTime = System.nanoTime();
        for (Employee employee : duplicatesAndBad){
            EMPLOYEE_DEO.insertEmployee(employee);
        }
        endTime = System.nanoTime();
        PrintTimingData.logTimingData("All duplicates written in: ", startTime, endTime);
    }

    public static void updateRecord(Employee em){
        LOGGER.info("Changing record at id= " + em.getId());
        long startTime = System.nanoTime();
        EMPLOYEE_DEO.updateEmployee(em);
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("The update was completed within: ", startTime, endTime);
    }


    public static void deleteRecord(int id){
        LOGGER.info("Deleting records for id: " + id);
        long startTime = System.nanoTime();
        EMPLOYEE_DEO.deleteEmployee(id);
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("The delete was completed within: ", startTime, endTime);


    }
}
