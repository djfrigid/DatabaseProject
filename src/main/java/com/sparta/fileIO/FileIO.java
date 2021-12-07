package com.sparta.fileIO;

import com.sparta.example.Employee;

import java.io.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class FileIO {

    public static void main(String[] args) {

    }
    private HashSet<Employee> uniqueEmployees = new HashSet<>();
    private List<Employee> duplicatesAndCorrupted = new ArrayList<>();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private static void readFromFile(String inputFilename, String outputFilename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename)); BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))){
            String nextLine;
            reader.readLine(); // Read first line
            while((nextLine = reader.readLine()) != null){
                parseData(nextLine.split(","));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
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
        // THIS DOESN'T WORK. ASK KONRAD TO CHANGE DATE USE
        Date dateOfBirth = (Date) FORMATTER.parse(components[7]);
        Date dateOfJoining = (Date) FORMATTER.parse(components[7]);
        return new Employee(id, namePrefix, firstName, initial, lastName, gender, email, dateOfBirth, dateOfJoining, salary);
    }


}
