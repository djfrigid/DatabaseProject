package com.sparta.fileIO;

import com.sparta.example.Employee;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class FileIO {
    private static final int poolSize = 8;
    private HashSet<Employee> uniqueEmployees = new HashSet<>();
    private List<Employee> duplicatesAndCorrupted = new ArrayList<>();

    public static void main(String[] args) {
        String filename = takeUserInput();
        // Thread Safe Queue to be shared amongst all threads
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(256);
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
            pool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    private static String takeUserInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the name of the file that you would like to read");
        System.out.println("(Input is case sensitive)");
        return scanner.nextLine().trim();
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
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("MM/dd/yyyy");

    public EmployeeParser(BlockingQueue<String> queue){
        this.queue = queue;
    }

    private static Employee parseData(String[] components) {
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
        }
        String namePrefix = components[1];
        String firstName = components[2];
        char initial = components[3].charAt(0);
        String lastName = components[4];
        char gender = components[5].charAt(0);
        String email = components[6];
        Date dateOfBirth = null;
        Date dateOfJoining = null;
        try {
            dateOfBirth = FORMATTER.parse(components[7]);
            dateOfJoining = FORMATTER.parse(components[8]);
        } catch (ParseException e) {
            if (dateOfBirth == null) System.out.println("Error parsing Date of Birth");
            else System.out.println("Error parsing Date of Joining");
        }
        Employee e = new Employee(id, namePrefix, firstName, initial, lastName, gender, email, dateOfBirth, dateOfJoining, salary);
        System.out.println(e);
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
            } catch (InterruptedException e) {
                break;
            }
        }
        while ((line = queue.poll()) != null){
            newEmployee = parseData(line.split(","));
        }
    }
}
