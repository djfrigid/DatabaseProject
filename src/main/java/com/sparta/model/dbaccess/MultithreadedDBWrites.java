package com.sparta.model.dbaccess;

import com.sparta.model.employee.Employee;
import com.sparta.model.util.PrintTimingData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static com.sparta.model.util.Constants.LOGGER;

public class MultithreadedDBWrites {
    private static final int poolSize = 16;

    public static void writeNonDuplicatesOnly(Set<Employee> toWrite){
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        // List<List<Employee>> masterList = splitEmployeeSetForMT(toWrite);
        BlockingQueue<Employee> employeeBlockingQueue = new ArrayBlockingQueue<>(toWrite.size());
        employeeBlockingQueue.addAll(toWrite);
        for (int i = 0 ; i < poolSize ; i++){
            submitToPool(pool, employeeBlockingQueue);
        }
        try{
            long startTime = System.nanoTime();
            if (pool.awaitTermination(10, TimeUnit.SECONDS)){
                long endTime = System.nanoTime();
                PrintTimingData.logTimingData("Writing done in: ", startTime, endTime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdownNow();
    }

    private static void submitToPool(ExecutorService pool, BlockingQueue<Employee> employeeBlockingQueue){
        pool.submit(new DBWriter(employeeBlockingQueue));
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
        List<Employee> test = chunks.get(chunks.size()-1);
        test.addAll(leftovers);
        chunks.remove(chunks.size()-1);
        chunks.add(test);
       // List<Employee> splitOne = listEmployees.subList(0, listEmployees.size()/2);
        //List<Employee> splitTwo = listEmployees.subList(listEmployees.size()/2, listEmployees.size());
        return chunks;
    }
}

class DBWriter implements Runnable{
    private final BlockingQueue<Employee> chunk;
    public DBWriter(BlockingQueue<Employee> chunk){
        this.chunk = chunk;
    }

    @Override
    public void run() {
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        int count = 0;
        Employee employee;
        while((employee = chunk.poll()) != null){
            count+=1;
            employeeDao.insertEmployee(employee);
            if (count % 100 == 0){
                LOGGER.info("Thread " + Thread.currentThread().getId() + " has inserted " + count + " records");
            }
        }
    }
}