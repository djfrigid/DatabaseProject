package com.sparta.controler_view.impl;

import com.sparta.controler_view.io.bretty.console.view.ActionView;
import com.sparta.model.employee.Employee;

import java.sql.Date;

import static com.sparta.model.util.Constants.EMPLOYEE_DAO;

public class InsertEmployee extends ActionView {

    public InsertEmployee(){
        super("Running INSERT operation...", "INSERT Employee");

    }

    @Override
    public void executeCustomAction() {
        int id = prompt("Please enter employee ID: ", Integer.class);
        String namePrefix = prompt("Please enter name prefix: ", String.class);
        String firstName = prompt("Please enter first name: ", String.class);
        char initial = prompt("Please enter an initial: ", String.class).charAt(0);
        String lastName = prompt("Please enter last name: ", String.class);
        char gender = prompt("Please enter Male(M) or Female(F): ", String.class).charAt(0);
        String email = prompt("Please enter email: ", String.class);
        Date dateofBirth = prompt("Please enter date of birth: ", Date.class);
        Date dateOfJoining = prompt("Please enter joining date: ", Date.class);
        int salary = prompt("Please enter employee salary: ", Integer.class);
        Employee employee1 = new Employee(id,namePrefix,firstName,initial,lastName,gender, email,dateofBirth,dateOfJoining,salary);
        EMPLOYEE_DAO.insertEmployee(employee1);
    }
}
