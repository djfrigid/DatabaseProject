package com.sparta.controler_view.impl;

import com.sparta.controler_view.io.bretty.console.view.ActionView;
import com.sparta.model.employee.Employee;

import java.util.List;

import static com.sparta.model.util.Constants.EMPLOYEE_DAO;

public class SelectAll extends ActionView {


    public SelectAll(){
        super("Running SELECT operation...", "SELECT ALL");
    }

    @Override
    public void executeCustomAction() {
        List<Employee> employeeList = EMPLOYEE_DAO.getAllEmployees();
        employeeList.forEach(System.out::println);
        
    }
}
