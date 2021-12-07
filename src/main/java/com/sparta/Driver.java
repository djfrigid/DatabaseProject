package com.sparta;

import com.sparta.example.Employee;
import com.sparta.fileIO.FileIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;

public class Driver {
    public static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) {
        List<Collection<Employee>> validAndDuplicateCollections = FileIO.performMultithreadedRead();
        LOGGER.info("Number of unique records: " + validAndDuplicateCollections.get(0).size());
        LOGGER.info("Number of duplicate records: " + validAndDuplicateCollections.get(1).size());
    }
}
