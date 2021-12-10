package com.sparta.controler_view.impl;

import com.sparta.controler_view.io.bretty.console.view.ActionView;
import com.sparta.model.dbaccess.EmployeeDao;
import com.sparta.model.employee.Employee;

import static com.sparta.model.util.Constants.EMPLOYEE_DAO;

public class DeleteEmployee extends ActionView {

    public DeleteEmployee(){
        super("Running DELETE operation...", "DELETE Employee");
    }

    @Override
    public void executeCustomAction() {
        int id = prompt("Please enter employee ID: ", Integer.class);
        //System.out.println("The employee deleted: " );
        EMPLOYEE_DAO.deleteEmployee(id);


    }
}
