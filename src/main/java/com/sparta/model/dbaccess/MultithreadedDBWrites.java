package com.sparta.model.dbaccess;

import com.sparta.model.employee.Employee;

import java.util.*;
import java.util.concurrent.*;

import static com.sparta.model.util.Constants.LOGGER;

public class MultithreadedDBWrites {

    public static void writeNonDuplicatesOnly(Set<Employee> toWrite, int poolSize){
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        BlockingQueue<Employee> employeeBlockingQueue = new ArrayBlockingQueue<>(toWrite.size());
        employeeBlockingQueue.addAll(toWrite);
        List<Future<?>> areDone = new ArrayList<>(poolSize);
        for (int i = 0 ; i < poolSize ; i++){
            areDone.add(pool.submit(new DBWriter(employeeBlockingQueue)));
        }
        pool.shutdown();
        LOGGER.info("Beginning write process using: " + poolSize + " threads.");
        for (Future<?> future: areDone){
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public static int poolSizeUI(){
        final Scanner SCANNER = new Scanner(System.in);
        System.out.println("Please choose the number of threads that you would like to use.");
        System.out.println("Valid options are:");
        System.out.println("1, 2, 4, 8, 16, 32, 64, 128");
        boolean sizeSetFlag = false;
        String choice = null;
        while (!sizeSetFlag){
            choice = SCANNER.nextLine().toUpperCase(Locale.ROOT).trim();
            if (choice.equals("1") || choice.equals("2") || choice.equals("4") || choice.equals("8") || choice.equals("16") || choice.equals("32") || choice.equals("64") || choice.equals("128") ){
                sizeSetFlag = true;
            } else System.out.println("Choice was not valid. Try again");
        }
        return Integer.parseInt(choice);
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
        int masterCount = 0;
        int numberOfBatches = 0;
        int batchSize = 256;
        Employee employee;
        List<Employee> employeeBatch = new ArrayList<>(batchSize);
        while((employee = chunk.poll()) != null){
            // employeeDao.insertEmployee(employee);
            employeeBatch.add(employee);
            masterCount+=1;
            if (employeeBatch.size() == batchSize){
                employeeDao.insertEmployeeBatch(employeeBatch);
                numberOfBatches+=1;
                employeeBatch.clear();
            }
            if (masterCount % batchSize == 0){
                LOGGER.info("Thread " + Thread.currentThread().getId() + " has inserted " + numberOfBatches + " batches (" + masterCount +") records");
            }
        }
        if (!employeeBatch.isEmpty()){
            employeeDao.insertEmployeeBatch(employeeBatch);
        }
        employeeDao.tearDownConnection();
    }
}