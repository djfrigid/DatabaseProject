package com.sparta.controler_view.impl;

import com.sparta.controler_view.io.bretty.console.view.ActionView;
import com.sparta.model.employee.Employee;

import java.sql.Date;

import static com.sparta.model.util.Constants.EMPLOYEE_DAO;

public class UpdateEmployee extends ActionView {

    public UpdateEmployee() {
        super("Running UPDATE operation...", "UPDATE Employee");
    }

    @Override
    public void executeCustomAction() {
        System.out.println("Params to update: ");
        int id= prompt("ID: ", Integer.class);
        String namePrefix = prompt("Name prefix: ", String.class);
        String firstName = prompt("First name: ", String.class);
        char initial = prompt("Initial: ", String.class).charAt(0);
        String lastName = prompt("Last name: ", String.class);
        char gender = prompt("Gender: ", String.class).charAt(0);
        String email = prompt("Email: ", String.class);
        Date dateOfBirth = prompt("Date of birth: ", Date.class);
        Date dateOfJoining = prompt("Date of joining: ", Date.class);
        int salary = prompt("Salary: ", Integer.class);

        Employee employee = new Employee(id,namePrefix,firstName,initial,lastName,gender,email,dateOfBirth,dateOfJoining,salary);
        EMPLOYEE_DAO.updateEmployee(employee);


    }
}
