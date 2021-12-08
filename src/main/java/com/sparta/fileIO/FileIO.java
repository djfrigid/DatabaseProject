package com.sparta.fileIO;

import com.sparta.example.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sparta.util.PrintTimingData;
import com.sparta.validate.EmployeeValidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

import static com.sparta.util.Constants.LOGGER;

public class FileIO {
    private static final int poolSize = 8;
    private static final HashSet<Employee> uniqueEmployees = new HashSet<>();
    private static final List<Employee> duplicatesAndCorrupted = new ArrayList<>();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static List<Collection<Employee>> performMultithreadedRead() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(256);
        // Thread Safe Queue to be shared amongst all threads
        Path filename = Path.of("EmployeeRecords.csv");
        // Thread pool of fixed size, to be used for parsing Employee lines
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        // Create Parser threads and put them in the pool
        for (int i = 0 ; i < poolSize - 1 ; i++){
            pool.submit(new EmployeeParser(queue));
        }
        // Try to read the file, shutting the thread when done, then block until no items left in queue
        try {
            long startTime = System.nanoTime();
            pool.submit(new FileReaderClass(queue, filename)).get();
            pool.shutdownNow();
            long endTime = System.nanoTime();
            PrintTimingData.logTimingData("File read done in: ", startTime, endTime);
            startTime = System.nanoTime();
            if (pool.awaitTermination(10, TimeUnit.SECONDS)){
                endTime = System.nanoTime();
                LOGGER.info("Parsing threads now finished");
                PrintTimingData.logTimingData("Parsing done in: ", startTime, endTime);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
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
        switch (choice){
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
        return Path.of(accessPath);
    }

    public void checkDuplicate(Employee x){
        if(uniqueEmployees.contains(x))
            duplicatesAndCorrupted.add(x);
        else
            uniqueEmployees.add(x);
    private static String buildSystemIndependentPath() {
        String accessPath;
        String regex = "[\\/]";
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
        if (uniqueEmployees.contains(x))
            duplicatesAndCorrupted.add((x));
        else uniqueEmployees.add(x);
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
            LOGGER.info("Items added to queue are: " + count);
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
        java.sql.Date dateOfBirth = java.sql.Date.valueOf(DateFormatter.formatDate(components[7]));
        java.sql.Date dateOfJoining = java.sql.Date.valueOf(DateFormatter.formatDate(components[8]));
        return new Employee(id, namePrefix, firstName, initial, lastName, gender, email, dateOfBirth, dateOfJoining, salary);
    }

    @Override
    public void run() {
        String line;
        Employee newEmployee;
        // While still reading from the file, run indefinitely. Once reading is over, catch the exception and break the loop
        // Once loop broken, empty queue then shut down.
        while (true){
            try{
                line = queue.take();
                newEmployee = parseData(line.split(","));
                FileIO.insertEmployee(newEmployee);
            } catch (InterruptedException e) {
                break;
            }
        }
        while ((line = queue.poll()) != null){
            newEmployee = parseData(line.split(","));
            FileIO.insertEmployee(newEmployee);
        }
    }
}

class DateFormatter {

    private static final SimpleDateFormat inSDF = new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(String inDate) {
        String outDate = "";
        if (inDate != null) {
            try {
                java.util.Date date = inSDF.parse(inDate);
                outDate = outSDF.format(date);
            } catch (ParseException ex){
                ex.printStackTrace();
            }
        }
        return outDate;
    }
}

class loggingValues {
    private static Logger logger = LogManager.getLogger("Update");

    public void logUnique(HashSet<Employee> em) {
        logger.info("The number of the unique values : " + em.size());
    }

    public void logDuplicates(List<Employee> d) {
        logger.info("The number of duplicate values: " + d.size());
    }

    public void logCorrupt(){
        logger.info("The number of corrupt values: " );
    }

    public void errors(){
        logger.info("error");
    }
}