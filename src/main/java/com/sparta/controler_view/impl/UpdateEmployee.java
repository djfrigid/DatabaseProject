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
        int id= prompt("Please enter ID: ", Integer.class);
        String namePrefix = prompt("Please enter name prefix: ", String.class);
        String firstName = prompt("Please enter first name: ", String.class);
        char initial = prompt("Please enter initial: ", String.class).charAt(0);
        String lastName = prompt("Please enter last name: ", String.class);
        char gender = prompt("Please enter gender: ", String.class).charAt(0);
        String email = prompt("Please enter email: ", String.class);
        Date dateOfBirth = prompt("Please enter date of birth: ", Date.class);
        Date dateOfJoining = prompt("Please enter date of joining: ", Date.class);
        int salary = prompt("Please enter salary: ", Integer.class);

        Employee employee = new Employee(id,namePrefix,firstName,initial,lastName,gender,email,dateOfBirth,dateOfJoining,salary);
        EMPLOYEE_DAO.updateEmployee(employee);


    }
}
