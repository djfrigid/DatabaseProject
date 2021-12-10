package com.sparta.controler_view.impl;

import com.sparta.controler_view.io.bretty.console.view.ActionView;
import com.sparta.model.employee.Employee;

import static com.sparta.model.util.Constants.EMPLOYEE_DAO;

public class SelectOne extends ActionView {

    public SelectOne() {
        super("Running SELECT operation...", "SELECT ONE");
    }

    @Override
    public void executeCustomAction() {
        int id = prompt("Please enter employee ID: " , Integer.class);
        Employee employee = EMPLOYEE_DAO.getEmployee(id);
        System.out.println(employee);
    }
}
