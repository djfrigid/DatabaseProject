package com.sparta.model.dbaccess;

import com.sparta.model.employee.Employee;
import com.sparta.model.util.PrintTimingData;

import java.util.HashSet;
import java.util.List;

import static com.sparta.model.util.Constants.EMPLOYEE_DAO;
import static com.sparta.model.util.Constants.LOGGER;

public class DBAccess {

    public static void readAll(){
        LOGGER.info("DB read started for all data");
        long startTime = System.nanoTime();
        List <Employee> listAllEmployees = EMPLOYEE_DAO.getAllEmployees();
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("DB read for all records done in: ", startTime, endTime);
    }

    public static void writeNonDuplicatesOnly(HashSet<Employee> toWrite) {
        LOGGER.info("Starting single threaded write for: " + toWrite.size() + " records");
        int count = 0;
        long startTime = System.nanoTime();
        for (Employee employee : toWrite) {
            EMPLOYEE_DAO.insertEmployee(employee);
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
            EMPLOYEE_DAO.insertEmployee(employee);
        }
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("DB write for unique entries done in: ", startTime, endTime);
        LOGGER.info("Starting single threaded duplicate writes");
        startTime = System.nanoTime();
        for (Employee employee : duplicatesAndBad){
            EMPLOYEE_DAO.insertEmployee(employee);
        }
        endTime = System.nanoTime();
        PrintTimingData.logTimingData("All duplicates written in: ", startTime, endTime);
    }

    public static void updateRecord(Employee em){
        LOGGER.info("Changing record at id= " + em.getId());
        long startTime = System.nanoTime();
        EMPLOYEE_DAO.updateEmployee(em);
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("The update was completed within: ", startTime, endTime);
    }


    public static void deleteRecord(int id){
        LOGGER.info("Deleting records for id: " + id);
        long startTime = System.nanoTime();
        EMPLOYEE_DAO.deleteEmployee(id);
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("The delete was completed within: ", startTime, endTime);


    }
}
