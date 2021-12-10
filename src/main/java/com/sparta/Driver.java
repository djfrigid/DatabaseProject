package com.sparta;

import com.sparta.dbaccess.MultithreadedDBWrites;
import com.sparta.employee.Employee;
import com.sparta.fileIO.FileIO;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.sparta.util.Constants.LOGGER;

public class Driver {
    public static void main(String[] args) {
        File file =new File("exampleLogfile.log");
        file.delete();
        List<Collection<Employee>> validAndDuplicateCollections = FileIO.performMultithreadedRead();
        LOGGER.info("Number of unique records: " + validAndDuplicateCollections.get(0).size());
        LOGGER.info("Number of duplicate records: " + validAndDuplicateCollections.get(1).size());
        MultithreadedDBWrites.writeNonDuplicatesOnly((Set<Employee>) validAndDuplicateCollections.get(0));
        LOGGER.info("Exiting Driver Now");
    }
}
