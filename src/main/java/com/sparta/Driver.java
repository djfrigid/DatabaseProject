package com.sparta;

import com.sparta.employee.Employee;
import com.sparta.fileIO.FileIO;

import java.util.Collection;
import java.util.List;

import static com.sparta.util.Constants.LOGGER;

public class Driver {
    public static void main(String[] args) {
        List<Collection<Employee>> validAndDuplicateCollections = FileIO.performMultithreadedRead();
        LOGGER.info("Number of unique records: " + validAndDuplicateCollections.get(0).size());
        LOGGER.info("Number of duplicate records: " + validAndDuplicateCollections.get(1).size());
    }
}
