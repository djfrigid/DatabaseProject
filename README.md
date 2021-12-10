<div id="top"></div>
<div align="center">
   <a href="https://github.com/djfrigid/DatabaseProject">
    <img src="images/statistics.png" alt="Logo" width="150" height="150">
  </a>
    <h1 align= "center">Database Project</h1>
</div>


<details>
  <summary>Table of Contents</summary>
  <ol>
          <li><a href="#getting-started">Getting Started</a></li>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#phase-one">Phase One</a></li>
      <li><a href="#phase-two">Phase Two</a></li>
      <li><a href="#phase-three">Phase Three</a></li>
      <li><a href="#user-interface">User Interface</a></li>
      <li><a href="#practces">Practices</a></li>
  </ol>
</details>



### Getting Started

This section will walk you through on how to run this project on your machine. 

Make sure that you have JDK installed on your machine in order to compile the code and run the app. 

1. Clone the repository 

   ````sh
   git clone https://github.com/djfrigid/DatabaseProject
   ````

2. Once you clone the project you will need to include `connection.properties` file to connect to the database. Example of MySQL connection file: 

   ```properties
   dbUrl=jdbc:mysql://localhost:3306/[NAME OF THE DATABASE]
   dbUser=[USER NAME]
   dbPassword=[PASSWORD]
   ```

3. Open command prompt (Windows) or terminal (Mac/Linux) and navigate to cloned repository. 

4. Once in the repo, make sure you are in the folder where `Driver.java` is located. 

5. Compile the code

   ```sh
   javac Driver.java
   ```

6. Run the program

   ```sh
   java Driver
   ```

<p align="right">(<a href="#top">back to top</a>)</p>

### About The Project

This project is apart of Sparta global academy training which was developed by a team of five: [Mark](https://github.com/djfrigid), [Ria](https://github.com/Ria622), [Talal](https://github.com/talal1998), [George](https://github.com/gedwards97), [Konrad](https://github.com/KonradDlugosz). The project was divided into three phases and improved over the development cycle.  The main objective of this project is to develop java application that reads CSV file which includes details about employees. After the file is read, individual records is stored in a collection of employee instances. This is than used to populate database. Additionally, the program allows user to perform CRUD operations on the database to manipulate the data. 

<p align="right">(<a href="#top">back to top</a>)</p>

### Phase one

In phase one of the development, following features were developed: 

1. Ability to read data from CSV file that consist of employee details. 
2. Each record was initialized as an Employee object.
3. Each object was added to collection of employees.  
4. Validation check for the correct details format. 
5. Phrasing of data into correct format.
6. Junit testing to ensure data is being managed correctly. 
7. Logging of any errors that may accrue during run time. 

<p align="right">(<a href="#top">back to top</a>)</p>

### Phase two

In phase two of the development, following features were developed: 

1. Connection to the database.
2. Ability to create table and persist data into that table.
3. Data access object used to persist data from phase one.
4. Data access object used to perform CRUD operations.
5. Test added for data validation.

<p align="right">(<a href="#top">back to top</a>)</p>

### Phase three 

In phase three of the development, following features were developed: 

1. Ability to perform multithreading insertion of the data. 

2. Time recorded to evaluate the performance.

3. Making use of functional programming concept - streams and lambdas. 

   <p align="right">(<a href="#top">back to top</a>)</p>

### User Interface

In order to allow user to interact with the program, we have implemented console user interface which is used for varies of reasons:

##### Enter number of threads: 

````tex
Please choose the number of threads that you would like to use.
Valid options are:
1, 2, 4, 8, 16, 32, 64, 128
````

This example demonstrates program asking the user how many threads they would like to use when inserting data into the database. 

Another example for user interface is:

##### CRUD Operations: 

```tex
Database Operations:
1) SELECT ALL
2) SELECT ONE
3) INSERT Employee
4) UPDATE Employee
5) DELETE Employee
6) Quit
Please enter a number to continue: 
```

This example demonstrates all the possible operations that user can perform. For example, user can enter option two to select specific employee which can be specified by their ID number: 

````tex
Running SELECT operation...
Please enter employee ID: 5432
[INFO] 2021-12-10 16:50:58,639 main Data handling app - Employee found!
Employee{id=5432, namePrefix='Hon.', firstName='Sherri', initial=O, lastName='Shrader', gender=F, email='sherri.shrader@yahoo.com', dateOfBirth=1958-05-05, dateOfJoining=null, salary=168297}
````

Other operations behave in similar manner. For example, user can insert a new employee by specifying all the necessary details which than can be checked for existence by running select statement. 

<p align="right">(<a href="#top">back to top</a>)</p>

### Practices

In order to satisfy all the phases we have used certain programming practices as well as design pattern. 

##### Multithreading

Due to poor performance when inserting records into the database, it was necessary to use multithreading  technique to increase the time taken to insert records. In our project, the file consisted of 65,500 records to be inserted which took around **4-8 minutes** depending on machine. This was significantly increased by using multithreading which took around **8-70 seconds** also depending on machine and other processes running in the background. This time factor also depends on the amount of threads being used which has limited number due to the resources. 

##### Data Access Object

In our project we have used DAO pattern to isolate the business layer from the persistence layer. This class would include all the CRUD operations for manipulating the employee table. Each method would make use of pre pared SQL statements that they require to perform specific task. 

##### Test Driven Development

We have also took approach of TDD in some parts of our project like validation for employee details and connection factory which is responsible to connection to the database. This was done to write minimal code in order for the method to past its test which was written beforehand. This technique also minimized the number of bugs in production as well as improved quality of the code. 

<p align="right">(<a href="#top">back to top</a>)</p>