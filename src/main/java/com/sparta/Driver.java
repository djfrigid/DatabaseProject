package com.sparta;

import com.sparta.controler_view.impl.RunInterface;
import com.sparta.model.dbaccess.ConnectionFactory;
import com.sparta.model.dbaccess.MultithreadedDBWrites;
import com.sparta.model.dbaccess.StatementFactory;
import com.sparta.model.employee.Employee;
import com.sparta.model.fileIO.FileIO;
import com.sparta.model.util.DBInit;
import com.sparta.model.util.PrintTimingData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static com.sparta.model.util.Constants.EMPLOYEE_DAO;
import static com.sparta.model.util.Constants.LOGGER;

public class Driver {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("connection.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DBInit.initDB(properties.getProperty("dbUser"), properties.getProperty("dbPassword"));
        File file =new File("exampleLogfile.log");
        file.delete();
        List<Collection<Employee>> validAndDuplicateCollections = FileIO.performMultithreadedRead();
        LOGGER.info("Number of unique records: " + validAndDuplicateCollections.get(0).size());
        LOGGER.info("Number of duplicate records: " + validAndDuplicateCollections.get(1).size());
        EMPLOYEE_DAO.truncateTable();
        int poolSize = MultithreadedDBWrites.poolSizeUI();
        long startTime = System.nanoTime();
        MultithreadedDBWrites.writeNonDuplicatesOnly((Set<Employee>) validAndDuplicateCollections.get(0), poolSize);
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("MT Insert done in: ", startTime, endTime);
        LOGGER.info("Initiating CRUD Interface...");
        //Call CRUD interface
        RunInterface.runUserInterfaceCRUD();
    }
}
