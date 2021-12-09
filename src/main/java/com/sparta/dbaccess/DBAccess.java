package com.sparta.dbaccess;

import com.sparta.employee.Employee;
import com.sparta.util.PrintTimingData;

import java.util.HashSet;
import java.util.List;

import static com.sparta.util.Constants.LOGGER;

public class DBAccess {

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    public void readAll(){
        LOGGER.info("DB read started for all data");
        long startTime = System.nanoTime();
        List <Employee> listAllEmployees = employeeDao.getAllEmployees();
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("DB read for all records done in: ", startTime, endTime);
    }

    public void writeNonDuplicatesOnly(HashSet<Employee> toWrite) {
        LOGGER.info("Starting single threaded write for: " + toWrite.size() + " records");
        long startTime = System.nanoTime();
        for (Employee employee : toWrite) {
            employeeDao.insertEmployee(employee);
        }
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("DB write for all records done in: ", startTime, endTime);
    }

    public void writeAllData(HashSet<Employee> toWrite, List<Employee> duplicatesAndBad){
        LOGGER.info("Starting single threaded write for: " + toWrite.size() + " good records");
        long startTime = System.nanoTime();
        for (Employee employee : toWrite) {
            employeeDao.insertEmployee(employee);
        }
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("DB write for unique entries done in: ", startTime, endTime);
        LOGGER.info("Starting single threaded duplicate writes");
        startTime = System.nanoTime();
        for (Employee employee : duplicatesAndBad){
            employeeDao.insertEmployee(employee);
        }
        endTime = System.nanoTime();
        PrintTimingData.logTimingData("All duplicates written in: ", startTime, endTime);
    }

    public void updateRecord(Employee em){
        LOGGER.info("Changing record at id= " + em.getId());
        long startTime = System.nanoTime();
        employeeDao.updateEmployee(em);
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("The update was completed within: ", startTime, endTime);
    }


    public void deleteRecord(int id){
        LOGGER.info("Deleting records for id: " + id);
        long startTime = System.nanoTime();
        employeeDao.deleteEmployee(id);
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("The delete was completed within: ", startTime, endTime);


    }
}
