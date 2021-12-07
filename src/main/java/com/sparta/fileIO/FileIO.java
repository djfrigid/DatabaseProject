package com.sparta.fileIO;

import com.sparta.example.Employee;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;

public class FileIO {
    private static final int poolSize = 8;
    private HashSet<Employee> uniqueEmployees = new HashSet<>();
    private List<Employee> duplicatesAndCorrupted = new ArrayList<>();

    public static void main(String[] args) {
        String filename = "EmployeeRecords.csv";
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(256);
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        for (int i = 0 ; i < poolSize - 1 ; i++){
            pool.submit(new EmployeeParser(queue));
        }
        try {
            pool.submit(new FileReaderClass(queue, filename)).get();
            pool.shutdownNow();
            pool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

}

class FileReaderClass implements Runnable{
    private final BlockingQueue<String> queue;
    private final String inputFilename;

    public FileReaderClass(BlockingQueue<String> queue, String inputFilename){
        this.queue = queue;
        this.inputFilename = inputFilename;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename))){
            String nextLine;
            reader.readLine(); // Read first line
            while((nextLine = reader.readLine()) != null){
                queue.put(nextLine);
            }
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}

class EmployeeParser implements Runnable{
    private final BlockingQueue<String> queue;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public EmployeeParser(BlockingQueue<String> queue){
        this.queue = queue;
    }

    private static Employee parseData(String[] components){
        int id = Integer.MAX_VALUE;
        int salary = Integer.MIN_VALUE;
        try{
            id = Integer.parseInt(components[0]);
            salary = Integer.parseInt(components[9]);
        } catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }
        String namePrefix = components[1];
        String firstName = components[2];
        char initial = components[3].charAt(0);
        String lastName = components[4];
        char gender = components[5].charAt(0);
        String email = components[6];
        // THIS DOESN'T WORK. BUT I WAS DONE.
        // Date dateOfBirth = FORMATTER.parse(components[7]);
        // Date dateOfJoining = FORMATTER.parse(components[7]);
        Employee e = new Employee(id, namePrefix, firstName, initial, lastName, gender, email, null, null, salary);
        System.out.println(e);
        return e;
    }

    @Override
    public void run() {
        String line;
        Employee newEmployee;
        while (true){
            try{
                line = queue.take();
                newEmployee = parseData(line.split(","));
            } catch (InterruptedException e) {
                break;
            }
        }
        while ((line = queue.poll()) != null){
            newEmployee = parseData(line.split(","));
        }
    }
}
