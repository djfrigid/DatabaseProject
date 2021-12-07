package com.sparta.fileIO;

import com.sparta.example.Employee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class FileIO {
    private static final int poolSize = 8;
    private static HashSet<Employee> uniqueEmployees = new HashSet<>();
    private static List<Employee> duplicatesAndCorrupted = new ArrayList<>();
    public static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
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
            pool.submit(new FileReaderClass(queue, filename)).get();
            pool.shutdownNow();
            if (pool.awaitTermination(10, TimeUnit.SECONDS)){
                LOGGER.fatal("FUCK MY LIFE");
            };
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Number of unique records: " + uniqueEmployees.size());
        System.out.println("Number of duplicate records: " + duplicatesAndCorrupted.size());

    }

    private static Path takeUserInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose whether you would like to use a name(N), relative path(RP) or absolute path(AP) for your file");
        boolean typeSetFlag = false;
        String choice = null;
        while (!typeSetFlag){
            choice = scanner.nextLine().toUpperCase(Locale.ROOT).trim();
            if (choice.equals("N") || choice.equals("RP") || choice.equals("AP")){
                typeSetFlag = true;
            } else System.out.println("Choice was not one of N, RP or AP");
        }
        String accessPath = null;
        switch (choice){
            case "N" -> {
                System.out.println("Please enter the name of the file that you would like to use.");
                System.out.println("This file should be at the root level of the project");
                accessPath = scanner.nextLine().trim();
            }
            case "RP" -> {
                System.out.println("Enter the relative path of the file you would like to use");
                System.out.println("This path is relative to the root level of the project");
                accessPath = buildSystemIndependentPath(scanner);
            }
            case "AP" -> {
                System.out.println("Enter the absolute path of the file you would like to use");
                accessPath = buildSystemIndependentPath(scanner);
            }
        }
        return Path.of(accessPath);
    }

    private static String buildSystemIndependentPath(Scanner scanner) {
        String accessPath;
        String regex = "[\\/]";
        String[] apBits = scanner.nextLine().trim().split(regex);
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
            FileIO.LOGGER.info("Items added to queue are: " + count);
        } catch (IOException | InterruptedException e){
            FileIO.LOGGER.fatal("Exception occurred");
        }
    }
}

class EmployeeParser implements Runnable{
    private final BlockingQueue<String> queue;
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("MM/dd/yyyy");

    public EmployeeParser(BlockingQueue<String> queue){
        this.queue = queue;
    }

    public static synchronized Employee parseData(String[] components) {
        int id = Integer.MIN_VALUE;
        int salary = Integer.MIN_VALUE;
        boolean idParsed = false;
        try {
            id = Integer.parseInt(components[0]);
            idParsed = true;
            salary = Integer.parseInt(components[9]);
        } catch (NumberFormatException nfe) {
            if (idParsed) System.out.println("Error parsing salary field");
            else System.out.println("Error parsing Employee ID");
            FileIO.LOGGER.fatal("Fuck");
        }
        String namePrefix = components[1];
        String firstName = components[2];
        char initial = components[3].charAt(0);
        String lastName = components[4];
        char gender = components[5].charAt(0);
        String email = components[6];

        java.sql.Date dateOfBirth = java.sql.Date.valueOf(DateFormatter.formatDate(components[7]));
        java.sql.Date dateOfJoining = java.sql.Date.valueOf(DateFormatter.formatDate(components[8]));
        Employee e = new Employee(id, namePrefix, firstName, initial, lastName, gender, email, dateOfBirth, dateOfJoining, salary);
        return e;

    }

    @Override
    public void run() {
        String line;
        Employee newEmployee;
        // While still reading from the file, run indefinitely. Once reading is over, catch the excpetion and break the loop
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
