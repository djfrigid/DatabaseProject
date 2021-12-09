package com.sparta.dbaccess;

import com.sparta.employee.Employee;
import com.sparta.util.PrintTimingData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static com.sparta.util.Constants.LOGGER;

public class MultithreadedDBWrites {
    private static final int poolSize = 20;

    public static void writeNonDuplicatesOnly(Set<Employee> toWrite){
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        List<Set<Employee>> theSets = partitionSet(toWrite);
        long startTime = System.nanoTime();
        for (int i = 0 ; i < poolSize ; i++){
            System.out.println("Chunk " + "has: " + theSets.get(i).size() + "items");
            submitToPool(pool, theSets.get(i));
        }
        pool.shutdown();
        try {
            if(pool.awaitTermination(30,TimeUnit.SECONDS)){
                long endTime = System.nanoTime();
                PrintTimingData.logTimingData("Writing done in: ", startTime, endTime);
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void submitToPool(ExecutorService pool, Set<Employee> chunk){
        pool.submit(new DBWriter(chunk));
    }

    public static List<Set<Employee>> partitionSet(Set<Employee> toWrite){
        List<Set<Employee>> theSets = new ArrayList<>(poolSize);
        for(int i = 0; i < poolSize; i++){
            theSets.add(new HashSet<>());
        }

        int index =0;
        for(Employee employee: toWrite){
            theSets.get(index++ % poolSize).add(employee);
        }
        return theSets;
    }
}

class DBWriter implements Runnable{
    private final Set<Employee> chunk;
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private int count = 0;

    public DBWriter(Set<Employee> chunk){
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