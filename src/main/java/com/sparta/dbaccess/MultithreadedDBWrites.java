package com.sparta.dbaccess;

import com.sparta.employee.Employee;
import com.sparta.util.PrintTimingData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static com.sparta.util.Constants.LOGGER;

public class MultithreadedDBWrites {
    private static final int poolSize = 8;

    public static void writeNonDuplicatesOnly(Set<Employee> toWrite){
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        List<List<Employee>> masterList = splitEmployeeSetForMT(toWrite);
        for (int i = 0 ; i < poolSize ; i++){
            System.out.println("Chunk " + i + " is this many big: " + masterList.get(i).size());
            submitToPool(pool, masterList.get(i));
        }
        System.exit(0);
        try{
            long startTime = System.nanoTime();
            if (pool.awaitTermination(10, TimeUnit.SECONDS)){
                long endTime = System.nanoTime();
                PrintTimingData.logTimingData("Writing done in: ", startTime, endTime);
            } else {
                LOGGER.info("");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdownNow();
        LOGGER.info("P");
    }

    public static void submitToPool(ExecutorService pool, List<Employee> chunk){
        pool.submit(new DBWriter(chunk));
    }

    public static List<List<Employee>> splitEmployeeSetForMT(Set<Employee> toWrite){
        List<Employee> listEmployees = new ArrayList<>(toWrite);
        int chunkSize = toWrite.size()/poolSize;
        int lastRead = (chunkSize*poolSize)+1;
        List<Employee> autoChunkableListEmployees = listEmployees.subList(0, lastRead);
        List<Employee> leftovers = listEmployees.subList(lastRead, listEmployees.size());
        List<List<Employee>> chunks = new ArrayList<>(poolSize);
        for (int i = 0 ; i < poolSize ; i++){
            List<Employee> chunk = autoChunkableListEmployees.subList(chunkSize*i, chunkSize*(i+1));
            chunks.add(chunk);
        }
        chunks.get(chunks.size()-1).addAll(leftovers);
       // List<Employee> splitOne = listEmployees.subList(0, listEmployees.size()/2);
        //List<Employee> splitTwo = listEmployees.subList(listEmployees.size()/2, listEmployees.size());
        return chunks;
    }
}

class DBWriter implements Runnable{
    private final List<Employee> chunk;
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private int count = 0;

    public DBWriter(List<Employee> chunk){
        this.chunk = chunk;
    }

    @Override
    public void run() {
        Employee theEmployee;
        for (Employee e: chunk){
            employeeDao.insertEmployee(e);
            count++;
            if (count % 100 == 0){
                LOGGER.info("Records inserted so far: " + count);
            }
        }
    }
}