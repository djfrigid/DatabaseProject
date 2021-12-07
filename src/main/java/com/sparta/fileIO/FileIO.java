package com.sparta.fileIO;

import com.sparta.example.Employee;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class FileIO {

    public static void main(String[] args) {

    }

    private HashSet<Employee> uniqueEmployees = new HashSet<>();
    private List<Employee> duplicatesAndCorrupted = new ArrayList<>();


    private static void readFromFile(String inputFilename) {

    }

}

class ReadFromFile implements Runnable{
    private final BlockingQueue<String> queue;
    private final String inputFilename;

    public ReadFromFile(BlockingQueue<String> queue, String inputFilename){
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

class ParseEmployee implements Runnable{
    private final BlockingQueue<String> queue;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public ParseEmployee(BlockingQueue<String> queue){
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
        Date dateOfBirth = FORMATTER.parse(components[7]);
        Date dateOfJoining = FORMATTER.parse(components[7]);
        return new Employee(id, namePrefix, firstName, initial, lastName, gender, email, dateOfBirth, dateOfJoining, salary);
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
