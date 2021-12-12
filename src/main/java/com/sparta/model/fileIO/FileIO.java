package com.sparta.model.fileIO;

import com.sparta.model.employee.Employee;

import com.sparta.model.util.DateFormatter;
import com.sparta.model.util.PrintTimingData;
import com.sparta.model.validate.EmployeeValidate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

import static com.sparta.model.util.Constants.LOGGER;

public class FileIO {
    private static final int poolSize = 8;
    private static final HashSet<Employee> uniqueEmployees = new HashSet<>();
    private static final HashSet<Integer> uniqueId = new HashSet<>();
    private static final List<Employee> duplicatesAndCorrupted = new ArrayList<>();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static List<Collection<Employee>> performMultithreadedRead() {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        // Thread Safe Queue to be shared amongst all threads
        Path filename = Path.of("EmployeeRecords.csv");
        // Path filename = takeUserInput();
        // Thread pool of fixed size, to be used for parsing Employee lines
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        List<Future<?>> areDone = new ArrayList<>(poolSize);
        // Create Parser threads and put them in the pool
        for (int i = 0 ; i < poolSize - 1 ; i++){
            areDone.add(pool.submit(new EmployeeParser(queue)));
        }
        areDone.add(pool.submit(new FileReaderClass(queue, filename)));
        pool.shutdown();
        LOGGER.info("Tasks submitted to pool. Awaiting completion of process.");
        long startTime = System.nanoTime();
        for (Future<?> future: areDone){
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.nanoTime();
        PrintTimingData.logTimingData("Reading and parsing done in: ", startTime, endTime);
        List<Collection<Employee>> collections = new ArrayList<>(2);
        collections.add(uniqueEmployees);
        collections.add(duplicatesAndCorrupted);
        return collections;
    }

    private static Path takeUserInput(){
        System.out.println("Please choose whether you would like to use a name(N), relative path(RP) or absolute path(AP) for your file");
        boolean typeSetFlag = false;
        String choice = null;
        while (!typeSetFlag){
            choice = SCANNER.nextLine().toUpperCase(Locale.ROOT).trim();
            if (choice.equals("N") || choice.equals("RP") || choice.equals("AP")){
                typeSetFlag = true;
            } else System.out.println("Choice was not one of N, RP or AP");
        }
        String accessPath = null;
        boolean validPath = false;
        while (!validPath) {
            switch (choice) {
                case "N" -> {
                    System.out.println("Please enter the name of the file that you would like to use.");
                    System.out.println("This file should be at the root level of the project");
                    accessPath = SCANNER.nextLine().trim();
                }
                case "RP" -> {
                    System.out.println("Enter the relative path of the file you would like to use");
                    System.out.println("This path is relative to the root level of the project");
                    accessPath = buildSystemIndependentPath();
                }
                case "AP" -> {
                    System.out.println("Enter the absolute path of the file you would like to use");
                    accessPath = buildSystemIndependentPath();
                }
            }
            if (Files.exists(Path.of(accessPath))){
                validPath=true;
            }
            else System.out.println("Invalid path specified. Please try again");
        }

        return Path.of(accessPath);
    }

    private static String buildSystemIndependentPath() {
        String accessPath;
        String regex = "[/]";
        String[] apBits = SCANNER.nextLine().trim().split(regex);
        StringBuilder sb = new StringBuilder();
        String fileSep = File.separator;
        for (String s : apBits){
            sb.append(s).append(fileSep);
        }
        accessPath = sb.substring(0, sb.length()-1);
        System.out.println(accessPath);
        return accessPath;
    }

    public static synchronized void insertEmployee(Employee x){
        if (uniqueId.contains(x.getId())){
            duplicatesAndCorrupted.add((x));
        }
        else{
            uniqueId.add(x.getId());
            uniqueEmployees.add(x);
        }
    }

}

class FileReaderClass implements Runnable{
    private final BlockingQueue<String> queue;
    private final Path inputFilename;

    public FileReaderClass(BlockingQueue<String> queue, Path inputFilename){
        this.queue = queue;
        this.inputFilename = inputFilename;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(inputFilename)))){
            String nextLine;
            reader.readLine(); // Read first line
            int count = 0;
            while((nextLine = reader.readLine()) != null){
                queue.put(nextLine);
                count++;
            }
            LOGGER.info(count + " records found in file. Parsing in progress.");
        } catch (IOException | InterruptedException e){
            LOGGER.warn("Exception occurred");
        }
    }
}

class EmployeeParser implements Runnable{
    private final BlockingQueue<String> queue;

    public EmployeeParser(BlockingQueue<String> queue){
        this.queue = queue;
    }

    public static synchronized Employee parseData(String[] components) {
        int id = Integer.MIN_VALUE;
        int salary = Integer.MIN_VALUE;
        boolean idParsed = false;
        try {
            id = Integer.parseInt(EmployeeValidate.validateId(components[0]));
            idParsed = true;
            salary = Integer.parseInt(EmployeeValidate.validateSalary(components[9]));
        } catch (NumberFormatException nfe) {
            if (idParsed) System.out.println("Error parsing salary field");
            else System.out.println("Error parsing Employee ID");
            LOGGER.error("Parsing failed");
        }
        String namePrefix = EmployeeValidate.validateNamePrefix(components[1]);
        String firstName = EmployeeValidate.validateName(components[2]);
        char initial = EmployeeValidate.validateInitial(components[3].charAt(0));
        String lastName = EmployeeValidate.validateName(components[4]);
        char gender = EmployeeValidate.validateGender(components[5].charAt(0));
        String email = EmployeeValidate.validateEmail(components[6]);
        java.sql.Date dateOfBirth = DateFormatter.formatDate(components[7], true);
        java.sql.Date dateOfJoining = DateFormatter.formatDate(components[8], false);
        return new Employee(id, namePrefix, firstName, initial, lastName, gender, email, dateOfBirth, dateOfJoining, salary);
    }

    @Override
    public void run() {
        String line;
        Employee newEmployee;
        LOGGER.info("Thread " + Thread.currentThread().getId() + " beginning parsing process.");
        while ((line = queue.poll()) != null){
            newEmployee = parseData(line.split(","));
            FileIO.insertEmployee(newEmployee);
        }
    }
}
